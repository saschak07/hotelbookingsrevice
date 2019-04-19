package com.hotelbooking.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelBookingEntity;
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

	public HotelBookingEntity convertToBookingEntityFromDto(BookingRequestDto bookingRequest) {
		bookingRequest.setBookingIds(UUID.randomUUID().toString());
		HotelBookingEntity bookingEntity = super.map(bookingRequest, HotelBookingEntity.class);
		bookingEntity.setStartDate(LocalDateTime.
					ofInstant(Instant.ofEpochMilli(bookingRequest.getStartDate()
					.getValue()), ZoneId.of("America/Los_Angeles")));
		bookingEntity.setEndDate(LocalDateTime.
				ofInstant(Instant.ofEpochMilli(bookingRequest.getEndDate()
				.getValue()), ZoneId.of("America/Los_Angeles")));
		return bookingEntity;
	}

}
