package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/31/15.
 *
 */
public class AddressResult {

    private Address address;

    private List<Place> potentialPlaces;
    private List<AddrFeat> potentialAddresses;

    private List<String> cityTokens;
    private List<String> streetTokens;


    public AddressResult(Address address) {
        this.address = address;
        this.cityTokens = this.extractCityTokens(address.getStreet());
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

    public List<String> getCityTokens() {
        return cityTokens;
    }

    public void setCityTokens(List<String> cityTokens) {
        this.cityTokens = cityTokens;
    }

    /**
     * Attempt to find potential city strings by parsing out remaining strings in address.
     * Average US cities are of length 1,2, 3 words.
     * @param addressString
     */
    private List<String> extractCityTokens(String addressString) {
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
