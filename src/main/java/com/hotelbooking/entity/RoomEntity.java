package com.hotelbooking.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity {

	@Id
	private String roomIds = UUID.randomUUID().toString();
	private String roomNumber;
	private String roomType ;
	private String amenities;
	private String roomImage;
	
	public String getRoomImage() {
		return roomImage;
	}
	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}
	public String getRoomIds() {
		return roomIds;
	}
	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getAmenities() {
		return amenities;
	}
	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	
	
}
