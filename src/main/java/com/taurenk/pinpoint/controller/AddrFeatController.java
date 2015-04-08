package com.taurenk.pinpoint.controller;

import com.taurenk.pinpoint.geocoder.Address;
import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.service.AddrFeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        List<String> zipList = new ArrayList();
        zipList.add("10707");
        zipList.add("11949");
        //return this.addrFeatService.findByNameZip("ELM ST", zipList);
        return this.addrFeatService.findFuzzy_NameZip("ELM ST", zipList);
    }
}
