package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.exception.PlaceNotFoundException;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @RequestMapping(value = "/placeByZip",  method = RequestMethod.GET)
    public Place getPlaceByZipcode(@RequestParam(value="zipcode") String zipcode) {
        Place place = placeService.placeByZip(zipcode);
        if (place == null) throw new PlaceNotFoundException();
        return  place;
    }


    @RequestMapping(value = "/placesByCity",  method = RequestMethod.GET)
    public List<Place> getPlaceByCity(@RequestParam(value="city") String city) {
        List<Place> places = placeService.placesByCity(city);
        if (places == null) throw new PlaceNotFoundException();
        return  places;
    }


    @RequestMapping(value = "/test",  method = RequestMethod.GET)
    public List<Place> test() {
        List<String> cityList = new ArrayList();
        cityList.add("MANORVILLE");
        cityList.add("DRIVE MANORVILLE");
        return placeService.placesByCityList(cityList, null);
        //return placeService.placesByCityList(cityList);
    }


}
