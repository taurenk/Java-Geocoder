package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.GeomMath;
import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Test;
import static org.junit.Assert.* ;

/**
 * Created by taurenk on 5/24/15.
 */
public class GeomMathTest {

    @Test
    public void test_Distance() {
        System.out.println("Test Haversine Distance Formula....") ;
        // Coords taken from Caputo Drive Manorville NY 11949
        Coordinate coord1 = new Coordinate(40.859169, -72.793879);
        Coordinate coord2 = new Coordinate(40.858932, -72.79388);
        Coordinate coord3 = new Coordinate(40.856646, -72.792574);

        assertTrue( new GeomMath().haversine(coord1, coord2) == 0.67) ;
    }

}
