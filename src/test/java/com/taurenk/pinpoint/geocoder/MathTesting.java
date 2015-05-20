package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.GeomMath;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by taurenk on 5/18/15.
 */
public class MathTesting {

    public static void main(String[] args) {

        // -72.793879 40.859169,
        // -72.79388 40.858932
        Coordinate coord1 = new Coordinate();
        coord1.x = 40.859169;
        coord1.y = -72.793879;

        Coordinate coord2= new Coordinate();
        coord2.x = 40.858932;
        coord2.y = -72.79388;

        new GeomMath().haversine(coord1, coord2);
        // Bearing is fucked up...
        Double bearing = new GeomMath().getBearing(coord1, coord2);
        System.out.println("Bearing:" + bearing);

        System.out.println("new Point adding 100 km to bearing2:");
        new GeomMath().createNewCoordinate(coord1.x, coord1.y, 100.00, bearing);

        // Another Coordinate SET:
        // -72.793125 40.856705,-72.792574 40.856646
        /*
        coord1.x =  40.856705;
        coord1.y = -72.793125 ;
        coord2.y = 40.856646;
        coord2.y = -72.792574;
        bearing = new Interpolation().getBearing(coord1, coord2);
        System.out.println("Bearing:" + bearing);
        */
    }
}
