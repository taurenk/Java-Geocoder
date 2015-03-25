package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tauren on 3/25/15.
 */
public interface PlaceRepository extends CrudRepository<Place, Integer> {

    // Find Place by given zipcode
    Place findByZip(String zip);


}
