package com.taurenk.pinpoint.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tauren on 3/25/15.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "Welcome to PinPoint Geocoder!";
    }
}
