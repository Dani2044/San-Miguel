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
    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long eventId, Event event) {
        return eventRepository.findById(eventId).map(existingEvent -> {
            existingEvent.setTitle(event.getTitle());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStatus(event.getStatus());
            existingEvent.setStartDate(event.getStartDate());
            existingEvent.setEndDate(event.getEndDate());
            existingEvent.setFoundation(event.getFoundation());
            return eventRepository.save(existingEvent);
        }).orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
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
    public List<Event> searchByStartDate(Date startDate) {
        return eventRepository.findByStartDate(startDate);
    }

    @Override
    public List<Event> searchByLocation(String location) {
        return eventRepository.findByLocation(location);
    }
}