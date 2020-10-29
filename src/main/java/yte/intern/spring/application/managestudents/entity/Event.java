package yte.intern.spring.application.managestudents.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import yte.intern.spring.application.common.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_SEQ")
public class Event extends BaseEntity {
	@Column(name = "EVENT_ID", unique = true)
	@Size(max = 255, message = "Event ID can't be longer than 255!")
	@NotBlank
	private String eventId;

	@Column(name = "TITLE")
	@Size(max = 255, message = "Title can't be longer than 255!")
	@NotBlank
	private String title;

	@Column(name = "START_DATE")
	@FutureOrPresent(message = "Start Date can't be in the past")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate;

	@Column(name = "END_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd HH:mm")
	private LocalDateTime endDate;

	@Column(name = "LOCATION")
	@Size(max = 255, message = "Location can't be longer than 255!")
	@NotBlank
	private String location;

	@Column(name = "QUOTA")
	@Min(value = 1, message = "Quota must be greater than 0!")
	private Long quota;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "EVENTS_ID")
	private Set<Guest> guests;

	@AssertTrue(message = "End Date must be greater than Start Date")
	public boolean isDatesValid() {return !startDate.isAfter(endDate);}

	@AssertTrue(message = "Quota must be greater than 0")
	public boolean isQuotaValid() {return quota>0;}

	public boolean hasFullCapacity() {
		return guests.size() == quota;
	}

	public boolean hasGuest(Guest guest) {
		return guests.stream().anyMatch(it -> it.getTcKimlikNo().equals(guest.getTcKimlikNo()));
	}
}
