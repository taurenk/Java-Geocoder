package com.taurenk.pinpoint.geocoder;

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

    public Address(String addressString) {
        this.addressString = addressString;
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

    public List<String> getPotential_cities() {
        return potential_cities;
    }
}
