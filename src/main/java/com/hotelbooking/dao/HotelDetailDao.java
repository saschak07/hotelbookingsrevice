package com.hotelbooking.dao;

import java.util.List;
import java.util.Optional;

import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.entity.HotelEntity;

public interface HotelDetailDao {

	Optional<List<HotelDto>> getAllHotelDetails() throws Exception;

	Optional<HotelDto> addHotel(HotelEntity hotel) throws Exception;

}
