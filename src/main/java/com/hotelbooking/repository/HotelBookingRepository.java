package com.hotelbooking.repository;

import org.springframework.data.repository.CrudRepository;

import com.hotelbooking.entity.HotelBookingEntity;

public interface HotelBookingRepository extends CrudRepository<HotelBookingEntity, String> {

}
