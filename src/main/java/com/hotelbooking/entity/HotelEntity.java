package com.hotelbooking.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hotels")
public class HotelEntity {

	@Id
	private String hotelIds = UUID.randomUUID().toString();
	private String hotelName;
	private String address;
	private String contactNo;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_ref_id" , nullable = true)
	private List<RoomEntity> rooms;
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
	public List<RoomEntity> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
	}
	
	
}
