package com.michal.springboot.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class FormUser {

	@Pattern(regexp = "[a-zA-Z ]{3,25}", message = "{pattern.user.name.validation}")
	private String firstName;

	@Pattern(regexp = "[a-zA-Z ]{3,25}", message = "{pattern.user.name.validation}")
	private String lastName;

	@Size(min = 5,message = "{pattern.user.password.validation}")
	private String password;
	
	@Pattern(regexp = "[a-zA-Z0-9./ ]{4,60}", message = "{pattern.user.address.validation}")
	private String address;

	@Pattern(regexp = "[0-9-]{7,14}", message = "{pattern.user.tele.validation}")
	private String telephone;

	@Pattern(regexp = "[0-9-/]{8,10}", message = "{pattern.user.birth.validation}")
	private String birth;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBirth() {
		return birth;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
}