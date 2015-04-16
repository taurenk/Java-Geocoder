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
     *
     * @param addrString
     * @return
     */
    public Address geocode(String addrString) {
        // TODO: REGEX NOT WORKING ON "MANORVILLE NY 11949"....

        preParser.preParse(addrString);

        // Parse address string into Address Object
        Address address = preParser.address;

        // Build a new address results object
        AddressResult addressResult = new AddressResult(address);

        // Try and remove City From Address - while finding place candidates
        addressResult = this.geocodeCity(addressResult);


        if (addressResult.getAddress().getCity() == null) {
            // Try and rank potential cities
            // TODO logic to set place into Address
            System.out.println("HIT no city");
            if (addressResult.getPotentialPlaces().size() < 0) {
                address = addressResult.getAddress();
                return this.setAddressWithPlace(address, addressResult.getPotentialPlaces().get(0));
            }
        }

        if (addressResult.getAddress().getStreet().equals("")) {
            System.out.println("HIT no street");
            if (addressResult.getPotentialPlaces().size() < 0) {
                address = addressResult.getAddress();
                return this.setAddressWithPlace(address, addressResult.getPotentialPlaces().get(0));
            }
        }

        if (address.getIntersectionFlag() == true) {
            System.out.println("Address is an intersection!");
            // Return something
        }
        if (address.getPoBox() != null) {
            System.out.println("Address is a PO Box!");
            // Try and rank potential cities
            // TODO logic to set place into Address
        }

        this.geocodeStreet(addressResult);
        return addressResult.getAddress();
    }


    /**
     * Using an Address and Potential zipcodes, Query DB to try and find street level address candidates.
     * Send candidates to ranking algorithm to figure out top candidate.
     *
     * @param addressResult
     * @return
     */
    private AddressResult geocodeStreet(AddressResult addressResult) {

        // Do some string manipulating
        addressResult = postParser.postParse(addressResult);
        Address address = addressResult.getAddress();

        //
        List<AddrFeat> candidates = addrFeatService.findFuzzy_NameZip(address.getStreet(),
                addressResult.getPotentialZips());
        // Send AddressResult to ranking
        if (candidates.size() > 0) {
            addressResult = new RankAlgo().rankCandidates(addressResult, candidates);
            // TODO: copy new data into Address object.

        } else {
            // Return null because nothing has changed?
            return null;
        }
        return null;
    }

    /**
     * The idea here is to remove the city from the street string.
     *
     * @param addressResult
     * @return
     */
    private AddressResult geocodeCity(AddressResult addressResult) {
        Address address = addressResult.getAddress();
        GeocodeCity cityGeocoder = new GeocodeCity();

        if (address.getZip() != null) {
            Place place = placeService.placeByZip(address.getZip());
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
        // Rank the Potential Places...
        if (addressResult.getPotentialPlaces().size() != 0) {
            addressResult.setPotentialPlaces(
                    new RankCity().rankCity(
                            addressResult.getAddress(), addressResult.getPotentialPlaces()
                    ));
        }
        return addressResult;
    } //End geocodeCity()


    private Address setAddressWithFeat(Address address, AddrFeat feature) {
        // this is bit tricky maybe. we want to get the zip.city from the Place record
        // but the lat/lon from the AddrFeat
        return address;
    }

    private Address setAddressWithPlace(Address address, Place place) {
        // this is bit tricky maybe. we want to get the zip.city from the Place record
        // but the lat/lon from the AddrFeat
        address.setCity(place.getCity());
        address.setZip(place.getZip());
        address.setStreet(null);
        address.setLat(place.getLatitude());
        address.setLon(place.getLongitude());
        return address;
    }



}
