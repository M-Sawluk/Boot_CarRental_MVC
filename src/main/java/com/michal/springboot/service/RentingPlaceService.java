package com.michal.springboot.service;

import com.michal.springboot.domain.RentingPlace;
import com.michal.springboot.repository.RentingPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mike on 2017-03-17.
 */

@Service
public class RentingPlaceService {

    private RentingPlaceRepository rentingPlaceRepository;

    public RentingPlaceService(RentingPlaceRepository rentingPlaceRepository) {
        this.rentingPlaceRepository = rentingPlaceRepository;
    }

    public void save(RentingPlace place) {
        rentingPlaceRepository.save(place);
    }

    public List<RentingPlace> findAll(){
        return (List<RentingPlace>) rentingPlaceRepository.findAll();
    }
}
