package com.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import com.google.api.services.calendar.model.Event;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.HotelDto;

public interface BookingService {

	List<Event> getEvents() throws Exception;

	Optional<Event> bookRoom(BookingRequestDto bookingRequest) throws Exception;

}
