package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.AddressUtilities;

/**
 * Created by taurenk on 4/7/15.
 */
public class PostParser {

    AddressUtilities addressUtilities;

    public PostParser(AddressUtilities addressUtilities) {
        this.addressUtilities = addressUtilities;
    }


    /**
     * Want to standardize the last word of the street string - if able
     * @param addressResult
     * @return
     */
    public AddressResult postParse(AddressResult addressResult) {

        Address address = addressResult.getAddress();
        // Check if last word is in dictionary

        return null;
    }
}
