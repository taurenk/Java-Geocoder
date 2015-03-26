package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
public interface PlaceRepository extends CrudRepository<Place, Integer> {

    // Find Place by given zipcode
    Place findByZip(String zip);

    @Query("select p from Place p where p.city = ?1")
    List<Place> findByCity(String city);

    /**
     *
     * TODO: implement DTo to return distance
     * TODO: http://stackoverflow.com/questions/28168408/custom-query-spring-data-jpa-rest
     * @param city
     * @return
     */
    // @Query("select p from Place p where p.city = ?1")
    // @Query(value = "SELECT * FROM place WHERE place = ?0", nativeQuery = true)
    @Query(value = "SELECT * FROM place " +
            "WHERE dmetaphone(?0) = dmetaphone(place) " +
            "AND  levenshtein(place, ?0) <= 3;", nativeQuery = true)
    List<Place> findByCityFuzzy(String city);

}


