package com.hotelbooking.controller;


import java.io.IOException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.exceptionhandler.BookingServiceExceptionHandler;
import com.hotelbooking.service.HotelDetailService;

@RunWith(MockitoJUnitRunner.class)
public class HotelControllerTest {

	@Mock
	private HotelDetailService hotelDetailService;
	private MockMvc mvc;
	@InjectMocks
	private HotelController hotelController;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(hotelController).setControllerAdvice(new BookingServiceExceptionHandler()).build();
		
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
	
}
