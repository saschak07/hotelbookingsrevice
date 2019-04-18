package com.hotelbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.HotelDetailService;

@RestController
public class HotelController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private HotelDetailService hotelDetailService;
	@GetMapping("/booking-events")
	public ResponseEntity<List<Event>> getCalendarEvent() throws Exception{
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getEvents());
	}
	
	@GetMapping("/all-hotels")
	public ResponseEntity<List<HotelDto>> getAllHotelDetails() throws Exception{
		Optional<List<HotelDto>> optionalHotels = hotelDetailService.getAllHotels();
		return ResponseEntity.status(HttpStatus.OK).body(optionalHotels.get());
	}
	@PostMapping("/add-hotel")
	public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotelDto) throws Exception{
		Optional<HotelDto> optionalHotel = hotelDetailService.addHotel(hotelDto);
		return ResponseEntity.status(HttpStatus.OK).body(optionalHotel.get());
	}

}
