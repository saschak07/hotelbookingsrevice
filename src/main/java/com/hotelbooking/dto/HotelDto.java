package com.hotelbooking.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto {

	private String hotelIds ;
	private String hotelName;
	private String address;
	private String contactNo;
	private List<RoomDto> rooms;
	public String getHotelIds() {
		return hotelIds;
	}
	public void setHotelIds(String hotelIds) {
		this.hotelIds = hotelIds;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public List<RoomDto> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomDto> rooms) {
		this.rooms = rooms;
	}
	
	
}
