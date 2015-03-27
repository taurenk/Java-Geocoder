package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
public interface PlaceRepository extends CrudRepository<Place, Integer> {

    Place findPlaceByZip(String zip);

    List<Place> findPlaceByCity(String city);

    /**
     * Custom Query to find a place record based on a fuzzy city search
     *
     * @param city
     * @return List<place>
     */
    @Query(value = "SELECT * FROM Place " +
            "WHERE dmetaphone(?#{[0]}) = dmetaphone(place) " +
            "AND  levenshtein(place, ?#{[0]}) <= 3;", nativeQuery = true)
    List<Place> findPlaceByCityFuzzy(String city);


    /**
     * Expirmental Method - extract place data with the levenshtien score
     *
     * @param city
     * @return
     */
    @Query(value = "SELECT id, zip, place, name1, levenshtein(place, ?#{[0]}) FROM Place " +
            "WHERE dmetaphone(?#{[0]}) = dmetaphone(place) " +
            "AND  levenshtein(place, ?#{[0]}) <= 3;", nativeQuery = true)
    List<Object[]> findPlaceByCity_score(String city);


    /*
    @Query(value = "SELECT * Place WHERE dmetaphone(place) IN (?#{[0]})", nativeQuery = true)
    List<Place> placesByCityList(String cityList);
    */
}


