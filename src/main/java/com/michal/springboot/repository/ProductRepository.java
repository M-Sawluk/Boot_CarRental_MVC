package com.michal.springboot.repository;

import com.michal.springboot.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;

/**
 * Created by Mike on 2017-03-14.
 */
public interface ProductRepository extends JpaRepository<Car,Long> {

    Car findByCarId(String carId);

    Set<Car> findByNameOrManufacturerIgnoreCase(String name , String manufacturer);

    Set<Car> findByPriceLessThan(int maxPrice);

    Set<Car> findByPriceGreaterThan(int minPrice);



}
