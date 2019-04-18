package com.hotelbooking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.entity.RoomEntity;
@Component
public class ConversionUtil extends ModelMapper{

	public HotelDto convertToHotelDtoFromEntity(HotelEntity hotel) {
		return super.map(hotel, HotelDto.class);
	}

	public HotelEntity convertHotelDtoToEntity(HotelDto hotelDto) {
		hotelDto.setHotelIds(UUID.randomUUID().toString());
		HotelEntity hotelEntity = super.map(hotelDto, HotelEntity.class);
		List<RoomEntity> roomEntityList = new ArrayList<RoomEntity>();
		hotelDto.getRooms().forEach(room -> {
			room.setRoomIds(UUID.randomUUID().toString());
			roomEntityList.add(super.map(room, RoomEntity.class));
		});
		hotelEntity.setRooms(roomEntityList);
		 return hotelEntity;
	}

	public RoomEntity convertRoomEntityFromRoomAddRequest(HotelRoomAddRequestDto roomAddRequest) {
		roomAddRequest.setRoomIds(UUID.randomUUID().toString());
		return super.map(roomAddRequest, RoomEntity.class);
	}

}
