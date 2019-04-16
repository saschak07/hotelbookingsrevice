package com.hotelbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;
import com.hotelbooking.service.BookingService;

@RestController
public class HotelController {
	@Autowired
	private BookingService bookingService;
	@GetMapping("/booking-events")
	public ResponseEntity<List<Event>> getCalendarEvent() throws Exception{
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getEvents());
	}

}
