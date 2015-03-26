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

        List<Place> placeCandidates = new ArrayList();

        // If zip find it!
        if (address.getZip() != null) {
            System.out.println("\t\tTEST: " + address.getZip());
            Place place = placeService.getPlaceByZip("11949");
            System.out.println("\t\tPlace:" + place.getCity());
            if (place != null){
                placeCandidates.add(place);
            }
        }


        // city is *probably* in embedded in street string at this point. Find it and Extract.
        if (placeCandidates.get(0) != null){
            // Check if found zip/city is embedded...
            Place p = placeCandidates.get(0);
            String foundCity = placeCandidates.get(0).getCity();
            if (address.getStreet().contains(foundCity)) {
                address.setCity(foundCity);
                String newStreet = address.getStreet();
                // Be careful, Do not want to replace EVERY occurence or the wrong occurence
                newStreet = newStreet.replaceAll("\\s(" +foundCity+ ")$", "");
                address.setStreet(newStreet);
            }
        }

        return address;
    }

}
