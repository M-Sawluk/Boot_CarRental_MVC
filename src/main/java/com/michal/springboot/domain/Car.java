package com.michal.springboot.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.michal.springboot.validators.CarId;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1464566456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "C[0-9]+", message = "example: C1004")
    @CarId
    private String carId;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 ]{3,25}", message = "Min 3 chars")
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]{3,25}", message = "Min 3 chars")
    private String manufacturer;

    @NotNull
    private int price;

    @Pattern(regexp = "[a-zA-Z ]{3,25}", message = "Min 3 chars")
    private String type;

    @Pattern(regexp = "[a-zA-Z0-9./ ]{3,60}", message = "Min 3 chars")
    private String description;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private Set<CarStorage> carStorage = new HashSet<CarStorage>();

    @Transient
    private MultipartFile productImage;

    @OneToMany(mappedBy = "car", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Order> order = new HashSet<Order>();

    public Set<CarStorage> getCarStorage() {
        return carStorage;
    }

    public void setCarStorage(Set<CarStorage> carStorage) {
        this.carStorage = carStorage;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Transient
    public MultipartFile getProductImage() {
        return productImage;
    }

    @Transient
    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        return carId.equals(car.carId);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + carId.hashCode();
        return result;
    }
}
