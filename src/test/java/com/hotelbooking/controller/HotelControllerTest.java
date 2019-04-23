package com.hotelbooking.controller;


import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;


import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.hotelbooking.dto.AvailabilityRequestDto;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.exception.InvalidRequestException;
import com.hotelbooking.exception.NoDetailsFoundException;
import com.hotelbooking.exception.NoRoomAvailableException;
import com.hotelbooking.exceptionhandler.BookingServiceExceptionHandler;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.HotelDetailService;



@RunWith(MockitoJUnitRunner.class)
public class HotelControllerTest {

	@Mock
	private HotelDetailService hotelDetailService;
	@Mock
	private BookingService bookingService;
	private MockMvc mvc;
	@InjectMocks
	private HotelController hotelController;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(hotelController).setControllerAdvice(new BookingServiceExceptionHandler()).build();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
	}
	
	@Test
	public void test_getAllHotelDetails_happypath() throws Exception {
		HotelDto hotel = mapper.readValue(this.getClass().getResource("/all_hotels.json"), HotelDto.class);
		List<HotelDto> hotelList = new ArrayList<HotelDto>();
		hotelList.add(hotel);
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.getAllHotels()).thenReturn(Optional.of(hotelList));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all-hotels");
		mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getAllHotels();
	}
	
	@Test
	public void test_getAllHotelDetails_db_ConnectException() throws Exception {
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is5xxServerError();
		Mockito.when(hotelDetailService.getAllHotels()).thenThrow(ConnectException.class);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all-hotels");
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getAllHotels();
		
	}
	@Test
	public void test_getHotelById_HappyPath() throws Exception {

		HotelDto hotel = mapper.readValue(this.getClass().getResource("/all_hotels.json"), HotelDto.class);
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.getHotelById(Mockito.anyString())).thenReturn(Optional.of(hotel));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hotel/hotelid");
		mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getHotelById(Mockito.anyString());
		
	}
	@Test
	public void test_getHotelById_NoDetailsFoundException() throws Exception {
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is4xxClientError();
		Mockito.when(hotelDetailService.getHotelById(Mockito.anyString())).thenThrow(NoDetailsFoundException.class);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hotel/hotelid");
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getHotelById(Mockito.anyString());
		
	}
	@Test
	public void test_AddHotel_HappyPath() throws Exception {

		HotelDto hotel = mapper.readValue(this.getClass().getResource("/all_hotels.json"), HotelDto.class);
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.addHotel(Mockito.any(HotelDto.class))).thenReturn(Optional.of(hotel));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-hotel").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(hotel));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(hotelDetailService).addHotel(Mockito.any(HotelDto.class));
		
	}
	
	@Test
	public void test_AddRoom_HappyPath() throws Exception {

		HotelRoomAddRequestDto roomAddRequest = mapper.readValue(this.getClass().getResource("/add_room.json"), HotelRoomAddRequestDto.class);
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.addRoom(Mockito.any(HotelRoomAddRequestDto.class))).thenReturn(Optional.of(new HotelDto()));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-rooms").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(roomAddRequest));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(hotelDetailService).addRoom(Mockito.any(HotelRoomAddRequestDto.class));
		
	}
	@Test
	public void test_getAvailableRoom_HappyPath() throws Exception {

		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class))).thenReturn(Optional.of(new HotelDto()));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/available-rooms").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(hotelDetailService).getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class));
		
	}
	@Test
	public void test_getAvailableRoom_InvalidReqouest() throws Exception {

		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is4xxClientError();
		Mockito.when(hotelDetailService.getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class))).thenThrow(InvalidRequestException.class);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/available-rooms").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(hotelDetailService).getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class));
		
	}
	@Test
	public void test_getAvailableRoom_NoDetailsFound() throws Exception {

		AvailabilityRequestDto requestDto = new AvailabilityRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is4xxClientError();
		Mockito.when(hotelDetailService.getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class))).thenThrow(NoDetailsFoundException.class);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/available-rooms").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(hotelDetailService).getAvailableroomDetails(Mockito.any(AvailabilityRequestDto.class));
		
	}
	@Test
	public void test_bookRoom_HappyPath() throws Exception {

		BookingRequestDto requestDto = new BookingRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(bookingService.bookRoom(Mockito.any(BookingRequestDto.class))).thenReturn(Optional.of(new BookingResponseDto()));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book-room").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(bookingService).bookRoom(Mockito.any(BookingRequestDto.class));
		
	}
	
	@Test
	public void test_bookRoom_InvalidRequest() throws Exception {

		BookingRequestDto requestDto = new BookingRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is4xxClientError();
		Mockito.when(bookingService.bookRoom(Mockito.any(BookingRequestDto.class))).thenThrow(InvalidRequestException.class);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book-room").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(bookingService).bookRoom(Mockito.any(BookingRequestDto.class));
		
	}
	
	@Test
	public void test_bookRoom_NoRoomsAvailable() throws Exception {

		BookingRequestDto requestDto = new BookingRequestDto();
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().isNotFound();
		Mockito.when(bookingService.bookRoom(Mockito.any(BookingRequestDto.class))).thenThrow(NoRoomAvailableException.class);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book-room").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto));
		 mvc.perform(requestBuilder).andExpect(resultMatcher);
		
		Mockito.verify(bookingService).bookRoom(Mockito.any(BookingRequestDto.class));
		
	}
	@Test
	public void test_getBookingById_HappyPath() throws Exception {

		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is2xxSuccessful();
		Mockito.when(hotelDetailService.getBookingDetails(Mockito.anyString())).thenReturn(Optional.of(new BookingResponseDto()));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/booking/bookingId");
		mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getBookingDetails(Mockito.anyString());
		
	}
	@Test
	public void test_getBookingById_NoDetailsFound() throws Exception {

		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is4xxClientError();
		Mockito.when(hotelDetailService.getBookingDetails(Mockito.anyString())).thenThrow(NoDetailsFoundException.class);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/booking/bookingId");
		mvc.perform(requestBuilder).andExpect(resultMatcher);
		Mockito.verify(hotelDetailService).getBookingDetails(Mockito.anyString());
		
	}
}
