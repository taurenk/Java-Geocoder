package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/31/15.
 */
public class AddressResult {

    private Address address;
    private List<Place> potentialPlaces;
    private List<String> streetTokens;

    public AddressResult(Address address) {
        this.address = address;
        this.streetTokens = this.extractPotentialCities(address.getStreet());
        potentialPlaces = new ArrayList<Place>();
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

    public void addPotentialPlace(Place place) { this.potentialPlaces.add(place); }

    public List<String> getStreetTokens() {
        return streetTokens;
    }

    public void setStreetTokens(List<String> streetTokens) {
        this.streetTokens = streetTokens;
    }

    /**
     * Attempt to find potential city strings by parsing out remaining strings in address.
     * Average US cities are of length 1,2, 3 words.
     * @param addressString
     */
    private List<String> extractPotentialCities(String addressString) {
        List<String> potentialCities = new ArrayList();
        String list[] = addressString.split(" ");
        if (list.length >=3) { potentialCities.add(list[list.length-3] + " " +
                list[list.length-2] + " " + list[list.length-1] ); }
        if (list.length >=2) { potentialCities.add(list[list.length-2] + " "
                + list[list.length-1] ); }
        if (list.length >=1) { potentialCities.add( list[(list.length-1)]  ); }
        return potentialCities;
    }
}
