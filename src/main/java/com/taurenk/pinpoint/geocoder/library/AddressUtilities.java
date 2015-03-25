package com.taurenk.pinpoint.geocoder.library;

import com.taurenk.pinpoint.geocoder.Address;

import java.util.HashMap;
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

    // US Address Standards Map
    private HashMap state_map;
    private HashMap directional_map;

    public AddressUtilities() {
        LibraryLoader data = new LibraryLoader();
        stateRegex = Pattern.compile(data.getSTATE_REGEX());
        state_map = data.getSTATE_MAP();
        directional_map = data.getDIRECTIONAL_MAP();
    }

    public Pattern getZipRegex() {
        return zipRegex;
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

    public HashMap getState_map() {
        return state_map;
    }

    public HashMap getDirectional_map() {
        return directional_map;
    }
}
