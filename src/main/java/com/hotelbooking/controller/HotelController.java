package com.hotelbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;
import com.hotelbooking.dto.AvailabilityRequestDto;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
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
	@GetMapping("/hotel/{hotelIds}")
	public ResponseEntity<HotelDto> getHotelById(@PathVariable String hotelIds){
		return ResponseEntity.status(HttpStatus.OK).body(hotelDetailService.getHotelById(hotelIds).get());
	}
	@PostMapping("/add-rooms")
	public ResponseEntity<HotelDto> addHotelRooms(@RequestBody HotelRoomAddRequestDto roomAddRequest) throws Exception{
		Optional<HotelDto> optionalHotel = hotelDetailService.addRoom(roomAddRequest);
		return ResponseEntity.status(HttpStatus.OK).body(optionalHotel.get());
	}
	@PostMapping("/book-room")
	public ResponseEntity<BookingResponseDto> bookRoom(@RequestBody BookingRequestDto bookingRequest) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.bookRoom(bookingRequest).get());
	}
	@PostMapping("/available-rooms")
	public ResponseEntity<HotelDto> getAvailaleRooms(@RequestBody AvailabilityRequestDto availabilityRequest) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(hotelDetailService.
				getAvailableroomDetails(availabilityRequest).get());
	}
	
	 @GetMapping("/booking/{bookingId}") 
	 public ResponseEntity<BookingResponseDto>getBookingDetailsForHotelId(@PathVariable String bookingId){
		 return ResponseEntity.status(HttpStatus.OK).body(hotelDetailService.getBookingDetails(bookingId).get());
	 }
	 
	

}
