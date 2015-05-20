package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.AddrFeatService;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private GeocodeCity geocodeCity_;

    @Autowired
    private GeocodeStreet geocodeStreet;

    private final String[] LEVELS = {"0 - No Match Found", "1 - City/Zip Match",
                                        "2 - Street Level Match", "3 - Street Level Match: Interpolated"};

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

        preParser.preParse(addrString); // PO BOX REGEX HERE

        // Parse address string into Address Object
        Address address = preParser.address;

        // Build a new address results object
        AddressResult addressResult = new AddressResult(address);

        // City will be embeded in street string, this will assist in parsing them out
        //addressResult = this.geocodeCity(addressResult);
        addressResult = this.geocodeCity_beta(addressResult);



        // Is Address PO Box? TODO
        if (addressResult.getAddress().getPoBox() != null) {
            System.out.println("Address is a PO Box!");
           return null;
        }
        else if (address.getIntersectionFlag() == true) {
            System.out.println("Address is an intersection!");
            return null;
        }
        else if  (addressResult.getAddress().getStreet() == null && addressResult.getPotentialPlaces() != null ) {
            System.out.println("Entered Adress, FOUND CITY!");
            address = setAddressWithPlace(addressResult.getAddress(), addressResult.getPotentialPlaces().get(0));
            return address;
        }
        else {
            addressResult = postParser.postParse(addressResult);
            address = this.geocodeStreet.geocodeStreet(addressResult); //
            addressResult.setAddress(address);
            return address;
        }


    }


    /**
     * call out or geocodeCity service to handle this.
     * @param addressResult
     * @return
     */
    private AddressResult geocodeCity_beta(AddressResult addressResult){
        addressResult = geocodeCity_.GeocodeCity(addressResult);
        return addressResult;
    }



    private Address setAddressWithFeat(Address address, AddrFeat feature) {
        // this is bit tricky maybe. we want to get the zip.city from the Place record
        // but the lat/lon from the AddrFeat
        address.setCity(feature.getLcity());            // TODO: figure out cityleft vs cityright
        address.setZip(feature.getZipl());              // TODO: figure out zipl vs zipr
        address.setStreet(feature.getFullname());

        //TODO set long/lat?
        return address;
    }

    private Address setAddressWithPlace(Address address, Place place) {
        // this is bit tricky maybe. we want to get the zip.city from the Place record
        // but the lat/lon from the AddrFeat
        address.setCity(place.getCity());
        address.setZip(place.getZip());
        address.setStreet(null);
        //address.setLat(place.getLatitude());
        //address.setLon(place.getLongitude());
        return address;
    }



}
