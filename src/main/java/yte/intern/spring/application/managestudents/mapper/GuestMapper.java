package yte.intern.spring.application.managestudents.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.application.managestudents.dto.GuestDTO;
import yte.intern.spring.application.managestudents.entity.Guest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

	GuestDTO mapToDto(Guest guest);

	Guest mapToEntity(GuestDTO guestDTO);

	List<GuestDTO> mapToDto(List<Guest> guestList);

	List<Guest> mapToEntity(List<GuestDTO> guestDTOList);
}
