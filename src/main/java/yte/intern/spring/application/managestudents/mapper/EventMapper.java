package yte.intern.spring.application.managestudents.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.application.managestudents.dto.EventDTO;
import yte.intern.spring.application.managestudents.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

	EventDTO mapToDto(Event event);

	Event mapToEntity(EventDTO eventDTO);

	List<EventDTO> mapToDto(List<Event> eventList);

	List<Event> mapToEntity(List<EventDTO> eventDTOList);
}
