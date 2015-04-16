package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.model.Place;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by taurenk on 4/13/15.
 */
public class RankCity {

    /**
     * Take in an Address and place candidates to score and rank them.
     * @param address
     * @param placeCandidates
     * @return
     */
    public List<Place> rankCity(Address address, List<Place> placeCandidates) {


        for (Place potentialPlace : placeCandidates) {
            potentialPlace.setScore(this.simpleScore(address, potentialPlace));
        }

        placeCandidates = this.rank(placeCandidates);
        return placeCandidates;
    }

    /**
     * Score potential place [city] records. I need to play with scoring,
     * are city matches worth more than zip? which are users more likely to get right?
     * @param address
     * @param place
     * @return
     */
    private Double simpleScore (Address address,  Place place) {
        Double curr_score = 0.0;
        if (address.getZip().equals(place.getZip())) {
             curr_score += 1;
        }
        if(address.getCity().equals(place.getCity())) {
             curr_score += 2;
        } else if (StringUtils.getLevenshteinDistance(address.getCity(), place.getCity()) <= 2) {
             curr_score += 1;
        }
        place.setScore(curr_score);

        return curr_score;
    }


    /**
     * Simple Sort
     * @param placeCandidates
     * @return
     */
    private List<Place> rank(List<Place> placeCandidates) {

        Collections.sort(placeCandidates, new Comparator<Place>() {
            @Override
            public int compare(Place p1, Place p2) {

                if (p1.getScore() > p2.getScore()) {
                    return -1;
                } else if (p1.getScore() < p2.getScore()) {
                    return 1;
                }
                return 0;
            }
        });

        return placeCandidates;
    }

}
