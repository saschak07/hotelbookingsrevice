package com.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import com.hotelbooking.dto.HotelDto;

public interface HotelDetailService {

	Optional<List<HotelDto>> getAllHotels() throws Exception;

	Optional<HotelDto> addHotel(HotelDto hotelDto) throws Exception;

}
