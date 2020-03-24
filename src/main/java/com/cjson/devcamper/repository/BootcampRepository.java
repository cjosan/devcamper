package com.cjson.devcamper.repository;

import com.cjson.devcamper.model.Bootcamp;
import com.cjson.devcamper.model.QBootcamp;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface BootcampRepository extends PagingAndSortingRepository<Bootcamp, Long>, QuerydslPredicateExecutor<Bootcamp>, QuerydslBinderCustomizer<QBootcamp> {

	//	SELECT *
//	FROM location
//	WHERE ST_DistanceSphere(point, ST_MakePoint(-71.103257,52.350615)) <= 900 * 1609.34
//
//	SELECT *
//	FROM location
//	WHERE ST_DistanceSphere(point, ST_MakePoint(-71.103257,52.350615)) <= 1112 * 1000
	@Query(value = "SELECT * FROM bootcamp " +
			"WHERE ST_DistanceSphere(location_point, ST_MakePoint(:longitude, :latitude)) <= :radius * :meters",
			nativeQuery = true)
	List<Bootcamp> getBootcampsInRadius(@Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("radius") Double radius, @Param("meters") Double meters);

	@Override
	default void customize(QuerydslBindings bindings, QBootcamp bootcamp) {
		// Exclude id from filtering
		bindings.excluding(bootcamp.id);

		// Make case-insensitive 'like' filter for all string properties
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

		bindings.bind(QBootcamp.bootcamp.tags).first((path, values) -> {
			BooleanBuilder predicate = new BooleanBuilder();
			values.forEach(value -> predicate.or(path.any().containsIgnoreCase(value)));

			return predicate;
		});

		// Make a kind of 'between' filter for created date property
		bindings.bind(QBootcamp.bootcamp.createdAt).all((path, value) -> {
			Iterator<? extends LocalDate> it = value.iterator();
			LocalDate from = it.next();
			if (value.size() >= 2) {
				LocalDate to = it.next();
				return Optional.of(path.between(from, to));
			} else {
				return Optional.of(path.goe(from));
			}
		});
	}
}
