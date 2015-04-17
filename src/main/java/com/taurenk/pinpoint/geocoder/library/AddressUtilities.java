package com.taurenk.pinpoint.geocoder.library;

import com.taurenk.pinpoint.geocoder.Address;

import java.util.HashMap;
import java.util.Iterator;
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

    private Pattern cityRegex = Pattern.compile("\\s(\\w+)$");
    private Pattern stateRegex;

    private Pattern intersectionTest = Pattern.compile("\\s(AT|@|AND|&)\\s");
    private Pattern poBoxRegex = Pattern.compile("(\\s)?(PO BOX)\\s(\\w+[-]?\\w+)\\s");

    // US Address Standards Map

    private Map<String, String> STREET_TYPE_MAP;
    private Map<String, String> STATE_MAP;
    private Map<String, String> DIRECTIONAL_MAP;
    private Map<String, String> UNIT_MAP;

    public AddressUtilities() {
        System.out.println("Intializing AddressUtilities.");

        this.STATE_MAP = DataSets.getStateMap();
        this.DIRECTIONAL_MAP = DataSets.getDirectionalMap();
        this.STREET_TYPE_MAP = DataSets.getStreetTypeMap();
        this.stateRegex = Pattern.compile("\\s(" + this.build_state_regex() + ")$");
    }

    /**
     * Create the state regex from STATE_MAP
     */
    private String  build_state_regex() {
        StringBuilder sb = new StringBuilder();
        Iterator it = STATE_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            sb.append(pair.getKey());
            if (it.hasNext()) {sb.append("|");}
        }

        return sb.toString();
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

    public Pattern getIntersectionTest() {
        return intersectionTest;
    }

    public Pattern getPoBoxRegex() {
        return poBoxRegex;
    }

    public Map<String, String> getSTREET_TYPE_MAP() {
        return STREET_TYPE_MAP;
    }

    public Map<String, String> getSTATE_MAP() {
        return STATE_MAP;
    }

    public Map<String, String> getDIRECTIONAL_MAP() {
        return DIRECTIONAL_MAP;
    }

    public Map<String, String> getUNIT_MAP() {
        return UNIT_MAP;
    }
}
