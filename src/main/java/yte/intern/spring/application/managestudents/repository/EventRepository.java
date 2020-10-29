package yte.intern.spring.application.managestudents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.application.managestudents.entity.Event;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
	Optional<Event> findByEventId(String eventId);

	@Transactional
	void deleteByEventId(String eventId);
}

