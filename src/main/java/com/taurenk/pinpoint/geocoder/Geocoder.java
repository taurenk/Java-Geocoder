package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import com.taurenk.pinpoint.service.PlaceService;
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


        // If intersection
        if (address.getIntersectionFlag() == true) {
            System.out.println("Address is an intersection!");
            // Return something
        }
        // If PO Box
        if (address.getPoBox() != null) {
            System.out.println("Address is a PO Box!");
            // Geocode City/Place
        }
        // GEOCODE ADDRESS

        return addressResult.getAddress();
    }



    /**
     * Often times City will be lodged into the Street Address string.
     * Try and locate/extract this data.
     * @param addressResult
     * @return
     */
    public AddressResult geocodeCity(AddressResult addressResult){
        Address address = addressResult.getAddress();

        // Check if the Place/Zip is in Street String
        if (address.getZip() != null) {
            Place place = placeService.placeByZip(address.getZip());
            System.out.println("\tPlaces Found by Zip:" + place.getZip() + " : " + place.getCity());
            if (place != null) {
                address = extractCityData(address, place.getCity());
            }
        }

        return addressResult;
    }


    // This only works on STRICT matches. Need to be able to support "fuzzy matching"...
    private Address extractCityData(Address address, String city) {
        String street = address.getStreet();
        System.out.println("\tExtracting city from <" + street + ">");
        Pattern p = Pattern.compile("\\s(" + city + ")$");
        Matcher m = p.matcher(street); // get a matcher object

        if (m.find() ) {
            System.out.println("\tFound City: " + city);
            address.setCity(city);
            address.setStreet(street.replace("\\s("+city+")$", ""));
        }
        return address;
    }



    /**
     * Attempt to find potential city strings by parsing out remaining strings in address.
     * Average US cities are of length 1,2, 3 words.
     * @param addressString
     */
    private List<String> extractPotentialCities(String addressString) {
        List<String> potentialCities = new ArrayList();
        String list[] = addressString.split(" ");
        if (list.length >=1) { potentialCities.add( list[(list.length-1)]  ); }
        if (list.length >=2) { potentialCities.add(list[list.length-2] + " "
                + list[list.length-1] ); }
        if (list.length >=3) { potentialCities.add(list[list.length-3] + " " +
                list[list.length-2] + " " + list[list.length-1] ); }

        return potentialCities;
    }

}
