package com.taurenk.pinpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tauren on 3/25/15.
 */
@Entity
@Table(name="place")
public class Place {

    @Id
    private Integer Id;

    @Column(name="zip")
    private String zip;

    @Column(name="place")
    private String city;

    @Column(name="name1")
    private String state;

    private int stringDistance;

    public Integer getId() {
        return Id;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }


    public int getStringDistance() {
        return stringDistance;
    }

    public void setStringDistance(String distance) {
        this.stringDistance = Integer.parseInt(distance);
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStringDistance(int stringDistance) {
        this.stringDistance = stringDistance;
    }
}
