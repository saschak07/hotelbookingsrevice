package com.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;

public interface HotelDetailService {

	Optional<List<HotelDto>> getAllHotels() throws Exception;

	Optional<HotelDto> addHotel(HotelDto hotelDto) throws Exception;

	Optional<HotelDto> getHotelById(String hotelIds);

	Optional<HotelDto> addRoom(HotelRoomAddRequestDto roomAddRequest) throws Exception;

}
