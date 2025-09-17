package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Event;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long eventId);
    Event createEvent(Event event);
    Event updateEvent(Long eventId, Event event);
    void deleteEvent(Long eventId);
    List<Event> searchByTitle(String title);
    List<Event> searchByStatus(String status);
    List<Event> searchByStartDate(Date startDate);
    List<Event> searchByLocation(String location);
}