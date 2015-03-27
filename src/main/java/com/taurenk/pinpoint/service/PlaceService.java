package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.exception.PlaceNotFound;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 * Remember the basic Repo syntax: find…By, read…By, and get…By
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;


    public Place placeByZip(String zip){return placeRepository.findPlaceByZip(zip); }


    public List<Place> placesByCity(String city) { return placeRepository.findPlaceByCity(city); }


    public List<Place> placesByCityFuzzy(String city) { return placeRepository.findPlaceByCityFuzzy(city); }


    public List<Place> placesByCity_withScore(String city) {
        List<Object[]> results = placeRepository.findPlaceByCity_score(city);
        return this.objectToPlace(results);
    }


    /**
     * Map an object to Place
     * @param objectList
     * @return
     */
    private List<Place> objectToPlace(List<Object[]> objectList) {
        List<Place> places = new ArrayList();
        for (Object[] result : objectList) {
            Place place = new Place();
            place.setId((Integer)result[0]);
            place.setZip((String)result[1]);
            place.setCity((String)result[2]);
            place.setState((String)result[3]);
            place.setStringDistance((Integer)result[4]);
            places.add(place);
        }
        return places;
    }

}
