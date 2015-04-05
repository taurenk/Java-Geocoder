package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
public interface AddrFeatRepository extends CrudRepository<AddrFeat, Integer> {

    List<AddrFeat> findByFullname(String fullname);


    /*
    @Query(value="SELECT gid, tlid, fullname, levenshtein(fullname, ?#{[0]} ), " +
            "zipl, zipr, state, lfromhn, ltohn, rfromhn, rtohn, geom " +
            "FROM addrfeat WHERE dmetaphone(fullname) = dmetaphone( ?#{[0]} ) " +
            "AND ( zipl = ?#{[1]} OR zipr = ?#{[2]} ) " +
            "AND levenshtein(fullname, ?#{[0]} ) <= 2 ; ", nativeQuery=true)
    */
    @Query(value="SELECT gid, tlid, fullname, levenshtein(fullname, ?#{[0]} ), name, " +
            "zipl, zipr, state, lfromhn, ltohn, rfromhn, rtohn, ST_asText(geom) " +
             // ", state, lfromhn, ltohn, rfromhn, rtohn, geom " +
            "FROM addrfeat " +
            "WHERE fullname LIKE ?#{[0]} " +
            "AND levenshtein(fullname, ?#{[0]} ) < 2 ; ", nativeQuery=true)
    List<Object[]> fuzzySearchByName(String street);
}
