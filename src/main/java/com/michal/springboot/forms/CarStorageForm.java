package com.michal.springboot.forms;

import com.michal.springboot.domain.CarStorage;

import java.util.ArrayList;
import java.util.List;

public class CarStorageForm
{
	private List<CarStorage> storages = new ArrayList<CarStorage>();

	public List<CarStorage> getStorages() {
		return storages;
	}

	public void setStorages(List<CarStorage> storages) {
		this.storages = storages;
	}

	@Override
	public String toString() {
		return "CarStorageForm [storages=" + storages + "]";
	}
	

}
