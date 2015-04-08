package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.Place;
import org.apache.commons.codec.language.DoubleMetaphone;

import java.util.List;

/**
 * Created by tauren on 4/2/15.
 */

public class GeocodeCity {


    public AddressResult geocodeByZip(Place place, AddressResult addressResult) {
        addressResult.addPotentialPlace(place);
        for (String potentialCity: addressResult.getCityTokens() ){
            String stringToReplace = this.findCity(potentialCity, place.getCity());
            if (stringToReplace != null) {
                addressResult = this.setCityData(addressResult, stringToReplace, potentialCity);
            }
        }
        return addressResult;
    }


    public AddressResult geocodeByCity(List<Place> placeList, AddressResult addressResult) {
        outerloop:
        for (Place potentialPlace : placeList) {
            for (String s : addressResult.getCityTokens()){
                String stringToReplace = this.findCity(s, potentialPlace.getCity());
                if (stringToReplace != null) {
                    addressResult = this.setCityData(addressResult, stringToReplace, potentialPlace.getCity());
                    addressResult.addPotentialPlace(potentialPlace);
                    break outerloop;
                }
            }
        }

        return addressResult;
    }


    /**
     * Use found city data to setup AddressResult/Address data
     * @param addressResult
     * @param stringToReplace
     * @param city
     * @return addressResult
     */
    public AddressResult setCityData(AddressResult addressResult, String stringToReplace, String city ) {
        Address address = addressResult.getAddress();
        address.setCity(city);
        String newStreet = address.getStreet().replaceAll("\\s(" + stringToReplace + ")$","");
        address.setStreet(newStreet);
        addressResult.setAddress(address);
        return addressResult;
    }

    /**
     * return string to replace in street string
     * @param cityToken
     * @param candidateCity
     * @return
     */
    public String findCity(String cityToken, String candidateCity) {
        if (cityToken.equals(candidateCity) ) {
            return cityToken;
        }

        DoubleMetaphone dm = new DoubleMetaphone();
        String tokenMetaphone = dm.doubleMetaphone(cityToken);
        String candidateMetaphone = dm.doubleMetaphone(candidateCity);
        if (tokenMetaphone.equals(candidateMetaphone)) {
            return cityToken;
        }

        int stringDistance = org.apache.commons.lang.StringUtils.getLevenshteinDistance(cityToken, candidateCity);
        if (stringDistance==1) {
            return cityToken;
        }
        return null;
    }


}
