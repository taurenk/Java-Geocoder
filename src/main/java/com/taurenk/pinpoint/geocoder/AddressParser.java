package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.AddressUtilities;

import java.util.regex.Matcher;

/**
 * Created by tauren on 3/25/15.
 * AddressParser takes in a string and converts it into
 * an Address object via pre-parsing algorithm:finding Number, State, Zip, potential City.
 *
 */
public class AddressParser {

    Address address;
    AddressUtilities addressUtilities;

    public AddressParser() {
        this.addressUtilities = new AddressUtilities();
    }


    public void preParse(String addrString){
        this.address = new Address(addrString);
        this.cleanseString(address.getAddressString());
        System.out.println("\tCleansed: " + address.getAddressString());

        String addr = this.address.getAddressString();
        addr = extractZip(addr);
        System.out.println("\tAfter ZIP: <" + addr + ">");

        addr = extractNumber(addr);
        System.out.println("\tAfter Number: <" + addr + ">");

        addr = extractState(addr);
        System.out.println("\tAfter State: <" + addr + ">");

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
            System.out.println("\t\tZip Match: <" + m.group(0) +">" );
            this.address.setZip(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        } else {
            System.out.println("\t\tip Match: NO MATCH");
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
            System.out.println("\t\tNumber Match: <" + m.group(0) + ">");
            this.address.setNumber(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        } else {
            System.out.println("\t\tNumber Match: NO MATCH");
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
            System.out.println("\t\tStates Match: <" + m.group(0) + ">");
            this.address.setState(m.group(0).trim());
            addressString = addressString.replace(m.group(0), "");
        } else {
            System.out.println("\t\tStates Match: NO MATCH");
        }
        return addressString.trim();
    }
}
