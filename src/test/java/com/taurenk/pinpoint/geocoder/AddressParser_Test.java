package com.taurenk.pinpoint.geocoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
public class AddressParser_Test {

    public static void main(String[] args) {


        List<String> test_address = new ArrayList<String>();
        test_address.add("6 Caputo Dr., Manorville NY  11949");
        test_address.add("100 White Plains Road, White Plains NY 10604");

        Geocoder geocoder = new Geocoder();

        for (String addr : test_address) {
            System.out.println("Geocoding Address: " + addr.toString());
            geocoder.geocode(addr);

        }
    }
}
