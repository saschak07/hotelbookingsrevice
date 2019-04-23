package com.hotelbooking.repository;

import org.springframework.data.repository.CrudRepository;

import com.hotelbooking.entity.HotelEntity;

public interface HotelDetailsRepository extends CrudRepository<HotelEntity, String> {

}
