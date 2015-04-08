package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;

import java.util.List;

/**
 * Created by taurenk on 4/8/15.
 */
public class RankAlgo {

    public void rankAddrFeats(AddressResult addressResult, List<AddrFeat> candidates) {
        Address address = addressResult.getAddress();

        // Keep track of the scores
        int[] scoreIndex = new int[candidates.size()-1];

        // Iterate through potential candidates to rank them.
        for (AddrFeat candidate : candidates) {
            int curr_score = 0;
        }
    }
}
