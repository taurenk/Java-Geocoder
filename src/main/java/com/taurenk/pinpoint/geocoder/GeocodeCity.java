package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.exception.PlaceNotFoundException;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by taurenk on 5/15/15.
 */
@Component
public class GeocodeCity {

    @Autowired
    PlaceService placeService;
    
    /**
     * We pass in the AddressResult object, which *should* contain
     * List<String>StreetTokens and potential zips.
     *
     * We want to find out a few things;
     * @param addressResult
     * @return
     */
    public AddressResult GeocodeCity(AddressResult addressResult) {
        String extractedCity = null;

        // Retrieve cities by zip
        if (addressResult.getAddress().getZip() != null) {
            Place placeCandiate = this.getCityByZip(addressResult.getAddress().getZip());
            if (placeCandiate != null) {
                //extractedCity = this.extractCity(addressResult.getCityTokens(), placeCandiate.getCity());
                addressResult.addPotentialPlace(placeCandiate);
                addressResult.addPotentialZips(placeCandiate.getZip());
            }
        }

        // Retrieve cities by tokenized street list
        if ( (extractedCity==null) && (addressResult.getPotentialPlaces() != null)) {
            List<Place> placeCandidates = this.getCitybyTokens(addressResult.getCityTokens(), "NY");
            for (Place place : placeCandidates) {
                    addressResult.addPotentialPlace(place);
                    addressResult.addPotentialZips(place.getZip());
            }
        }

        for (Place place : addressResult.getPotentialPlaces()) {
            extractedCity = this.extractCity(addressResult.getCityTokens(), place.getCity());
            if (extractedCity != null) {
                String oldStreet  =  addressResult.getAddress().getStreet();
                addressResult.getAddress().setStreet(oldStreet.replace(extractedCity, ""));

                if( addressResult.getAddress().getStreet().trim().equals("")) {
                    addressResult.getAddress().setStreet(null);
                }


            }
            if (extractedCity != null) { break; }
        } // end extract places loop

        return addressResult;
    }

    /**
     * Retrieve city by given zipcode
     * @return Place
     */
    private Place getCityByZip(String zipcode) {
        try {
            Place placeCandidate = placeService.placeByZip("11949");
            return placeCandidate;
        } catch (PlaceNotFoundException placeNotFound) {
            return null;
        } catch (Exception ex) {
            System.out.println("\nERROR IN CITY BY ZIP + <" + zipcode + ">");
            System.out.println(ex.toString());
            return null;
        }

    }

    /**
     * Retrieve places by token list.
     * @param streetTokens
     * @return
     */
    private List<Place> getCitybyTokens(List<String> streetTokens, String state) {
        try {
            List<Place> placeCandidates = placeService.placesByCityList(streetTokens, "NY");
            return placeCandidates;
        } catch (PlaceNotFoundException placeNotFound) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * take tokens and compare against potential city.
     * @param streetTokens
     * @param potentialCity
     * @return
     */
    private String extractCity(List<String> streetTokens, String potentialCity) {
        //DoubleMetaphone dm = new DoubleMetaphone();

        // Starting with largest token in street string, Compare to the found city.
        // If a city matches a street token, we can extract it out!
        for (String streetToken : streetTokens) {
           if (org.apache.commons.lang.StringUtils.getLevenshteinDistance(streetToken, potentialCity) <= 2) {
                return potentialCity;
           }
        }
        return  null;
    }


}
