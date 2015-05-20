package com.taurenk.pinpoint.geocoder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.linearref.LinearIterator;

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
        String testString = "MULTILINESTRING((-72.793879 40.859169,-72.79388 40.858932,-72.793827 40.858744,-72.793757 40.858599," +
                "-72.793414 40.858028,-72.793338 40.857868,-72.793293 40.857733,-72.793284 40.85759,-72.793383 40.85699,-72.793369 40.856883," +
                "-72.793323 40.856804,-72.793247 40.856739,-72.793125 40.856705,-72.792574 40.856646))";

        MultiLineString mls = new GeometryHelper().stringToMultiLineString(testString);

        System.out.println("Converted:" + mls.toText()); // NULL POINTER EXCEPTION
        System.out.println("Line Iterator:");
        for (LinearIterator it = new LinearIterator(mls); it.hasNext(); it.next()) {
            System.out.println(it.getLine().toString());
            Coordinate[] ls = it.getLine().getCoordinates();
            System.out.println(ls[0]);

        }

    }

}
