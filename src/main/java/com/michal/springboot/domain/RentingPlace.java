package com.michal.springboot.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

@Entity
public class RentingPlace implements Serializable
{

	private static final long serialVersionUID = 7282658533765530602L;

	@GeneratedValue
	@Id
	private long id;
	
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "Min 3 chars max 12")
	private String city;
	
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "Min 3 chars max 12")
	private String street;
	
	@Pattern(regexp = "[a-zA-Z0-9]{1,12}", message = "Min 3 chars max 12")
	private String placeNumber;
	
	@OneToMany(mappedBy="rentingPlace" , fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<CarStorage> carStorage = new HashSet<CarStorage>();
	
	@OneToMany(mappedBy="rentingPlace",orphanRemoval=true)
	@JsonManagedReference
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPlaceNumber() {
		return placeNumber;
	}

	public void setPlaceNumber(String placeNumber) {
		this.placeNumber = placeNumber;
	}

	@Override
	public String toString() {
		return "RentingPlace{" +
				"id=" + id +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				", placeNumber='" + placeNumber + '\'' +
				", carStorage=" + carStorage +
				", order=" + order +
				'}';
	}
}
