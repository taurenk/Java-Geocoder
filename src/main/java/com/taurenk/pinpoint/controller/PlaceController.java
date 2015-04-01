package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @RequestMapping("/placeByZip")
    public Place getPlaceByZipcode(@RequestParam(value="zipcode") String zipcode) {
        Place place = placeService.placeByZip(zipcode);
        return  place;
    }


    @RequestMapping("/placesByCity")
    public List<Place> getPlaceByCity(@RequestParam(value="city") String city) {
        List<Place> places = placeService.placesByCity(city);
        return  places;
    }


    @RequestMapping("/test")
    public List<Place> test() {
        List<String> cityList = new ArrayList();
        cityList.add("MANORVILLE");
        cityList.add("DRIVE MANORVILLE");
        return placeService.placesByCityList(cityList);
        //return placeService.placesByCityList(cityList);
    }


}
