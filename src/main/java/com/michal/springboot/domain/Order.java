package com.michal.springboot.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.michal.springboot.forms.OrderStatus;
import com.michal.springboot.validators.RentingDateRange;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
@RentingDateRange
public class Order implements Serializable {

    private static final long serialVersionUID = -6389653771904477133L;

    @Id
    @GeneratedValue
    private long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    @Future
    @NotNull
    private Date rentStart;

    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    @NotNull
    @Future
    private Date rentEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private RentingPlace rentingPlace;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @SuppressWarnings("unused")
    private int price;

    //Helpers

    @Transient
    private List<Car> cars;

    @Transient
    private List<RentingPlace> places;

    @Transient
    @Pattern(regexp = "C[0-9]+", message = "Please select car")
    private String selectedCar;

    @Transient
    @Digits(integer = 3, fraction = 0, message = "Please select city")
    private String selectedPlace;

    //End of Helpers

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getRentStart() {
        return rentStart;
    }

    public void setRentStart(Date rentStart) {
        this.rentStart = rentStart;
    }

    public Date getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(Date rentEnd) {
        this.rentEnd = rentEnd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public RentingPlace getRentingPlace() {
        return rentingPlace;
    }

    public void setRentingPlace(RentingPlace rentingPlace) {
        this.rentingPlace = rentingPlace;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<RentingPlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<RentingPlace> places) {
        this.places = places;
    }

    public String getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(String selectedCar) {
        this.selectedCar = selectedCar;
    }

    public String getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(String selectedPlace) {
        this.selectedPlace = selectedPlace;
    }

    public int getPrice() {

        return (int) ((rentEnd.getTime() - rentStart.getTime()) / (1000 * 60 * 60 * 24)) * car.getPrice();
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(getId(), order.getId())
                .append(getRentStart(), order.getRentStart())
                .append(getRentEnd(), order.getRentEnd())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getRentStart())
                .append(getRentEnd())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", rentStart=" + rentStart +
                ", rentEnd=" + rentEnd +
                ", user=" + user +
                ", car=" + car +
                ", rentingPlace=" + rentingPlace +
                ", status=" + status +
                ", price=" + price +
                ", cars=" + cars +
                ", places=" + places +
                ", selectedCar='" + selectedCar + '\'' +
                ", selectedPlace='" + selectedPlace + '\'' +
                '}';
    }
}
