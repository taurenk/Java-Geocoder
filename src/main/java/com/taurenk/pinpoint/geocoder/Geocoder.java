package com.taurenk.pinpoint.geocoder;

/**
 * Created by tauren on 3/25/15.
 */
public class Geocoder {

    private AddressParser parser;

    public Geocoder(String address) {
        parser = new AddressParser();
    }

    /**
     * Pass the geocoder a raw address string to start geocoding process
     * @param addrString
     * @return
     */
    public Address geocodeAddress(String addrString){

        return null;
    }
}
