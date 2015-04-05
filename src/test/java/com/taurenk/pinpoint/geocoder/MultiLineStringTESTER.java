package com.taurenk.pinpoint.geocoder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

/**
 * Created by taurenk on 4/5/15.
 */
public class MultiLineStringTESTER {

    public MultiLineString multiLineString;

    public MultiLineStringTESTER() {
        this.multiLineString = this.convertGeomToString("dmeo");
    }


    private MultiLineString convertGeomToString(String geometryAsText) {
        GeometryFactory gm = new GeometryFactory();
        LineString[] lines = new LineString[1];
        lines[0] = this.lineStringCreator();
        //lines[1] = this.lineStringCreator();
        return gm.createMultiLineString(lines);
    }

    // Create LineString
    private LineString lineStringCreator() {
        Double x = 40.859169;
        Double y = -72.793879;
        Double x1 = 345.123;
        Double y1 = 543.321;
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(x,y);
        coordinates[1] = new Coordinate(x1,y1);
        return new GeometryFactory().createLineString(coordinates);
    }

    public static void main(String[] args) {
        MultiLineStringTESTER mst = new MultiLineStringTESTER();
        System.out.println("New MLS: " + mst.multiLineString.toString());

    }

}
