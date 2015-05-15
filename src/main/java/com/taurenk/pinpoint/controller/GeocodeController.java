package com.taurenk.pinpoint.controller;


import com.taurenk.pinpoint.geocoder.Address;
import com.taurenk.pinpoint.geocoder.Geocoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/api/geocoder")
public class GeocodeController {

    @Autowired
    private Geocoder geocoder = new Geocoder();

    @RequestMapping("/geocode")
    public Address getTest(@RequestParam(value="address", defaultValue="6 caputo drive manorville ny 11949") String address) {
        Address addr = geocoder.geocode(address);
        return  addr;
    }




}
