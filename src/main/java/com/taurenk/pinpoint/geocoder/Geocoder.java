package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import com.taurenk.pinpoint.service.PlaceService;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // Parse address string into Address Object
        Address address = parser.address;
        // Build a new address results object
        AddressResult addressResult = new AddressResult(address);
        // Try and remove City From Address - while finding place candidates
        addressResult = this.geocodeCity(addressResult);


        if (addressResult.getAddress().getCity() == null) {
            // return City Data
        }
        if (address.getIntersectionFlag() == true) {
            System.out.println("Address is an intersection!");
            // Return something
        }
        if (address.getPoBox() != null) {
            System.out.println("Address is a PO Box!");
            // return City Data
        }


        return addressResult.getAddress();
    }


    private AddressResult geocodeStreet(AddressResult addressResult) {

        return null;
    }

    /**
     * The idea here is to remove the city from the street string.
     * @param addressResult
     * @return
     */
    private AddressResult geocodeCity(AddressResult addressResult) {
        Address address = addressResult.getAddress();
        GeocodeCity cityGeocoder = new GeocodeCity();

        if (address.getZip() != null) {
            Place place = placeService.placeByZip(address.getZip() );
            if (place != null) {
               addressResult = cityGeocoder.geocodeByZip(place, addressResult);
            }

            if (addressResult.getAddress().getCity() == null) {
                // try a more 'fuzzier' match based on city tokens.
                List<Place> placeList = placeService.placesByCityList(addressResult.getStreetTokens(), address.getState());
                if (placeList != null) {
                    addressResult = cityGeocoder.geocodeByCity(placeList, addressResult);
                }
            }
        }
        return addressResult;
    }


}
