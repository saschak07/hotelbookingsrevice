package com.hotelbooking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.google.api.client.util.DateTime;
import com.hotelbooking.entity.HotelBookingEntity;

public interface HotelBookingRepository extends CrudRepository<HotelBookingEntity, String> {

	@Query(value = "select b from HotelBookingEntity b where b.startDate " + 
			"BETWEEN :start AND :end " + 
			"OR b.endDate BETWEEN :start AND :end" + 
			" and b.hotelId = :id")
	Optional<List<HotelBookingEntity>> getAllBookedRoom(@Param("id") String hotelId, 
			@Param("start") LocalDateTime localDateTime, @Param("end") LocalDateTime localDateTime2);

}
