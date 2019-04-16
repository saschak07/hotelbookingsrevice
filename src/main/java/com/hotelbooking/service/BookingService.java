package com.hotelbooking.service;

import java.util.List;

import com.google.api.services.calendar.model.Event;

public interface BookingService {

	List<Event> getEvents() throws Exception;

}
