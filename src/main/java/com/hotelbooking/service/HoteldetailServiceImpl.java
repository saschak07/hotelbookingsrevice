package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.dao.HotelDetailDao;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.entity.RoomEntity;
import com.hotelbooking.repository.HotelDetailsRepository;
import com.hotelbooking.util.ConversionUtil;
@Service
public class HoteldetailServiceImpl implements HotelDetailService{

	@Autowired
	private HotelDetailDao hotelDetailDao;
	@Autowired
	private ConversionUtil conversionUtil;
	
	@PostConstruct
	private void loadData() throws Exception {
		RoomEntity roomEntity = new RoomEntity();
		roomEntity.setRoomNumber("101");
		roomEntity.setRoomType("single");
		roomEntity.setAmenities("tv||wifi||minibar");
		List<RoomEntity> roomList = new ArrayList<RoomEntity>();
		roomList.add(roomEntity);
		HotelEntity hotel = new HotelEntity();
		hotel.setHotelName("Hotel Mayura");
		hotel.setAddress("101, RoyalMinakshi road, Majestic, Bangalore-560001");
		hotel.setContactNo("+919108111757");
		hotel.setRooms(roomList);
		hotelDetailDao.addHotel(hotel);
	}
	
	@Override
	public Optional<List<HotelDto>> getAllHotels() throws Exception{
		return hotelDetailDao.getAllHotelDetails();
	}

	@Override
	public Optional<HotelDto> addHotel(HotelDto hotelDto) throws Exception {
		return hotelDetailDao.addHotel(conversionUtil.convertHotelDtoToEntity(hotelDto));
	}

}
