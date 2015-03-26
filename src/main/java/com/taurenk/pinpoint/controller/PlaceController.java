package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @RequestMapping("/getPlaceByZip")
    public Place getPlaceByZipcode(@RequestParam(value="zipcode", defaultValue="11949") String zipcode) {
        Place place = placeService.getPlaceByZip(zipcode);
        return  place;
    }

    @RequestMapping("/getPlaceByCity")
    public List<Place> getPlaceByCity(@RequestParam(value="city", defaultValue="Manorville") String city) {
        List<Place> places = placeService.getPlaceByCity(city);
        return  places;
    }

}
