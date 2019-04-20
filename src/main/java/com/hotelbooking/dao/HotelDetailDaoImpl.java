package com.hotelbooking.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dto.AvailabilityRequestDto;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelBookingEntity;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.entity.RoomEntity;
import com.hotelbooking.repository.HotelBookingRepository;
import com.hotelbooking.repository.HotelDetailsRepository;
import com.hotelbooking.util.ConversionUtil;
@Service
public class HotelDetailDaoImpl implements HotelDetailDao{

	@Autowired
	private HotelDetailsRepository hotelDetailRepository;
	@Autowired
	private HotelBookingRepository hotelBookingrepository;
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
	@Override
	public Optional<HotelDto> getHotelById(String hotelIds) {
		Optional<HotelDto> optionalHotel = Optional.empty();
		try {
		 optionalHotel = Optional.of(conversionUtil.convertToHotelDtoFromEntity(hotelDetailRepository.findById(hotelIds).get()));
		}catch(Exception e) {
			logger.error("Error while retrieving: hotel:"+hotelIds, e);
			throw e;
		}
		return optionalHotel;
	}
	@Override
	public Optional<HotelDto> addRoom(HotelRoomAddRequestDto roomAddRequest) throws Exception{
		Optional<HotelDto> hotelResponseOptional = Optional.empty();
		try {
		Optional<HotelEntity> hotelEntityOptional = hotelDetailRepository.findById(roomAddRequest.getHotelIds());
		List<RoomEntity> roomListFromDataStore = hotelEntityOptional.get().getRooms();
		roomListFromDataStore.add(conversionUtil.convertRoomEntityFromRoomAddRequest(roomAddRequest));
		hotelEntityOptional.get().setRooms(roomListFromDataStore);
		hotelResponseOptional = Optional.of(conversionUtil.convertToHotelDtoFromEntity(hotelDetailRepository.save(hotelEntityOptional.get())));
		}catch(Exception e) {
			logger.error("Error while adding room to hotel:"+roomAddRequest.getHotelIds(), e);
			throw e;
		}
		return hotelResponseOptional;
	}
	@Override
	public void bookRoom(BookingRequestDto bookingRequest) throws Exception {
		try {
			hotelBookingrepository.save(conversionUtil.convertToBookingEntityFromDto(bookingRequest));
		}catch(Exception e) {
			logger.error("Error while saving booking details:"+new ObjectMapper().writeValueAsString(bookingRequest),e);
			throw e;
		}
		
	}
	@Override
	public Optional<List<HotelBookingEntity>> getBookedRoomDetails(String hotelId, 
			LocalDateTime startDate, LocalDateTime endDate) {
		logger.info("looking for booked rooms for hotelId"+hotelId);
		 Optional<List<HotelBookingEntity>> bookedRoomList =
		 hotelBookingrepository.getAllBookedRoom(hotelId,
				 startDate,endDate);
		 
		return bookedRoomList;
	}

}
