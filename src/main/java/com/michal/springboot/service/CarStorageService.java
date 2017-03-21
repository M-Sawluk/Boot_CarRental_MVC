package com.michal.springboot.service;

import com.michal.springboot.domain.CarStorage;
import com.michal.springboot.forms.CarStorageForm;
import com.michal.springboot.repository.CarStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mike on 2017-03-17.
 */
@Service
public class CarStorageService {

    private CarStorageRepository carStorageRepository;

    public CarStorageService(CarStorageRepository carStorageRepository) {
        this.carStorageRepository = carStorageRepository;
    }

    public void setUnits(CarStorageForm cSF) {

        List<CarStorage> carStoragesToUpdate = cSF.getStorages();

        for (int i = 0; i < carStoragesToUpdate.size(); i++) {

            CarStorage cs = carStorageRepository.findByCarIdAndRentingPlaceId(
                    carStoragesToUpdate.get(i).getCar().getId(),
                    carStoragesToUpdate.get(i).getRentingPlace().getId());

            if (cs != null) {
                cs.setUnits(cs.getUnits() + carStoragesToUpdate.get(i).getUnits());
                carStorageRepository.save(cs);

            } else {
                carStorageRepository.save(carStoragesToUpdate.get(i));
            }

        }



    }
}
