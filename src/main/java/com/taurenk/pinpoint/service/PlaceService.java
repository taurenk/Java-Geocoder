package com.taurenk.pinpoint.service;

import com.taurenk.pinpoint.exception.InternalServerException;
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

    /**
     * Retrieve place [city] by zip. Zipcodes are assumed to be unique.
     * @param zip
     * @return
     */
    public Place placeByZip(String zip) {
        Place place = null;
        try {
            place = placeRepository.findPlaceByZip(zip);
        } catch (Exception ex) {
            System.out.printf("Error in PlaceService: %s\n", ex.toString());
            throw new InternalServerException();
        }

        return place;
    }

    /**
     * Retrieve a list of potential places based on an entered city String
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
    public List<Place> placesByCityList(List<String> potentialCities, String state) {
        String[][] cityList = this.assembleCityList(potentialCities);
        // if no state is found, look at all states/zips.
        if (state==null) {state = "%";}
        List<Object[]> resultSet = placeRepository.placesByCityList(cityList[0][0], cityList[0][1],
                                                    cityList[1][0], cityList[1][1],
                                                    cityList[2][0], cityList[2][1],
                                                    state);
        return this.objectToPlace(resultSet);
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

        String cityList[][] = new String[3][2];
        DoubleMetaphone dm = new DoubleMetaphone();
        int index = 0;
        for (int i=0; i<potentialCities.size();i++) {
            String currCity = potentialCities.get(i);
            cityList[i][0] = currCity;
            cityList[i][1] = dm.doubleMetaphone(currCity);
            index++;
        }

        // fill rest of list up
       for (int i=index; i<3; i++){
           cityList[i][0] = "NULL";
           cityList[i][1] = "NULL";
       }
        return cityList;
    }

}
