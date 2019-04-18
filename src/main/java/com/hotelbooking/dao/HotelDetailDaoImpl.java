package com.hotelbooking.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.repository.HotelDetailsRepository;
import com.hotelbooking.util.ConversionUtil;
@Service
public class HotelDetailDaoImpl implements HotelDetailDao{

	@Autowired
	private HotelDetailsRepository hotelDetailRepository;
	@Autowired
	private ConversionUtil conversionUtil;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public Optional<List<HotelDto>> getAllHotelDetails() throws Exception {
		List<HotelDto> hotelList = new ArrayList<HotelDto>();
		try {
			hotelDetailRepository.findAll().forEach(hotel -> hotelList.add(conversionUtil.convertToHotelDtoFromEntity(hotel)));
		}catch (Exception e) {
			logger.error("Error retrieving all hotel details", e);
			throw e;
		}
		return Optional.of(hotelList);
	}
	@Override
	public Optional<HotelDto> addHotel(HotelEntity hotel) throws Exception{
		Optional<HotelDto> optionalHotel = Optional.empty();
		try {
			optionalHotel = Optional.of(conversionUtil.convertToHotelDtoFromEntity(hotelDetailRepository.save(hotel)));
		}catch(Exception e) {
			logger.error("Error while adding new hotel", e);
			throw e;
		}
		return optionalHotel;
	}

}
