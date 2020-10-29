package yte.intern.spring.application.managestudents.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yte.intern.spring.application.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "GUEST_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends BaseEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "TC_KIMLIK_NO", unique = true)
	private String tcKimlikNo;

	@Column(name = "EMAIL", unique = true)
	private String email;
}
