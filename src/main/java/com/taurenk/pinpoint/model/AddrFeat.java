package com.taurenk.pinpoint.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiLineString;
import org.postgis.Geometry;
//import org.postgis.MultiLineString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by tauren on 4/3/15.
 */
@Entity
@Table(name="addrfeat")
public class AddrFeat {

    @Id
    private int gid;

    private Double tlid;

    private String fullname;
    private String name;

    private String lfromhn;
    private String ltohn;

    private String rfromhn;
    private String rtohn;

    private String zipl;
    private String zipr;

    private String state;

    @Type(type="org.hibernate.spatial.GeometryType")
    private MultiLineString geom;

    @Transient
    private int stringDistance;

    @Transient
    private Double rankScore;


    public Double getRankScore() {
        return rankScore;
    }

    public void setRankScore(Double rankScore) {
        this.rankScore = rankScore;
    }


    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public Double getTlid() {
        return tlid;
    }

    public void setTlid(Double tlid) {
        this.tlid = tlid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLfromhn() {
        return lfromhn;
    }

    public void setLfromhn(String lfromhn) {
        this.lfromhn = lfromhn;
    }

    public String getLtohn() {
        return ltohn;
    }

    public void setLtohn(String ltohn) {
        this.ltohn = ltohn;
    }

    public String getRfromhn() {
        return rfromhn;
    }

    public void setRfromhn(String rfromhn) {
        this.rfromhn = rfromhn;
    }

    public String getRtohn() {
        return rtohn;
    }

    public void setRtohn(String rtohn) {
        this.rtohn = rtohn;
    }

    public String getZipl() {
        return zipl;
    }

    public void setZipl(String zipl) {
        this.zipl = zipl;
    }

    public String getZipr() {
        return zipr;
    }

    public void setZipr(String zipr) {
        this.zipr = zipr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGeom() {
        return this.geom.toString();
    }
    public void setGeom(MultiLineString geom) {
        this.geom = geom;
    }

    public int setStringDistance(int stringDistance) {  return this.stringDistance = stringDistance; }

    public int getStringDistance() {
        return stringDistance;
    }



}
