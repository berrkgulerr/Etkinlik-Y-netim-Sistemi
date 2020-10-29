package yte.intern.spring.application.managestudents.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventDTO {

	@Size(max = 255, message = "Event ID can't be longer than 255!")
	@NotBlank
	public final String eventId;

	@Size(max = 255, message = "Title can't be longer than 255!")
	@NotBlank
	public final String title;

	@FutureOrPresent(message = "Start Date can't be in the past")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	public final LocalDateTime startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	public final LocalDateTime endDate;

	@Size(max = 255, message = "Location can't be longer than 255!")
	@NotBlank
	public final String location;

	@Min(value = 1, message = "Quota must be greater than 0!")
	public final Long quota;

	@JsonCreator
	public EventDTO(@JsonProperty("eventId") String eventId,
					@JsonProperty("title") String title,
					@JsonProperty("startDate") LocalDateTime startDate,
					@JsonProperty("endDate") LocalDateTime endDate,
					@JsonProperty("location") String location,
					@JsonProperty("quota") Long quota) {
		this.eventId = eventId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.quota = quota;
	}
	@AssertTrue(message = "Quota must be greater than 0")
	public boolean isQuotaValid() {return quota>0;}

	@AssertTrue(message = "End Date must be greater than Start Date")
	public boolean isDatesValid() {return !startDate.isAfter(endDate);}
}
