package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.geocoder.library.GeomMath;
import com.taurenk.pinpoint.model.AddrFeat;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.linearref.LinearIterator;
import org.apache.commons.codec.language.DoubleMetaphone;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taurenk on 5/18/15.
 * Class that figures out where in a string of coordinates (AddrFeat) target
 * house/building number resides on.
 */
public class Interpolation {

    public Coordinate interpolate(AddrFeat addrFeat, String targetNumber) {

        Coordinate[] coords = new LinearIterator( addrFeat.getGeom() ).getLine().getCoordinates();

        if (addrFeat.getSideOfStreet() == null) {
            // If no side, just return start of street
            return coords[0];
        }

        // TEST; return most basic interpolation!
        return coords[0];
    }

    /**
     * Given a target number, find potential point based on
     * basic interpolation algorithm.
     * 1. figure out total distance of line sengment
     * 2. find basic calculations; house ratio
     * @param fromhn
     * @param tohn
     * @param target
     * @param coords
     * @return
     */
   private Coordinate findCoord(int fromhn, int tohn, int target, Coordinate[] coords) {

       Map<Integer, Segment> segmentMap = new HashMap();
       double total_distance = 00.00;
       double house_ratio;
       double segmentDistance;

       for (int i=0; i<=coords.length; i++){
           segmentDistance = new GeomMath().haversine(coords[i], coords[i+1]);
           segmentMap.put(i, new Segment(coords[i], coords[i+1], segmentDistance));
           total_distance += segmentDistance;
       }


       System.out.println("Total Distance=" + total_distance);

       // house ratio = total distance / houses per side of block.
       house_ratio = total_distance / (tohn-fromhn/2);
       double target_distance = ((target - fromhn)/2) * house_ratio;


        // calculate which segment we would fall under.
        double countedDistance = 00.00;
        for (int i=0; i<=coords.length-1;i++) {
            countedDistance += segmentMap.get(i).getDistance();
            if (countedDistance >= target_distance) {
                // do something.
                // figure out how much is left + bearing
                Double delta = countedDistance - target_distance;

            }
        }


       return null;
   }


}
