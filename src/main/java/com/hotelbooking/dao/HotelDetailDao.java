package com.hotelbooking.dao;

import java.util.List;
import java.util.Optional;

import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelEntity;

public interface HotelDetailDao {

	Optional<List<HotelDto>> getAllHotelDetails() throws Exception;

	Optional<HotelDto> addHotel(HotelEntity hotel) throws Exception;

	Optional<HotelDto> getHotelById(String hotelIds);

	Optional<HotelDto> addRoom(HotelRoomAddRequestDto roomAddRequest) throws Exception;

	void bookRoom(BookingRequestDto bookingRequest) throws Exception;

}
