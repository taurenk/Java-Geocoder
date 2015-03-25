package com.taurenk.pinpoint.geocoder.library;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tauren on 3/25/15.
 * LibraryLoader takes care of all functions related to
 * Loading data from file and building out regex/maps from
 * it.
 */
public class LibraryLoader {

    private HashMap STATE_MAP;
    private HashMap DIRECTIONAL_MAP;
    private HashMap UNIT_MAP;

    private String STATE_REGEX;

    public LibraryLoader() {
        STATE_MAP = new HashMap();
        DIRECTIONAL_MAP = new HashMap();
        UNIT_MAP = new HashMap();

        this.import_states_data();
        this.loadDirectionalMap();
        this.loadUnitMap();
    }

    /**
     * Load Standard unit Mapping
     * TODO: create a more comprehensive map
     * APARTMENT:APT | APT:APT?
     */
    private void loadUnitMap() {
        UNIT_MAP.put("APARTMENT", "APT");
        UNIT_MAP.put("APMT", "APT");
        UNIT_MAP.put("BASEMENT", "BSMT");
        UNIT_MAP.put("BUILDING", "BLDG");
        UNIT_MAP.put("DEPARTMENT", "DEPT");
        UNIT_MAP.put("FLOOR", "FL");
        UNIT_MAP.put("F", "FL");
        UNIT_MAP.put("FRONG", "FRNT");
        UNIT_MAP.put("HANGAR", "HNGR");
        UNIT_MAP.put("LOBBY", "LBBY");
        UNIT_MAP.put("LBY", "LBBY");
        UNIT_MAP.put("LOT", "LOT");
        UNIT_MAP.put("LT", "LOT");
        UNIT_MAP.put("LOWER", "LOWR");
        UNIT_MAP.put("NUMBER", "#");
        UNIT_MAP.put("#", "#");
        UNIT_MAP.put("NO", "#");
        UNIT_MAP.put("OFFICE", "OFC");
        UNIT_MAP.put("OFIC", "OFC");
        UNIT_MAP.put("OFFC", "OFC");
        UNIT_MAP.put("PIER", "PIER");
        UNIT_MAP.put("PENTHOUSE", "PH");
        UNIT_MAP.put("PBOX", "PO BOX");
        UNIT_MAP.put("PB", "PO BOX");
        UNIT_MAP.put("PBX", "PO BOX");
        UNIT_MAP.put("P O BOX", "PO BOX");
        UNIT_MAP.put("POBX", "PO BOX");
        UNIT_MAP.put("POBOX", "PO BOX");
        UNIT_MAP.put("BOX", "BX");
        UNIT_MAP.put("REAR", "REAR");
        UNIT_MAP.put("ROOM", "RM");
        UNIT_MAP.put("SIDE", "SIDE");
        UNIT_MAP.put("SLIP", "SLIP");
        UNIT_MAP.put("SPACE", "SPC");
        UNIT_MAP.put("STOP", "STOP");
        UNIT_MAP.put("SUITE", "STE");
        UNIT_MAP.put("SUIT", "STE");
        UNIT_MAP.put("TRAILER", "TRLR");
        UNIT_MAP.put("UNIT", "UNT");
        UNIT_MAP.put("UPPER", "UPPR");
    }

    /**
     * Load Standard Directions into HashMap
     */
    private void loadDirectionalMap() {
        DIRECTIONAL_MAP.put("NORTH", "N");
        DIRECTIONAL_MAP.put("NORTHEAST", "NE");
        DIRECTIONAL_MAP.put("EAST", "E");
        DIRECTIONAL_MAP.put("SOUTHEAST", "SE");
        DIRECTIONAL_MAP.put("SOUTH", "S");
        DIRECTIONAL_MAP.put("SOUTHWEST", "SW");
        DIRECTIONAL_MAP.put("WEST", "W");
        DIRECTIONAL_MAP.put("NORTHWEST", "NW");
    }


    /**
     * Read in US state file and create:
     * 1. State Abrv Hashmap for quick state lookup
     * 2. State Regex for searching for states.
     */
    private void import_states_data() {
        System.out.println(System.getProperty("java.class.path"));
        try {
            // This was a PAIN-make sure the target file IS IN THE TARGET CLASSES!
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            LibraryLoader.class.getResourceAsStream("US_States.txt")
                    ));

            String line;
            String[] lineItems;
            while((line = reader.readLine()) != null) {
                lineItems = line.split(":");
                if (lineItems[0].length() == 2) {
                    this.STATE_MAP.put(lineItems[0], lineItems[0]);
                    STATE_MAP.put(lineItems[1], lineItems[0]);
                }
            }

            StringBuilder sb = new StringBuilder();
            Iterator it = STATE_MAP.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                sb.append(pair.getKey());
                if (it.hasNext()) {sb.append("|");}
                it.remove();
            }

            this.STATE_REGEX = "\\s(" + sb.toString() + ")$";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap getSTATE_MAP() {
        return STATE_MAP;
    }

    public HashMap getDIRECTIONAL_MAP() {
        return DIRECTIONAL_MAP;
    }

    public HashMap getUNIT_MAP() {
        return UNIT_MAP;
    }

    public String getSTATE_REGEX() {
        return STATE_REGEX;
    }
}
