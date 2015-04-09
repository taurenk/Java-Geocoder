package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;

import java.util.List;

/**
 * Created by taurenk on 4/8/15.
 */
public class RankAlgo {


    public AddressResult rank(AddressResult addressResult, List<AddrFeat> candidates) {
        Address address = addressResult.getAddress();

        int[][] candidateScores = new int[candidates.size()-1][2];

        // Iterate through potential candidates to rank them.
        int index = 0;
        for (AddrFeat candidate : candidates) {
            candidateScores[index][0] = this.simpleRank(address, candidate);
            candidateScores[index][1] = index;
        }

        // sort the list by score...
        return null;
    }


    /**
     * Rank an AddrFeat candidate based on found parameters vs user entered.
     * Simple
     * @param address
     * @param candidate
     */
    public int simpleRank(Address address, AddrFeat candidate) {
        int curr_score = 0;
        // Check zips
        if (!address.getZip().equals(candidate.getZipl())) {
            curr_score += 1;
        } else if (!address.getZip().equals(candidate.getZipr())) {
            curr_score += 1;
        }

        // TODO Check city
        // Check street
        if (!address.getStreet().equals(candidate.getFullname())) {
            curr_score += candidate.getStringDistance();
        }

        return 0;
    }


     // Check if a target house number is within an address range
    private Boolean checkRange(String fromHn, String toHn, String target) {
        // Todo: Becareful- from/to numbers contain letters AND dashes.
        return false;
    }
}
