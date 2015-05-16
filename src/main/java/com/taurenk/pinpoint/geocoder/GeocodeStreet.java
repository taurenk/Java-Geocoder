package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.service.AddrFeatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by taurenk on 4/4/15.
 * Tying a new approach, let's seperate out this whole thing into a class!
 */
public class GeocodeStreet {

    @Autowired
    AddrFeatService addrFeatService;


    private List<AddrFeat> geocodeByNameZip(String streetName, String zipString) {
        try {

        } catch (Exception ex) {

        }
        return null;
    }


}
