package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long event_id) {
        return eventRepository.findById(event_id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long event_id, Event event) {
        return eventRepository.findById(event_id).map(existingEvent -> {
            existingEvent.setTitle(event.getTitle());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStatus(event.getStatus());
            existingEvent.setStart_date(event.getStart_date());
            existingEvent.setEnd_date(event.getEnd_date());
            existingEvent.setFoundation(event.getFoundation());
            return eventRepository.save(existingEvent);
        }).orElseThrow(() -> new RuntimeException("Event not found with id: " + event_id));
    }

    @Override
    public void deleteEvent(Long event_id) {
        eventRepository.deleteById(event_id);
    }

    @Override
    public List<Event> searchByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Event> searchByStatus(String status) {
        return eventRepository.findByStatus(status);
    }

    @Override
    public List<Event> searchByStartDate(Date start_date) {
        return eventRepository.findByStartDate(start_date);
    }

    @Override
    public List<Event> searchByLocation(String location) {
        return eventRepository.findByLocation(location);
    }
}