package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.application.managestudents.dto.EventDTO;
import yte.intern.spring.application.managestudents.dto.GuestDTO;
import yte.intern.spring.application.managestudents.entity.Event;
import yte.intern.spring.application.managestudents.entity.Guest;
import yte.intern.spring.application.managestudents.mapper.GuestMapper;
import yte.intern.spring.application.managestudents.mapper.EventMapper;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")

public class ManageEventController {

	private final ManageEventService manageEventService;
	private final EventMapper eventMapper;
	private final GuestMapper guestMapper;

	@GetMapping
	public List<EventDTO> listAllEvents() {
		List<Event> events = manageEventService.listAllEvents();
		return eventMapper.mapToDto(events);
	}

	@PostMapping
	public EventDTO addEvent(@Valid @RequestBody EventDTO eventDTO) {
		Event event = eventMapper.mapToEntity(eventDTO);
		Event addedEvent = manageEventService.addEvent(event);
		return eventMapper.mapToDto(addedEvent);
	}

	@GetMapping("/{eventId}")
	public EventDTO getStudentByStudentNumber(@PathVariable String eventId) {
		Event event = manageEventService.getEventByEventId(eventId);
		return eventMapper.mapToDto(event);
	}

	@PutMapping("/{eventId}")
	public EventDTO updateStudent(@PathVariable String eventId, @Valid @RequestBody EventDTO eventDTO) {
		Event event = eventMapper.mapToEntity(eventDTO);
		Event updatedEvent = manageEventService.updateEvent(eventId, event);
		return eventMapper.mapToDto(updatedEvent);
	}

	@DeleteMapping("/{eventId}")
	public void deleteStudent(@PathVariable String eventId) {
		manageEventService.deleteEvent(eventId);
	}


	@GetMapping("/{eventId}/guests")
	public List<GuestDTO> getEventsGuests(@PathVariable String eventId) {
		Set<Guest> eventsGuests = manageEventService.getEventsGuests(eventId);
		return guestMapper.mapToDto(new ArrayList<>(eventsGuests));
	}

	@PostMapping("/{eventId}/guests")
	public GuestDTO addGuestToEvent(@PathVariable String eventId, @RequestBody @Valid GuestDTO guestDTO) {
		Guest addedGuest = manageEventService.addGuestToEvent(eventId, guestMapper.mapToEntity(guestDTO));
		return guestMapper.mapToDto(addedGuest);
	}

	@DeleteMapping("/{eventId}/guests/{tcKimlikNo}")
	public void addGuestToEvent(@PathVariable String eventId, @PathVariable String tcKimlikNo) {
		manageEventService.deleteGuest(eventId, tcKimlikNo);
	}

}
