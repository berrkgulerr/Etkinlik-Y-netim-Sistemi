package yte.intern.spring.application.managestudents.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import yte.intern.spring.application.managestudents.dto.EventDTO;
import yte.intern.spring.application.managestudents.dto.EventDTO.EventDTOBuilder;
import yte.intern.spring.application.managestudents.entity.Event;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-10T13:43:45+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (AdoptOpenJDK)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDTO mapToDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDTOBuilder eventDTO = EventDTO.builder();

        eventDTO.eventId( event.getEventId() );
        eventDTO.title( event.getTitle() );
        eventDTO.startDate( event.getStartDate() );
        eventDTO.endDate( event.getEndDate() );
        eventDTO.location( event.getLocation() );
        eventDTO.quota( event.getQuota() );

        return eventDTO.build();
    }

    @Override
    public Event mapToEntity(EventDTO eventDTO) {
        if ( eventDTO == null ) {
            return null;
        }

        Event event = new Event();

        event.setEventId( eventDTO.getEventId() );
        event.setTitle( eventDTO.getTitle() );
        event.setStartDate( eventDTO.getStartDate() );
        event.setEndDate( eventDTO.getEndDate() );
        event.setLocation( eventDTO.getLocation() );
        event.setQuota( eventDTO.getQuota() );

        return event;
    }

    @Override
    public List<EventDTO> mapToDto(List<Event> eventList) {
        if ( eventList == null ) {
            return null;
        }

        List<EventDTO> list = new ArrayList<EventDTO>( eventList.size() );
        for ( Event event : eventList ) {
            list.add( mapToDto( event ) );
        }

        return list;
    }

    @Override
    public List<Event> mapToEntity(List<EventDTO> eventDTOList) {
        if ( eventDTOList == null ) {
            return null;
        }

        List<Event> list = new ArrayList<Event>( eventDTOList.size() );
        for ( EventDTO eventDTO : eventDTOList ) {
            list.add( mapToEntity( eventDTO ) );
        }

        return list;
    }
}
