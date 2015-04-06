package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.geocoder.library.GeometryHelper;
import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.repository.AddrFeatRepository;

import com.vividsolutions.jts.geom.*;

import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MutableCallSite;
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

            // Convert all result objects to their respected types and set AddrFeat object.
            addrFeat.setGid((Integer) result[0]);
            addrFeat.setFullname((String) result[2]);
            addrFeat.setName((String) result[4]);
            addrFeat.setZipl((String) result[5]);
            addrFeat.setZipr((String) result[6]);
            addrFeat.setState((String) result[7]);
            addrFeat.setLtohn((String) result[8]);
            addrFeat.setLtohn((String) result[9]);
            addrFeat.setRfromhn((String) result[10]);
            addrFeat.setRtohn((String) result[11]);
            // Todo: Do we really want to do this for *every* AddrFeat found?
            MultiLineString mls = new GeometryHelper().stringToMultiLineString( (String)result[12] );
            addrFeat.setGeom( mls );

            addrFeatList.add(addrFeat);

        }
        return addrFeatList;
    }


}

