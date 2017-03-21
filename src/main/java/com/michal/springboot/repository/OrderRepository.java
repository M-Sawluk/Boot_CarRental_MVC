package com.michal.springboot.repository;

import com.michal.springboot.domain.Order;
import com.michal.springboot.forms.OrderStatus;
import com.michal.springboot.service.OrderService;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface OrderRepository extends CrudRepository<Order,Long> {

	List<Order> findByRentEndBeforeAndUserId(Date presentDate,Long id);
	
	List<Order> findByRentEndAfterAndUserId(Date presentDate, Long id);

	Set<Order> findByCarIdAndRentingPlaceIdAndRentStartBetween(long carId,long pId,Date start,Date end);

	Set<Order> findByCarIdAndRentingPlaceIdAndRentEndBetween(long carId,long pId,Date start,Date end);

	Set<Order> findByCarIdAndRentingPlaceIdAndRentStartBeforeAndRentEndAfter(long carId,long pId,Date start,Date end);

	List<Order> findByStatus(OrderStatus status);

}
