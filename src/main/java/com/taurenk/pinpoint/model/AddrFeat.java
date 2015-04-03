package com.taurenk.pinpoint.model;

import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tauren on 4/3/15.
 */
@Entity
@Table(name="addrfeat")
public class AddrFeat {

    @Id
    private int gid;

    private String fullname;

    private String lfromhn;
    private String ltohn;

    private String rfromhn;
    private String rtohn;

    private String zipl;
    private String zipr;

    private String state;

    private String name;

    private String suftypabrv;


    public int getGid() {
        return gid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getLfromhn() {
        return lfromhn;
    }

    public String getLtohn() {
        return ltohn;
    }

    public String getRfromhn() {
        return rfromhn;
    }

    public String getRtohn() {
        return rtohn;
    }

    public String getZipl() {
        return zipl;
    }

    public String getZipr() {
        return zipr;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getSuftypabrv() {
        return suftypabrv;
    }

}
