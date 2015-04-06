package com.taurenk.pinpoint.geocoder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

/**
 * Created by taurenk on 4/5/15.
 * GeometryHelper
 * Hibernate cannot map GEOMETRY fields in custom JPA SQL code. The work around is
 * reading geom in as text via postgis ST_AsText() function and then converting it to
 * a MultiLineString.
 *
 */
public class GeometryHelper {


    /**
     * Take in the text results of ST_asText(geom) and convert to a MultiLineString
     * @param coords
     * @return MultiLineString
     */
    public MultiLineString stringToMultiLineString(String coords) {
        // Strip out unwanted strings from text
        coords = coords.replaceAll("MULTILINESTRING\\(\\(", "");
        // MULTILINESTRING
        coords = coords.replaceAll("\\)\\)", "");
        // Create a new LineString list (required for MultiLineString conversion)
        LineString[] lines = new LineString[1];
        // The format for our MultiLineString is just 1 running line of points.
        lines[0] = this.createLineString(coords);
        if (lines != null) {
            GeometryFactory gm = new GeometryFactory();
            return gm.createMultiLineString(lines);
        }
        return null;
    }



    /**
     * Take list of String coords and convert to LineString
     * @param coords
     * @return LineString
     */
    private LineString createLineString(String coords) {

        try {
            String[] coordList = coords.split(",");
            Coordinate[] coordinates = new Coordinate[coordList.length];
            int index = 0;
            for (String coord : coordList) {
                // Split (y x) into [x][y]
                String[] splitCoords = coord.trim().split(" ");
                Double x = Double.parseDouble(splitCoords[1]);
                Double y = Double.parseDouble(splitCoords[0]);
                coordinates[index] = new Coordinate(x, y);
                index+=1;
            }
            return new GeometryFactory().createLineString(coordinates);
        } catch (Exception ex) {
            System.out.println("Issue Creating Line String...");
            System.out.println(ex.toString());
            return null;
        }

    }
}
