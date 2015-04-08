package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.AddressUtilities;
import org.apache.commons.lang.StringUtils;

/**
 * Created by taurenk on 4/7/15.
 */
public class PostParser {

    AddressUtilities addressUtilities;

    public PostParser() {
        this.addressUtilities = new AddressUtilities();
    }


    /**
     * Want to standardize the last word of the street string - if able
     * @param addressResult
     * @return
     */
    public AddressResult postParse(AddressResult addressResult) {

        Address address = addressResult.getAddress();
        // Check if last word is in dictionary
        String[] streetTokens = address.getStreet().split(" ");
        String streetType = streetTokens[streetTokens.length-1];

        // if target word is in street type dict, transform it and set it.
        if ( addressUtilities.getSTREET_TYPE_MAP().containsKey(streetType)) {
            streetType = addressUtilities.getSTREET_TYPE_MAP().get(streetType);
            streetTokens[streetTokens.length-1] = streetType;
            StringUtils.join(streetTokens, " ");
            address.setStreet(StringUtils.join(streetTokens, " "));
        }

        addressResult.setAddress(address);
        return addressResult;
    }
}
