package yte.intern.spring.application.managestudents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.application.managestudents.entity.Event;
import yte.intern.spring.application.managestudents.entity.Guest;
import yte.intern.spring.application.managestudents.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ManageEventService {

	private final EventRepository eventRepository;

	public List<Event> listAllEvents() {
		return eventRepository.findAll();
	}

	public Event addEvent(Event event) {
		return eventRepository.save(event);
	}

	public Event getEventByEventId(String eventId) {
		return eventRepository.findByEventId(eventId).orElseThrow(EntityNotFoundException::new);
	}

	public Set<Guest> getEventsGuests(String eventId) {
		return eventRepository.findByEventId(eventId).map(Event::getGuests)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public Event updateEvent(String eventId, Event event) {
		Optional<Event> studentOptional = eventRepository.findByEventId(eventId);
		if (studentOptional.isPresent()) {
			if(studentOptional.get().getStartDate().isAfter(LocalDateTime.now())) {
				if (event.getStartDate().isAfter(LocalDateTime.now()) && event.getStartDate().isBefore(event.getEndDate())) {
					updateEventFromDB(event, studentOptional.get());
					return event;
				} else {
					throw new DateTimeException("Given Dates are not valid!");
				}
			}
			else{
				throw new DateTimeException("Start Date Has Already Passed, Can't Update");
			}
		} else {
			throw new EntityNotFoundException("Entity Not Found");
		}

	}

	private void updateEventFromDB(Event event, Event studentFromDB) {
		studentFromDB.setEventId(event.getEventId());
		studentFromDB.setTitle(event.getTitle());
		studentFromDB.setStartDate(event.getStartDate());
		studentFromDB.setEndDate(event.getEndDate());
		studentFromDB.setLocation(event.getLocation());
		studentFromDB.setQuota(event.getQuota());
	}

	public void deleteEvent(String eventId) {
		Optional<Event> studentOptional = eventRepository.findByEventId(eventId);
		if (studentOptional.isPresent()) {
			if(studentOptional.get().getStartDate().isAfter(LocalDateTime.now())) {
				eventRepository.deleteByEventId(eventId);
			}
			else {
				throw new DateTimeException("Start Date Has Already Passed, Can't Delete");
			}
		}
		else {
			throw new EntityNotFoundException();
		}
	}


	public Guest addGuestToEvent(String eventId, Guest guest) {
		Optional<Event> eventOptional = eventRepository.findByEventId(eventId);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			Set<Guest> guests = event.getGuests();

			if (event.hasFullCapacity()) {
				throw new IllegalStateException("quota is full");
			} else if (event.hasGuest(guest)) {
				throw new IllegalStateException();
			}

			guests.add(guest);
			Event savedEvent = eventRepository.save(event);
			return savedEvent
					.getGuests()
					.stream()
					.filter(it -> it.getTcKimlikNo().equals(guest.getTcKimlikNo()))
					.collect(toList())
					.get(0);
		} else {
			throw new EntityNotFoundException();
		}
	}


	public void deleteGuest(String eventId, String tcKimlikNo) {
		Optional<Event> eventOptional = eventRepository.findByEventId(eventId);
		if(eventOptional.isPresent()) {
			Event event = eventOptional.get();
			removeGuestFromEvent(tcKimlikNo, event);
			eventRepository.save(event);
		}
	}

	private void removeGuestFromEvent(String tcKimlikNo, Event event) {
		Set<Guest> filteredEvents = event.getGuests()
				.stream()
				.filter(it -> !it.getTcKimlikNo().equals(tcKimlikNo))
				.collect(toSet());

		event.getGuests().clear();
		event.getGuests().addAll(filteredEvents);
	}
}
