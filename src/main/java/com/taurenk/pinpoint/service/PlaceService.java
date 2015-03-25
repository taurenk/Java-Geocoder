package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tauren on 3/25/15.
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Return Place by Given Id
     * @param id
     * @return Place
     */
    public Place getPlaceById(Integer id){
        return placeRepository.findOne(id);
    }

    /**
     * Get Place by given zipcode String
     * @param zip
     * @return Place
     */
    public Place getPlaceByZip(String zip) { return placeRepository.findByZip(zip); }
}
