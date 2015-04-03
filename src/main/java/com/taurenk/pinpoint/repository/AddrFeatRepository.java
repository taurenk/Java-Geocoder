package com.taurenk.pinpoint.repository;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
public interface AddrFeatRepository extends CrudRepository<AddrFeat, Integer> {

    List<AddrFeat> findByFullname(String fullname);

}
