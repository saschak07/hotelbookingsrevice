package com.hotelbooking.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.api.client.util.DateTime;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.HotelDto;
import com.hotelbooking.dto.HotelRoomAddRequestDto;
import com.hotelbooking.entity.HotelBookingEntity;
import com.hotelbooking.entity.HotelEntity;
import com.hotelbooking.entity.RoomEntity;
@Component
public class ConversionUtil extends ModelMapper{
	 @Value("${timeZone.id}")
	 private String TIME_ZONE="Asia/Bangkok";

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
					.getValue()), ZoneId.of(TIME_ZONE)));
		bookingEntity.setEndDate(LocalDateTime.
				ofInstant(Instant.ofEpochMilli(bookingRequest.getEndDate()
				.getValue()), ZoneId.of(TIME_ZONE)));
		return bookingEntity;
	}

	public BookingResponseDto convertBookingEntityToResponse(Optional<HotelBookingEntity> optionalBookingEntity) {
		
		return super.map(optionalBookingEntity.get(), BookingResponseDto.class);
	}
	
	public LocalDateTime convertToLocalDateTime(DateTime dateTime) {
		return LocalDateTime.
				ofInstant(Instant.ofEpochMilli(dateTime
				.getValue()), ZoneId.of(TIME_ZONE));
	}

}
