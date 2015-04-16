package com.taurenk.pinpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    private Double latitude;

    private Double longitude;

    @Transient
    private int stringDistance;

    @Transient
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getStringDistance() {
        return stringDistance;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

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

    public Double getLongitude() { return longitude; }

    public Double getLatitude() { return latitude; }

    public void setStringDistance(int stringDistance) {
        this.stringDistance = stringDistance;
    }
}
