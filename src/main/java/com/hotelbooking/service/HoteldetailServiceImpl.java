package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.HotelDetailDao;
import com.hotelbooking.dto.AvailabilityRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.dto.RoomDto;
import com.hotelbooking.entity.HotelBookingEntity;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.entity.RoomEntity;
import com.hotelbooking.exception.InvalidRequestException;
import com.hotelbooking.exception.NoDetailsFoundException;
import com.hotelbooking.repository.HotelDetailsRepository;
import com.hotelbooking.util.ConversionUtil;
@Service
public class HoteldetailServiceImpl implements HotelDetailService{

	@Autowired
	private HotelDetailDao hotelDetailDao;
	@Autowired
	private ConversionUtil conversionUtil;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*
	 * @PostConstruct private void loadData() throws Exception { RoomEntity
	 * roomEntity = new RoomEntity(); roomEntity.setRoomNumber("102");
	 * roomEntity.setRoomType("single");
	 * roomEntity.setAmenities("tv||wifi||minibar"); List<RoomEntity> roomList = new
	 * ArrayList<RoomEntity>(); roomList.add(roomEntity); HotelEntity hotel = new
	 * HotelEntity(); hotel.setHotelName("Hotel Mayura");
	 * hotel.setAddress("101, RoyalMinakshi road, Majestic, Bangalore-560001");
	 * hotel.setContactNo("+919108111757"); hotel.setRooms(roomList);
	 * hotelDetailDao.addHotel(hotel); }
	 */
	
	 
	
	@Override
	public Optional<List<HotelDto>> getAllHotels() throws Exception{
		return hotelDetailDao.getAllHotelDetails();
	}

	@Override
	public Optional<HotelDto> addHotel(HotelDto hotelDto) throws Exception {
		return hotelDetailDao.addHotel(conversionUtil.convertHotelDtoToEntity(hotelDto));
	}

	@Override
	public Optional<HotelDto> getHotelById(String hotelIds) {
		return hotelDetailDao.getHotelById(hotelIds);
	}

	@Override
	public Optional<HotelDto> addRoom(HotelRoomAddRequestDto roomAddRequest) throws Exception{
		Optional<HotelDto> optioanlHotelDto = hotelDetailDao.getHotelById(roomAddRequest.getHotelIds());
		if(!optioanlHotelDto.isPresent()) {
			throw new NoDetailsFoundException("Hotel Does not exist");
		}
		return hotelDetailDao.addRoom(roomAddRequest);
	}

	@Override
	public Optional<HotelDto> getAvailableroomDetails(AvailabilityRequestDto availabilityRequest) throws Exception
	{
		if(!validRequest(availabilityRequest)) {
			throw new InvalidRequestException("invalid start and end date");
		}
		Optional<HotelDto> optionalHotel = Optional.empty();
		try {
			optionalHotel = hotelDetailDao.getHotelById(availabilityRequest.getHotelId());
			if(!optionalHotel.isPresent()) {
				throw new NoDetailsFoundException("details not found for hotelId:"+availabilityRequest.getHotelId());
			}
			logger.info("checking availability for"+new ObjectMapper().writeValueAsString(availabilityRequest));
			Optional<List<HotelBookingEntity>> bookedRoomListOptional = hotelDetailDao.getBookedRoomDetails(availabilityRequest.getHotelId(),
					conversionUtil.convertToLocalDateTime(availabilityRequest.getStartDate()),
					conversionUtil.convertToLocalDateTime(availabilityRequest.getEndDate()));
			if(bookedRoomListOptional.isPresent()) {
				for(HotelBookingEntity booking:bookedRoomListOptional.get()) {
					Optional<RoomDto> optionlRoom = optionalHotel.get().getRooms().stream().
							filter(room -> room.getRoomNumber().equals(booking.getRoomNo())).findFirst();
					optionalHotel.get().getRooms().remove(optionlRoom.get());
				}
			}
		}catch(Exception e) {
			logger.error("error while retrieving available roomdetails",e);
			throw e;
		}
		return optionalHotel;
	}

	private boolean validRequest(AvailabilityRequestDto availabilityRequest) {
		if(conversionUtil.convertToLocalDateTime(availabilityRequest.getEndDate())
				.isAfter(conversionUtil.convertToLocalDateTime(availabilityRequest.getStartDate()))) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<BookingResponseDto> getBookingDetails(String bookingId) throws Exception {
		
		return hotelDetailDao.getBookingDetails(bookingId);
	}
	
	}
