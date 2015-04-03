package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.repository.AddrFeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
@Service
public class AddrFeatService {

    @Autowired
    private AddrFeatRepository addrFeatRepository;

    public List<AddrFeat> getByFullname(String fullname) { return this.addrFeatRepository.findByFullname(fullname); }
}
