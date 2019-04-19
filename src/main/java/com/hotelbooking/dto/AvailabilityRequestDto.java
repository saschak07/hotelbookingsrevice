package com.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.DateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityRequestDto {
	
 private String hotelId;
 private DateTime startDate;
 private DateTime endDate;
public String getHotelId() {
	return hotelId;
}
public void setHotelId(String hotelId) {
	this.hotelId = hotelId;
}
public DateTime getStartDate() {
	return startDate;
}
public void setStartDate(DateTime startDate) {
	this.startDate = startDate;
}
public DateTime getEndDate() {
	return endDate;
}
public void setEndDate(DateTime endDate) {
	this.endDate = endDate;
}
	
 
}
