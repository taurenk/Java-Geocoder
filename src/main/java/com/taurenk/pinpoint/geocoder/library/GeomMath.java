package com.taurenk.pinpoint.geocoder.library;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by taurenk on 5/19/15.
 * Functions to handle math dealing with Geometric inputs and variables.
 *
 */
public class GeomMath {


    /**
     * Generate new point based on a give coordinate, distance from that point, and a bearing.
     * @param x
     * @param y
     * @param distance
     * @param bearing
     */
    public Coordinate createNewCoordinate(Double x, Double y, Double distance, Double bearing) {
        distance = distance/6371.0;
        bearing = Math.toRadians(bearing);
        x = Math.toRadians(x);
        y = Math.toRadians(y);

        double lat2 = Math.asin(
                Math.sin(x) * Math.cos(distance) + Math.cos(x) * Math.sin(distance) * Math.cos(bearing)
        );
        double a = Math.atan2(
                Math.sin(bearing)*Math.sin(distance)*Math.cos(x), Math.cos(distance)-Math.sin(x)*Math.sin(lat2)
        );
        double lon2 = y + a;

        lon2 = (lon2+ 3*Math.PI) % (2*Math.PI) - Math.PI;

        return new Coordinate(Math.toDegrees(lat2), Math.toDegrees(lon2));

    }


    /**
     * Calculate bearing based on 2 coordinates.
     * @param coord1
     * @param coord2
     * @return angle of bearing
     */
    public Double getBearing(Coordinate coord1, Coordinate coord2) {

        Double lat1 = Math.toRadians(coord1.x);
        Double lat2 = Math.toRadians(coord2.x);
        Double delta = Math.toRadians( (coord2.y-coord1.y) );

        Double y = Math.sin(delta) * Math.cos(lat2);
        Double x = Math.cos(lat1)*Math.sin(lat2) -
                Math.sin(lat1)*Math.cos(lat2)*Math.cos(delta);
        Double angle = Math.atan2(y, x);
        return ((Math.toDegrees(angle))+360) % 360;
    }



    /**
     * Use Haversine formula to calculate distance between 2 points.
     * @param coord1
     * @param coord2
     * @return
     */
    public double haversine(Coordinate coord1, Coordinate coord2) {

        double x1 = Math.toRadians(coord1.x);
        double y1 = Math.toRadians(coord1.y);
        double x2 = Math.toRadians(coord2.x);
        double y2 = Math.toRadians(coord2.y);
        double delta_lat = (x2-x1);
        double delta_lon = (y2-y1);

        double a = Math.sin(delta_lat/2) * Math.sin(delta_lat/2)
                + Math.cos(x1) * Math.cos(x2)
                * Math.sin(delta_lon/2) * Math.sin(delta_lon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return 6361 * c;
    }
}
