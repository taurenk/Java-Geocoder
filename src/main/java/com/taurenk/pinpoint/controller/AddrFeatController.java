package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.geocoder.Address;
import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.AddrFeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tauren on 4/3/15.
 */
@RestController
@RequestMapping("/api/addrfeat")
public class AddrFeatController {

    @Autowired
    private AddrFeatService addrFeatService;

    @RequestMapping("/byFullname")
    public List<AddrFeat> byFullname(@RequestParam(value="fullname") String fullname) {
      return  addrFeatService.getByFullname(fullname);
    }

    @RequestMapping("/test")
    public List<AddrFeat> test() {
            return this.addrFeatService.fuzzySearchByName("CAPUTO DR", "11949", "11949");
    }
}
