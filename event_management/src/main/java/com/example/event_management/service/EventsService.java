package com.example.event_management.service;

import com.example.event_management.exception.UnAuthorisedException;
import com.example.event_management.exception.ValidationException;
import com.example.event_management.model.Events;
import com.example.event_management.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UserEventService userEventService;

    public List<Events>  findAll() {
        return eventsRepository.findAll();
    }

    public List<Events>  findEventsWithPagination(int pageNumber, int limit, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);

        Page<Events> page = eventsRepository.findAll(PageRequest.of(pageNumber, limit, sort));

        return page.getContent();
    }

    public Events getById(String id) {
        Events event = eventsRepository.findById(id).orElseThrow(() -> new UnAuthorisedException("Event not found"));

        return event;
    }

    public Events save(Events event) {
        if(event.getTotalSeats() < 1){
            throw new ValidationException("'totalSeats' should be greater than 0");
        }

        event.setId(UUID.randomUUID().toString());
        event.setSeatsLeft(event.getTotalSeats());

        event = eventsRepository.save(event);

        return event;
    }

    public Events updateById(Events updatedEvent) {
        if(updatedEvent.getTotalSeats() < 1){
            throw new ValidationException("'totalSeats' should be greater than 0");
        }

        Events event = eventsRepository.findById(updatedEvent.getId()).orElseThrow(() -> new UnAuthorisedException("Event not found"));

        if(updatedEvent.getTotalSeats() < event.getTotalSeats() - event.getSeatsLeft()){
            throw new ValidationException("'totalSeats' can not be lesser than seatsLeft");
        }

        event.setName(updatedEvent.getName());
        event.setDate(updatedEvent.getDate());
        event.setTime(updatedEvent.getTime());
        event.setLocation(updatedEvent.getLocation());

        if(updatedEvent.getTotalSeats() > event.getTotalSeats()){
            event.setSeatsLeft(event.getSeatsLeft() + (updatedEvent.getTotalSeats() - event.getTotalSeats()));
        }else if(updatedEvent.getTotalSeats() < event.getTotalSeats()){
            event.setSeatsLeft(updatedEvent.getTotalSeats() - (event.getTotalSeats() - event.getSeatsLeft()));
        }

        event.setTotalSeats(updatedEvent.getTotalSeats());

        return eventsRepository.save(event);
    }

    public void deleteById(String id) {
        eventsRepository.deleteById(id);

        userEventService.deleteByEventId(id);
    }

}
