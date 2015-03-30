package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.exception.PlaceNotFound;
import com.taurenk.pinpoint.model.Place;
import com.taurenk.pinpoint.repository.PlaceRepository;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tauren on 3/25/15.
 * Remember the basic Repo syntax: find…By, read…By, and get…By
 */
@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;


    public Place placeByZip(String zip){return placeRepository.findPlaceByZip(zip); }


    /**
     * Retrive a list of potential places based on an entered city String
     * @param city
     * @return
     */
    public List<Place> placesByCity(String city) {
        List<Object[]>  results = placeRepository.findPlaceByCity(city);
        return this.objectToPlace(results);
    }


    /**
     * Retrieve list of potential city candidates with score.
     * @param potentialCities
     * @return
     */
    public List<Place> placesByCityList(List<String> potentialCities) {
        System.out.println("\tConverting city list...");
        List<Object[]> results = new ArrayList();
        String[][] cityList = this.assembleCityList(potentialCities);

        System.out.println("\tConverted: " + cityList );
        // Used a little hack here to save time.
        // Todo use a dynamic query t get this in one go.
        for (int i=0;i<cityList.length;i++) {
            List<Object[]> resultSet = placeRepository.placesByCityList(cityList[i][0], cityList[i][1]);
            results.addAll(resultSet);
        }
        System.out.println("\tResults returned.");
        return this.objectToPlace(results);
    }


    /**
     * Map an object generated by custom SQL to Place object
     * @param objectList
     * @return List<Place> places
     */
    private List<Place> objectToPlace(List<Object[]> objectList) {
        List<Place> places = new ArrayList();
        for (Object[] result : objectList) {
            Place place = new Place();
            place.setId((Integer)result[0]);
            place.setZip((String)result[1]);
            place.setCity((String)result[2]);
            place.setState((String)result[3]);
            place.setStringDistance((Integer)result[4]);
            places.add(place);
        }
        return places;
    }


    /**
     * Take in a list of potential cities Strings and convert them to a multi-dimensial array
     * of {City:DMetaphone(City)} pairs.
     * return this array.
     * @param potentialCities
     * @return String[][] cityMetaphoneList
     */
    private String[][] assembleCityList(List<String> potentialCities) {

        String cityList[][] = new String[potentialCities.size()][2];
        DoubleMetaphone dm = new DoubleMetaphone();

        for(int i=0; i<potentialCities.size();i++) {
            String city = potentialCities.get(i);
            cityList[i][0] = city;
            cityList[i][1] = dm.doubleMetaphone(potentialCities.get(i));
        }

        return cityList;
    }

}
