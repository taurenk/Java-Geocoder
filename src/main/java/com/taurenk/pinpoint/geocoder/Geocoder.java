package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
@Component
public class Geocoder {

    private AddressParser parser;

    @Autowired
    private PlaceService placeService;

    public Geocoder() {
        parser = new AddressParser();
    }


    /**
     * Determine if an address is geocode[able?] and at what level:
     * Address, City, None
     * @param addrString
     * @return
     */
    public Address geocode(String addrString){
        parser.preParse(addrString);
        Address address = parser.address;
        address = this.geocodeCity(address);
        // Maybe: Geocode city FIRST. Then we know what we are dealing with...?
        // If intersection
        // If PO Box
        // GEOCODE ADDRESS
        // if !results: PLACE
        // Else: no results :(
        return address;
    }

    /**
     * Todo: Geocode an address
     * @param address
     */
    public void geocodeAddress(Address address){
       // verify zip/city combo
    }

    /**
     * Re-usable class for geocoding city/zip based queries
     * TODO: High Level Doc
     * @param address
     * @return
     */
    public Address geocodeCity(Address address){

        /*
        Goal: Identify city or potential cities. Want to return a list of zips/city combos
        so that WHEN we get to geocoding the address, we will have a filtered target of addrfeats to search for.

        IF zip: THEN find place by zips
            If place.city in address.street: set citySearch
            If place.city fuzzy in address.street: set citySearch
          IF !citySearch: THEN find place by potential cities
            - Rank Candidate(s) according to string distance.


        */

        String citySearch = null;

        // If zip find it
        if (address.getZip() != null) {
            Place place = placeService.placeByZip(address.getZip());

            if ( address.getStreet().contains(place.getCity()) ) {
                citySearch = place.getCity();
            }
            // If a fuzzy search is needed, hold off.
        }

        // Find potential cities and "fuzzy match them".
        // Using potential city list extracted from address string.
        // This would do the ranking and return something to set up/extract the city..
        if (citySearch == null) {
            //use potential city list
            //List<Place> places = placeService.getPlaceByCity()
            //

        }

        if (citySearch == null) {
            return address;
        }

        return address;
    }



}
