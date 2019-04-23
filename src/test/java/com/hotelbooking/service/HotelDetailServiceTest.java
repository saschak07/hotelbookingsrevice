package com.hotelbooking.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.relation.InvalidRelationIdException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.HotelDetailDao;
import com.hotelbooking.dto.AvailabilityRequestDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelBookingEntity;
import com.hotelbooking.exception.InvalidRequestException;
import com.hotelbooking.exception.NoDetailsFoundException;
import com.hotelbooking.util.ConversionUtil;



@RunWith(MockitoJUnitRunner.class)
public class HotelDetailServiceTest {

	@Mock
	private HotelDetailDao hotelDetailDao;
	@Spy
	private ConversionUtil conversionUtil;
	@InjectMocks
	private HotelDetailService hotelDetailService = new HoteldetailServiceImpl();
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void Test_Addroom_HappyPath() throws Exception {
		HotelRoomAddRequestDto requestDto = new HotelRoomAddRequestDto();
		requestDto.setHotelIds("xxxx");
		Mockito.when(hotelDetailDao.getHotelById(requestDto.getHotelIds())).thenReturn(Optional.of(new HotelDto()));
		Mockito.when(hotelDetailDao.addRoom(requestDto)).thenReturn(Optional.of(new HotelDto()));
		hotelDetailService.addRoom(requestDto);
		Mockito.verify(hotelDetailDao).getHotelById(Mockito.anyString());
		Mockito.verify(hotelDetailDao).addRoom(Mockito.any(HotelRoomAddRequestDto.class));
	}
	@Test(expected = NoDetailsFoundException.class)
	public void Test_Addroom_NoDetailsfound() throws Exception {
		HotelRoomAddRequestDto requestDto = new HotelRoomAddRequestDto();
		requestDto.setHotelIds("xxxx");
		Mockito.when(hotelDetailDao.getHotelById(requestDto.getHotelIds())).thenReturn(Optional.empty());
		
		hotelDetailService.addRoom(requestDto);
		
		Mockito.verify(hotelDetailDao).getHotelById(Mockito.anyString());
		Mockito.verifyZeroInteractions(hotelDetailDao.addRoom(Mockito.any(HotelRoomAddRequestDto.class)));
	}
	@Test
	public void test_getavailableRoomDetails_happypath() throws Exception {
		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		requestDto.setHotelId("xxxx");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		requestDto.setStartDate(LocalDateTime.parse("2019-04-27 11:30:40",formatter));
		requestDto.setEndDate(LocalDateTime.parse("2019-04-28 11:30:40",formatter));
		HotelDto hotelDto = mapper.readValue(this.getClass().getResource("/Hotel_multipleRoom.json"), HotelDto.class);
		HotelBookingEntity booking = new HotelBookingEntity();
		booking.setRoomNo("101");
		List<HotelBookingEntity> bookingList = new ArrayList<HotelBookingEntity>();
		bookingList.add(booking);
		Mockito.when(hotelDetailDao.getHotelById(requestDto.getHotelId())).thenReturn(Optional.of(hotelDto));
		Mockito.when(hotelDetailDao.getBookedRoomDetails(requestDto.getHotelId(), 
				requestDto.getStartDate(), requestDto.getEndDate())).thenReturn(Optional.of(bookingList));
		hotelDetailService.getAvailableroomDetails(requestDto);
		
		Mockito.verify(hotelDetailDao).getHotelById(Mockito.anyString());
		Mockito.verify(hotelDetailDao).getBookedRoomDetails(Mockito.anyString(), Mockito.any(), Mockito.any());
	}
	@Test(expected = InvalidRequestException.class)
	public void test_getavailableRoomDetails_InvalidRequest() throws Exception {
		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		requestDto.setHotelId("xxxx");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		requestDto.setStartDate(LocalDateTime.parse("2019-04-27 11:30:40",formatter));
		requestDto.setEndDate(LocalDateTime.parse("2019-04-26 11:30:40",formatter));
		
		hotelDetailService.getAvailableroomDetails(requestDto);
		
		Mockito.verifyZeroInteractions(hotelDetailDao.getHotelById(Mockito.anyString()));
		Mockito.verifyZeroInteractions((hotelDetailDao.getBookedRoomDetails(Mockito.anyString(), Mockito.any(), Mockito.any())));
	}
	@Test(expected = NoDetailsFoundException.class)
	public void test_getavailableRoomDetails_NodetailsFound() throws Exception {
		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		requestDto.setHotelId("xxxx");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		requestDto.setStartDate(LocalDateTime.parse("2019-04-27 11:30:40",formatter));
		requestDto.setEndDate(LocalDateTime.parse("2019-04-28 11:30:40",formatter));
		Mockito.when(hotelDetailDao.getHotelById(requestDto.getHotelId())).thenReturn(Optional.empty());
		
		hotelDetailService.getAvailableroomDetails(requestDto);
		
		Mockito.verify(hotelDetailDao).getHotelById(Mockito.anyString());
		Mockito.verifyZeroInteractions((hotelDetailDao.getBookedRoomDetails(Mockito.anyString(), Mockito.any(), Mockito.any())));
	}
	 
}
