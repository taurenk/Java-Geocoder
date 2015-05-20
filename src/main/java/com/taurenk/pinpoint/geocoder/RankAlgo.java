package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by taurenk on 4/8/15.
 * Street Level address ranking via "Best Fit Algo".
 * Essentially, we see which candidates 'fit' the inputted address
 * - we do NOT take into account what they could have meant, zip+4, etc..
 */
public class RankAlgo {


    public List<AddrFeat> rankCandidates(AddressResult addressResult, List<AddrFeat> candidates) {
        Address address = addressResult.getAddress();


        // Iterate through potential candidates to rank them.
        for (AddrFeat candidate : candidates) {
            candidate = this.simpleScore(address, candidate);
        }

        /*
        System.out.println("Pre Rank:");
        for (AddrFeat af : candidates) {
            System.out.println("\t" + af.getGid() + "|" + af.getRankScore());
        }
        */
        candidates = this.rank(candidates);
        System.out.println("Post Rank:");
        for (AddrFeat af : candidates) {
           System.out.println("\t" + af.getGid() + "|" + af.getRankScore()
                                + "|" + af.getFullname() + "|" + af.getZipl()
                                + "|" + af.getSideOfStreet()
                           + "|" + af.getLfromhn() + "|" + af.getLtohn()
                           + "|" + af.getRfromhn() + "|" + af.getRtohn()

           );

        }

        return candidates;
    }


    /**
     * Rank an AddrFeat candidate based on found parameters vs user entered.
     * Simple
     * @param address
     * @param candidate
     */
    public AddrFeat simpleScore(Address address, AddrFeat candidate) {
        Double curr_score = 0.0;

        // Check zips
        if (address.getZip().equals(candidate.getZipl())) {
            curr_score += 1;
        } else if (address.getZip().equals(candidate.getZipr())) {
            curr_score += 1;
        }


        // Check street
        if (address.getStreet().equals(candidate.getFullname())) {
            curr_score += 2;
        }

        // Check left Range then Right
        if (address.getNumber() != null) {
            //System.out.println("\tRanking Range...");
            if (this.checkRange(candidate.getLfromhn(), candidate.getLtohn(), address.getNumber())){
                curr_score += .5;
                candidate.setSideOfStreet("L");
            } else if (this.checkRange(candidate.getRfromhn(), candidate.getRtohn(), address.getNumber())) {
                curr_score += .5;
                candidate.setSideOfStreet("R");
            }
        } //End Check Range
        candidate.setRankScore(curr_score);
        return candidate;
    }


    /**
     * Given an array of array[score][candidate_id], sort them via bubble sort.
     * Return the top scoring candidate.
     * Oh, and there is no tie breaker...
     * TODO change this to a merge sort
     * @return
     */
    public List<AddrFeat> rank(List<AddrFeat> candidates){

        Collections.sort(candidates, new Comparator<AddrFeat>() {
                @Override
                public int compare(AddrFeat a1, AddrFeat a2) {

                    if (a1.getRankScore() > a2.getRankScore()) {
                        return -1;
                    } else if (a1.getRankScore() < a2.getRankScore()) {
                        return 1;
                    }
                    return 0;
                }
            });

        return candidates;
    }

    /**
    * Check if a given number is between address ranges.
    * @param fromHn
    * @param toHn
    * @param target
    * @return found
    */
    private Boolean checkRange(String fromHn, String toHn, String target) {
        // Be careful- from/to numbers contain letters AND dashes.
        Boolean found = false;
        try {
            int intFromHn = Integer.parseInt(fromHn);
            int intToHn = Integer.parseInt(toHn);
            int intTarget = Integer.parseInt(target);


            if ((intFromHn <= intTarget) && (intTarget <= intToHn)) {
                if ( (intTarget % 2) == (intFromHn % 2)) {
                    found = true;
                }
            } else if ((intFromHn >= intTarget) && (intTarget >= intToHn)) {
                if ( (intTarget % 2) == (intFromHn % 2)) {
                    found = true;
                }
            }

        } catch (Exception ex) {
            // generically catch any data conversion errors!
        }
        return found;
    }




}
