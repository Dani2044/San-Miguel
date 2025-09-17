package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Event;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long event_id);
    Event createEvent(Event event);
    Event updateEvent(Long event_id, Event event);
    void deleteEvent(Long event_id);
    List<Event> searchByTitle(String title);
    List<Event> searchByStatus(String status);
    List<Event> searchByStartDate(Date start_date);
    List<Event> searchByLocation(String location);
}