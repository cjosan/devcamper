package com.cjson.devcamper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.slugify.Slugify;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Bootcamp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Please add a name")
	@Size(max = 50, message = "Name can not be more than 50 characters")
	private String name;

	private String slug;

	@Column(nullable = false)
	@NotBlank(message = "Please add a description")
	@Size(max = 500, message = "Description can not be more than 500 characters")
	private String description;

	@Pattern(regexp = "^(http|https)://(www)?.([a-z.]*)?(/[a-z1-9/]*)*\\??([&a-z1-9=]*)?",
			message = "Please use a valid URL with HTTP or HTTPS")
	private String website;

	@Size(max = 20, message = "Phone number can not be longer than 20 characters")
	private String phone;

	@Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
			message = "Please add a valid email")
	private String email;

	@NotBlank(message = "Please add an address")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String address;

	@Embedded
	private Location location;

	@NotEmpty(message = "Please add some tags")
	@ElementCollection
	private List<String> tags;

	private Boolean housing = false;

	private Boolean jobAssistance = false;

	private Boolean jobGuarantee = false;

	private Boolean acceptGi = false;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate createdAt = LocalDate.now();

	@PrePersist
	@PreUpdate
	private void generateSlug() {
		Slugify slg = new Slugify().withLowerCase(true);
		this.slug = slg.slugify(this.name);
	}
}
