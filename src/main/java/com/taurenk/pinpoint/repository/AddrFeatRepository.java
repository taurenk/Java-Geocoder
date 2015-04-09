package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
public interface AddrFeatRepository extends CrudRepository<AddrFeat, Integer> {

    List<AddrFeat> findByFullname(String fullname);


    @Query(value="SELECT gid, tlid, fullname, levenshtein(fullname, :test ), name, " +
            "zipl, zipr, state, lfromhn, ltohn, rfromhn, rtohn, ST_asText(geom) " +
            "FROM addrfeat " +
            "WHERE fullname LIKE :test " +
            "AND ( zipl in  ( :ziplist ) OR zipr in ( :ziplist) )" +
            "AND ( levenshtein(fullname, :test ) < 2 ); ", nativeQuery=true)
    List<Object[]> findByNameZip(@Param("test") String street, @Param("ziplist") List<String> zipList);



    @Query(value="SELECT gid, tlid, fullname, levenshtein(fullname, :test ), name, " +
            "zipl, zipr, state, lfromhn, ltohn, rfromhn, rtohn, ST_asText(geom) " +
            "FROM addrfeat " +
            "WHERE dmetaphone(fullname) LIKE dmetaphone( :test )  " +
            "AND ( zipl in  ( :ziplist ) OR zipr in ( :ziplist) )" +
            "AND ( levenshtein(fullname, :test ) <= 2 ); ", nativeQuery=true)
    List<Object[]> findFuzzy_NameZip(@Param("test") String street, @Param("ziplist") List<String> zipList);

}
