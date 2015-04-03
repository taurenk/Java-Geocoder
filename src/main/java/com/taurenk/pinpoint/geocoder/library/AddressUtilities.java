package com.taurenk.pinpoint.geocoder.library;

import com.taurenk.pinpoint.geocoder.Address;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by tauren on 3/25/15.
 * Contains Regex Library along with United States Address Standards.
 *
 * Address Standards taken from TIGER/Line files and USPS standard dictionary.
 *
 */
public class AddressUtilities {

    // Regex
    private Pattern zipRegex = Pattern.compile("(\\d{5}(-\\d{4})?)");
    private Pattern numRegex = Pattern.compile("(^\\d+)");
    // Average length of City in US is one word - test this out.
    private Pattern cityRegex = Pattern.compile("\\s(\\w+)$");
    private Pattern stateRegex;

    private Pattern intersectionTest = Pattern.compile("\\s(AT|@|AND|&)\\s");
    private Pattern poBoxRegex = Pattern.compile("\\s(PO BOX|P O BOX|P.O. BOX)(\\s|\\s#|\\s#\\s)(\\d*)");

    // US Address Standards Map
    private HashMap<String, String> state_map;
    private HashMap<String, String> directional_map;

    public AddressUtilities() {
        LibraryLoader data = new LibraryLoader();
        this.stateRegex = Pattern.compile(data.getSTATE_REGEX());
        this.state_map = data.getSTATE_MAP();
        this.directional_map = data.getDIRECTIONAL_MAP();
    }

    public Pattern getZipRegex() {
        return zipRegex;
    }

    public Pattern getIntersectionTest() {
        return intersectionTest;
    }

    public Pattern getPoBoxRegex() {
        return poBoxRegex;
    }

    public Pattern getNumRegex() {
        return numRegex;
    }

    public Pattern getCityRegex() {
        return cityRegex;
    }

    public Pattern getStateRegex() {
        return stateRegex;
    }

    public HashMap<String, String> getState_map() {
        return state_map;
    }

    public HashMap<String, String> getDirectional_map() {
        return directional_map;
    }
}
