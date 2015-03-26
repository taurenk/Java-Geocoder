package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.exception.PlaceNotFound;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Get Place by given zipcode String
     * @param zip
     * @return Place
     */
    public Place getPlaceByZip(String zip){return placeRepository.findByZip(zip); }


    /**
     * Return all places that match city string
     * @param city
     * @return
     */
    public List<Place> getPlaceByCity(String city) {
        return placeRepository.findByCity(city);
    }
}
