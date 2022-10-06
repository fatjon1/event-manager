package com.fatjon.eventmanager.model.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepo extends PagingAndSortingRepository<Event, Long> {
    Event findEventByCategory(String category);
}
