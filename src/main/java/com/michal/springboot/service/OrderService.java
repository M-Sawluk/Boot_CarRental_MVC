package com.michal.springboot.service;

import com.michal.springboot.domain.Order;
import com.michal.springboot.domain.User;
import com.michal.springboot.exception.InvalidCarException;
import com.michal.springboot.forms.OrderStatus;
import com.michal.springboot.repository.CarStorageRepository;
import com.michal.springboot.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private CarStorageRepository carStorageRepository;

    public OrderService(OrderRepository orderRepository, CarStorageRepository carStorageRepository) {
        this.orderRepository = orderRepository;
        this.carStorageRepository = carStorageRepository;
    }

    public Order validOrder(Order order) {

        Set<Order> set = new HashSet<>();

        orderRepository.findByCarIdAndRentingPlaceIdAndRentEndBetween(
                order.getCar().getId(), order.getRentingPlace().getId(),
                order.getRentStart(), order.getRentEnd())
                .forEach(set::add);

        orderRepository.findByCarIdAndRentingPlaceIdAndRentStartBetween(
                order.getCar().getId(), order.getRentingPlace().getId(),
                order.getRentStart(), order.getRentEnd())
                .forEach(set::add);

        orderRepository.findByCarIdAndRentingPlaceIdAndRentStartBeforeAndRentEndAfter(
                order.getCar().getId(), order.getRentingPlace().getId(),
                order.getRentStart(), order.getRentEnd())
                .forEach(set::add);

        if (set.size() >= carStorageRepository
                .findByCarIdAndRentingPlaceId(order.getCar().getId(), order.getRentingPlace().getId())
                .getUnits()) {
            throw new InvalidCarException();
        }

        return order;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getPastOrders(User user) {
        return orderRepository.findByRentEndBeforeAndUserId(new Date(), user.getId());
    }

    public List<Order> getPresentOrders(User user) {
        return orderRepository.findByRentEndAfterAndUserId(new Date(), user.getId());
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public void deleteOrder(long id) {
        orderRepository.delete(id);
    }

    public Order findOrderById(long id) {
        return orderRepository.findOne(id);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);

    }

    public int getOrdersToManage(){
        return orderRepository.findByStatus(OrderStatus.ACTIVE).size();
    }
}
