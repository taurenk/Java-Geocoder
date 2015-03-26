package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.exception.PlaceNotFound;
import com.taurenk.pinpoint.geocoder.Address;
import com.taurenk.pinpoint.geocoder.Geocoder;
import com.taurenk.pinpoint.model.Place;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/geocoder")
public class GeocodeController {

    private Geocoder geocoder = new Geocoder();

    @RequestMapping("/geocode")
    public Address getTest(@RequestParam(value="address", defaultValue="6 caputo drive manorville ny 11949") String address) {
        Address addr = geocoder.geocode(address);
        return  addr;
    }




}
