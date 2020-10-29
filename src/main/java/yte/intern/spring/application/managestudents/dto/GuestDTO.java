package yte.intern.spring.application.managestudents.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import yte.intern.spring.application.managestudents.validation.TcKimlikNo;

import javax.validation.constraints.*;

@Getter
@Builder
public class GuestDTO {


	@Size(max = 255, message = "Name can't be longer than 255!")
	@NotBlank
	public final String name;

	@Size(max = 255, message = "Surname can't be longer than 255!")
	@NotBlank
	public final String surname;

	@Email(message = "Please enter a valid e-mail address!")
	@Size(max = 255, message = "E-mail can't be longer than 255!")
	@NotBlank
	public final String email;

	@Size(min = 11, max = 11, message = "TC Kimlik no must be 11 characters long!")
	@TcKimlikNo(message = "TC Kimlik No must be valid!")
	@NotBlank
	public final String tcKimlikNo;


	@JsonCreator
	public GuestDTO(@JsonProperty("name") String name,
					@JsonProperty("surname") String surname,
					@JsonProperty("email") String email,
					@JsonProperty("tcKimlikNo") String tcKimlikNo) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.tcKimlikNo = tcKimlikNo;
	}
}
