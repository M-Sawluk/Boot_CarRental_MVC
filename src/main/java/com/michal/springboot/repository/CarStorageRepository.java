package com.michal.springboot.repository;

import com.michal.springboot.domain.CarStorage;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mike on 2017-03-16.
 */
public interface CarStorageRepository extends CrudRepository<CarStorage,Long>{

	CarStorage findByCarIdAndRentingPlaceId(long carId, long placeId);
}
