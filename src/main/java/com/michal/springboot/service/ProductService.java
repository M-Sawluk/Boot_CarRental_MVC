package com.michal.springboot.service;

import java.util.*;

import com.michal.springboot.domain.Car;
import com.michal.springboot.domain.Order;
import com.michal.springboot.forms.CarStorageForm;
import com.michal.springboot.repository.CarStorageRepository;
import com.michal.springboot.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CarStorageRepository carStorageRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository, CarStorageRepository carStorageRepository) {
        this.productRepository = productRepository;
        this.carStorageRepository = carStorageRepository;
    }

    public void save(Car car) {
        productRepository.save(car);
    }

    public Car findByCarId(String carId) {
        return productRepository.findByCarId(carId);
    }

    public List<Car> getCarForCarousel() {

        List<Car> allCars = (List<Car>) productRepository.findAll();
        Collections.shuffle(allCars);
        return allCars.subList(0, 4);
    }

    public List<Car> findAll() {
        return (List<Car>) productRepository.findAll();
    }

    public Set<Car> findByNameOrManufacturer(String name, String manufacturer) {
        return productRepository.findByNameOrManufacturerIgnoreCase(name,manufacturer);
    }

    public Set<Car> getProductsByFilter(int max,int min, String name) {

        Set<Car> cars = productRepository.findByPriceGreaterThan(min);

        if(max>0){
            cars.retainAll(productRepository.findByPriceLessThan(max));
        }
        if(name!=null){
            cars.retainAll(productRepository.findByNameOrManufacturerIgnoreCase(name,name));
        }

        return cars;
    }

    public void deleteCar(String carId) {
        productRepository.delete(productRepository.findByCarId(carId));
    }

    public void updateCar(Car newCar) {
        productRepository.save(newCar);
    }

    public Car fillCar(Order order) {

        if (order.getSelectedCar() == null) {
            return order.getCar();
        } else {
            return findByCarId(order.getSelectedCar());
        }
    }

    public List<Car> getPagableCars(){

        List<Car> cars = new ArrayList<>();
        productRepository.findAll(new PageRequest(1,10)).forEach(cars::add);

        return cars;
    }

}
