package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.geocoder.library.GeometryHelper;
import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.repository.AddrFeatRepository;

import com.vividsolutions.jts.geom.*;

import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
import org.apache.commons.codec.language.DoubleMetaphone;
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


    public List<AddrFeat> findByNameZip(String fullname, List<String> zipList) {
        List<Object[]> resultList = this.addrFeatRepository.findByNameZip(fullname, zipList);
        return this.objectToAddrFeat(resultList);
    }

    public List<AddrFeat> findFuzzy_NameZip(String fullname, List<String> zipList) {
         List<Object[]> resultList = this.addrFeatRepository.findFuzzy_NameZip(fullname, zipList);
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
            addrFeat.setStringDistance((Integer) result[3]);
            addrFeat.setName((String) result[4]);
            addrFeat.setZipl((String) result[5]);
            addrFeat.setZipr((String) result[6]);
            addrFeat.setState((String) result[7]);
            addrFeat.setLfromhn((String) result[8]); //check query
            addrFeat.setLtohn((String) result[9]);
            addrFeat.setRfromhn((String) result[10]);
            addrFeat.setRtohn((String) result[11]);
            addrFeat.setLcity((String) result[13]);
            addrFeat.setRcity((String) result[14]);

            // Todo: probably could hold off timely conversion until interpolation...
            MultiLineString mls = new GeometryHelper().stringToMultiLineString( (String)result[12] );
            addrFeat.setGeom( mls );

            addrFeatList.add(addrFeat);

        }
        return addrFeatList;
    }


}

