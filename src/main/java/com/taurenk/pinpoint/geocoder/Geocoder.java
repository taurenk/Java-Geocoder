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

    private PreParser preParser;
    private PostParser postParser;

    @Autowired
    private PlaceService placeService;
    @Autowired
    private AddrFeatService addrFeatService;

    public Geocoder() {
        preParser = new PreParser();
        postParser = new PostParser();
    }


    /**
     * Determine if an address is geocode[able?] and at what level:
     * Address, City, None
     * @param addrString
     * @return
     */
    public Address geocode(String addrString){
        // TODO: unhook address and preparser...
        preParser.preParse(addrString);

        // Parse address string into Address Object
        Address address = preParser.address;

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

        this.geocodeStreet(addressResult);
        return addressResult.getAddress();
    }


    /**
     * Idea here is to find matches based on street/zipcode combinations
     * and then rank them...
     * @param addressResult
     * @return
     */
    private AddressResult geocodeStreet(AddressResult addressResult) {
        //Geocode street by street + zip
        addressResult = postParser.postParse(addressResult);
        Address address = addressResult.getAddress();

        //List<String> zipList = new ArrayList<String>(); //Todo: find 'potential' zipcodes
        //zipList.add(address.getZip());
        List<AddrFeat> candidates = addrFeatService.findFuzzy_NameZip(address.getStreet(), addressResult.getPotentialZips());

        for (AddrFeat feat : candidates) {
            System.out.println("Candidate:" + feat.getFullname() + "|" + feat.getState()  + "|" + feat.getZipl() + "| Score: " + feat.getStringDistance());
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
