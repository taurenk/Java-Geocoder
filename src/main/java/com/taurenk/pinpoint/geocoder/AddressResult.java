package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;

import java.util.List;

/**
 * Created by tauren on 3/31/15.
 */
public class AddressResult {

    private Address address;
    private List<Place> potentialPlaces;
    private List<String> potentialZipcodes;

    public AddressResult(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Place> getPotentialPlaces() {
        return potentialPlaces;
    }

    public void setPotentialPlaces(List<Place> potentialPlaces) {
        this.potentialPlaces = potentialPlaces;
    }

}
