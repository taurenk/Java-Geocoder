package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.repository.AddrFeatRepository;

import com.vividsolutions.jts.geom.*;

import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
@Service
public class AddrFeatService {

    @Autowired
    private AddrFeatRepository addrFeatRepository;

    public List<AddrFeat> getByFullname(String fullname) { return this.addrFeatRepository.findByFullname(fullname); }


    /**
     * Query Address Feature by name, left zipcode, and right zipcode.
     * @param fullname
     * @param zipl
     * @param zipr
     * @return
     */
    public List<AddrFeat> fuzzySearchByName(String fullname, String zipl, String zipr) {
        //List<Object[]>  resultList = this.addrFeatRepository.fuzzySearchByName(fullname, zipl, zipr);
        List<Object[]>  resultList = this.addrFeatRepository.fuzzySearchByName(fullname);
        System.out.println("HERE");
        return this.objectToAddrFeat(resultList);
    }

    /**
     * Convert result set of custom SQL to an AddrFeat object.
     * @param objectList
     * @return
     */
    private List<AddrFeat> objectToAddrFeat(List<Object[]> objectList) {
        List<AddrFeat> addrFeatList = new ArrayList<AddrFeat>();
        for (Object[] result : objectList) {
            AddrFeat addrFeat = new AddrFeat();

            //gid, tlid, fullname, levenshtein(fullname, ?#{[0]} ), name, " +
            //"zipl, zipr, state, lfromhn, ltohn, rfromhn, rtohn, geom

            addrFeat.setGid((Integer) result[0]);
            addrFeat.setFullname( (String) result[2]);
            //addrFeat.setTlid((String)result[1]);


            /*
            addrFeat.setLfromhn((String) result[5]);
            addrFeat.setLtohn((String) result[6]);

            addrFeat.setRfromhn((String) result[7]);
            addrFeat.setRtohn((String) result[8]);

            addrFeat.setZipl((String) result[9]);
            addrFeat.setZipr((String) result[10]);
               */
            //addrFeat.setState((String) result[7]);

            //String geom = this.convertGeomToString( (String)result[12]);
            addrFeat.setGeom( this.convertGeomToString("demo") );

            addrFeatList.add(addrFeat);
            System.out.println("\tADDR FEAT: " + addrFeat.getFullname() );

        }
        return addrFeatList;
    }

    private MultiLineString convertGeomToString(String geometryAsText) {

        GeometryFactory gm = new GeometryFactory();

        LineString[] lines = new LineString[2];
        lines[0] = this.lineStringCreator();
        lines[1] = this.lineStringCreator();

        return gm.createMultiLineString(lines);
    }


    // Create LineString..
    private LineString lineStringCreator() {
        //int numCoords = getRandomNumCoords(2);
        Double x = 123.123;
        Double y = 321.321;
        Double x1 = 345.123;
        Double y1 = 543.321;
        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = new Coordinate(x,y);
        coordinates[1] = new Coordinate(x1,y1);
        return new GeometryFactory().createLineString(coordinates);
    }

}

