package com.taurenk.pinpoint.geocoder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
public class Address {

    private String addressString;
    private String number;
    private String street;
    private String city;
    //Potential city list
    private List<String> potential_cities;
    private String state;
    private String zip;

    private double lat;
    private double lon;

    public Address(String addressString) {
        this.addressString = addressString;
    }

    @JsonIgnore
    public List<String> getPotential_cities() {
        return potential_cities;
    }

    public void setPotential_cities(List<String> potential_cities) {
        this.potential_cities = potential_cities;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


}
