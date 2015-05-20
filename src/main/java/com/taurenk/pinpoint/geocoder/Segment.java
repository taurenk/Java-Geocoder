package com.taurenk.pinpoint.geocoder;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by taurenk on 5/18/15.
 */
public class Segment {
    Coordinate start;
    Coordinate end;
    Double distance;

    public Segment(Coordinate start, Coordinate end, Double distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
