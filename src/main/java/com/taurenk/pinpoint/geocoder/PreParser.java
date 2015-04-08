package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.AddressUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tauren on 3/25/15.
 * PreParser takes in a string and converts it into
 * an Address object via pre-parsing algorithm:finding Number, State, Zip, potential City.
 *
 */
public class PreParser {

    Address address;
    AddressUtilities addressUtilities;

    public PreParser() {
        this.addressUtilities = new AddressUtilities();
    }


    public void preParse(String addrString){
        this.address = new Address(addrString);
        this.cleanseString(address.getAddressString());
        System.out.println("\tCleansed: " + address.getAddressString());

        String addr = this.address.getAddressString();
        addr = extractZip(addr);
        addr = extractNumber(addr);
        addr = extractState(addr);
        addr = extractPOBox(addr);

        // Test Intersection
        Matcher m = addressUtilities.getIntersectionTest().matcher(addr);
        if (m.find( )) { this.address.setIntersectionFlag(true);}

        // Move remaining string to street
        address.setStreet(addr);
    }

    /**
     * Convert string to uppercase,
     * Strip out commas, periods,
     * hyphens in between words.
     * and remove excess white space
     */
    public void cleanseString(String addressString) {
        String s = addressString.replaceAll("[,.]", "");
        s = s.toUpperCase();
        s = s.replaceAll(" - ", "-");
        s = s.replaceAll("\\s+", " ");
        this.address.setAddressString(s);
    }

    /**
     * Use zipcode regex to find zipcode in address string.
     * if found, remove zip from the string and continue
     * @param addressString
     * @return
     */
    public String extractZip(String addressString) {
        Matcher m = addressUtilities.getZipRegex().matcher(addressString);
        if (m.find( )) {
            //System.out.println("\t\tZip Match: <" + m.group(0) +">" );
            this.address.setZip(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        }
        return addressString.trim();
    }

    /**
     * Use number regex to find number in address string.
     * If found, remove number from string,
     * set number in Address. and return new string without number.
     * @param addressString
     * @return
     */
    public String extractNumber(String addressString) {
        Matcher m = addressUtilities.getNumRegex().matcher(addressString);
        if (m.find( )) {
            //System.out.println("\t\tNumber Match: <" + m.group(0) + ">");
            this.address.setNumber(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        }
        return addressString.trim();
    }

    /**
     *
     * TODO: Make this a bit stronger...perhaps use string distance or metaphones?
     *      New York == Naw York?
     * @param addressString
     * @return
     */
    public String extractState(String addressString) {
        Matcher m = addressUtilities.getStateRegex().matcher(addressString);
        if (m.find( )) {
            // Standardize State to State Code
            String state = m.group(0).trim().toString();

            if ( addressUtilities.getSTATE_MAP().containsKey(state) )  {
                state = addressUtilities.getSTATE_MAP().get(state);
            }
            this.address.setState(state);
            addressString = addressString.replace(m.group(0), "");
        }
        return addressString.trim();
    }


    /**
     * Locate and Extract PO Box if found.
     * @param addressString
     * @return
     */
    public String extractPOBox(String addressString) {
        Matcher m = addressUtilities.getPoBoxRegex().matcher(addressString);
        if (m.find( )) {
            //System.out.println("\t\tPO Box Match: <" + m.group(0) + ">");
            this.address.setPoBox(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        }
        return addressString.trim();
    }
}
