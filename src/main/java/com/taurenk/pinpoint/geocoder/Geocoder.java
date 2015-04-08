package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.AddrFeatService;
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

    private PreParser parser;

    @Autowired
    private PlaceService placeService;
    @Autowired
    private AddrFeatService addrFeatService;

    public Geocoder() {
        parser = new PreParser();
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

        System.out.println("Geocoding Street:" + addressResult.getAddress().getStreet());
        this.geocodeStreet(addressResult);
        return addressResult.getAddress();
    }


    private AddressResult geocodeStreet(AddressResult addressResult) {
        //Geocode street by street + zip
        Address address = addressResult.getAddress();
        List<AddrFeat> candidates = new ArrayList<AddrFeat>();
        candidates.addAll(addrFeatService.fuzzySearchByName(address.getStreet(), address.getZip(), address.getZip()));

        for (AddrFeat feat : candidates) {
            System.out.println("Candidate:" + feat.getName() );
        }
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
                List<Place> placeList = placeService.placesByCityList(addressResult.getCityTokens(), address.getState());
                if (placeList != null) {
                    addressResult = cityGeocoder.geocodeByCity(placeList, addressResult);
                }
            }
        }
        return addressResult;
    }


}
