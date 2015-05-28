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
        try {
            Coordinate[] coords = new LinearIterator(addrFeat.getGeom()).getLine().getCoordinates();
            // Try out "WHERE IT BELONGS" Rule
            // If odd ->
            if (addrFeat.getSideOfStreet() == null) {
                System.out.println("\t\tInterpolation: no str side specified.");
                // TODO; right now just set it to right for testing
                addrFeat.setSideOfStreet("R");
            }



            if (addrFeat.getSideOfStreet() == null) {
                return coords[0];
            }

            System.out.println("\t\tInterpolation: Str side specified:" + addrFeat.getSideOfStreet());
            int from;
            int to;
            int hn;
            // TODO: MAKE THIS PRETTY!
            if (addrFeat.getSideOfStreet().equals("R")) {
                from = Integer.parseInt(addrFeat.getRfromhn());
                to = Integer.parseInt(addrFeat.getRtohn());
                hn = Integer.parseInt(targetNumber);
            } else {
                from = Integer.parseInt(addrFeat.getLfromhn());
                to = Integer.parseInt(addrFeat.getLtohn());
                hn = Integer.parseInt(targetNumber);

             }
             return findCoord(from, to, hn, coords);

        } catch (Exception ex) {
            System.out.println("\t\tInterpolation Err:" + ex.toString());
            return null;
        }
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
        // TODO: This should be encapsulated in a try/catch block
       //           Due to intensive use of math.
       Map<Integer, Segment> segmentMap = new HashMap();
       Coordinate newCoord = new Coordinate(coords[0].x, coords[0].y);
       double total_distance = 00.00;
       double house_ratio;
       double segmentDistance;

       System.out.println("\t\tLength of coords:" + coords.length);
       for (int i=0; i<=coords.length-2; i++){
           segmentDistance = new GeomMath().haversine(coords[i], coords[i+1]);
           segmentMap.put(i, new Segment(coords[i], coords[i+1], segmentDistance));
           total_distance += segmentDistance;
           //System.out.print("\t" + i);
       }


       System.out.println("Total Distance=" + total_distance);

       // house ratio = total distance / houses per side of block.
       house_ratio = total_distance / (tohn-fromhn/2);
       double target_distance = ((target - fromhn)/2) * house_ratio;


        // calculate which segment we would fall under.
        double countedDistance = 00.00;
        for (int i=0; i<=coords.length-2;i++) {
            countedDistance += segmentMap.get(i).getDistance();
            if (countedDistance >= target_distance) {
                // do something.
                // figure out how much is left + bearing
                Double delta = countedDistance - target_distance;
                newCoord = new GeomMath().createNewCoordinate(
                                        segmentMap.get(i).getStart().x,
                                        segmentMap.get(i).getStart().y,
                                        delta,
                                        new GeomMath().getBearing(
                                                segmentMap.get(i).getStart(),
                                                segmentMap.get(i).getEnd()
                                            )
                                        );
                System.out.println("Calculated Point:" + newCoord.x + "," + newCoord.y );
                break;
            }
        }


       return newCoord;
   }


}
