package com.hotelbooking.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.DateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityRequestDto {
	
 private String hotelId;
 private LocalDateTime startDate;
 private LocalDateTime endDate;
public String getHotelId() {
	return hotelId;
}
public void setHotelId(String hotelId) {
	this.hotelId = hotelId;
}
public LocalDateTime getStartDate() {
	return startDate;
}
public void setStartDate(LocalDateTime startDate) {
	this.startDate = startDate;
}
public LocalDateTime getEndDate() {
	return endDate;
}
public void setEndDate(LocalDateTime endDate) {
	this.endDate = endDate;
}

	
 
}
