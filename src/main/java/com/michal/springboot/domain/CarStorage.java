package com.michal.springboot.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CarStorage implements Serializable {

    private static final long serialVersionUID = -1881552477424451360L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private RentingPlace rentingPlace;

    private int units;

    @Transient
    private String placeName;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarStorage that = (CarStorage) o;

        if (id != that.id) return false;
        return units == that.units;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + units;
        return result;
    }
}
