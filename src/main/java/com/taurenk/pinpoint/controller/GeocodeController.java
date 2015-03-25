package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.geocoder.Address;
import com.taurenk.pinpoint.geocoder.Geocoder;
import com.taurenk.pinpoint.model.Place;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/geocoder")
public class GeocodeController {

    private Geocoder geocoder = new Geocoder();

    @RequestMapping("/geocode")
    public String getTest(@RequestParam(value="address", defaultValue="6 caputo drive manorville ny 11949") String address) {
        //geocoder.geocodeAddress(address);
        return  address;
    }
}
