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

    /**
     * Find a place by fuzzy searching City
     *
     * @param city
     * @return
     */
    @Query(value = "SELECT id, zip, place, name1, levenshtein(place, ?#{[0]}) FROM Place " +
            "WHERE dmetaphone(?#{[0]}) = dmetaphone(place) " +
            "AND  levenshtein(place, ?#{[0]}) <= 3;", nativeQuery = true)
    List<Object[]> findPlaceByCity(String city);


    /**
     *
     * @param city
     * @param cityMetaphone
     * @return
     */
    @Query(value= "SELECT place.id, place.zip, place.place, place.name1, levenshtein(place.place, x.city) " +
            "FROM place JOIN ( VALUES " +
            //" ?#{[1]} ) " +
            " (?#{[0]}, ?#{[1]}) ) " +
            "AS x (city, city_dmetaphone) " +
            "ON dmetaphone(place.place) = x.city_dmetaphone " +
            "WHERE levenshtein(place.place, x.city) <= 3;", nativeQuery=true)
    List<Object[]> placesByCityList(String city, String cityMetaphone);
}


