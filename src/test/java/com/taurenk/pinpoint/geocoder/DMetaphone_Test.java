package com.taurenk.pinpoint.geocoder;

import org.apache.commons.codec.language.DoubleMetaphone;

/**
 * Created by tauren on 3/27/15.
 */
public class DMetaphone_Test {

    public static void main(String[] args) {
        String[] anArray = {"Manorville", "East Hampton", "Whatever"};

        for (String testString : anArray) {
            String dm = new DoubleMetaphone().doubleMetaphone(testString);
            System.out.println(testString + " : " + dm);
        }

    }


}
