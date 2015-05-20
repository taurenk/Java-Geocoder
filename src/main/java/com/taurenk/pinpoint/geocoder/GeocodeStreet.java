package com.taurenk.pinpoint.geocoder;

import com.taurenk.pinpoint.model.AddrFeat;
import com.taurenk.pinpoint.service.AddrFeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taurenk on 4/4/15.
 * Tying a new approach, let's seperate out this whole thing into a class!
 */
@Component
public class GeocodeStreet {

    @Autowired
    AddrFeatService addrFeatService;


    public Address geocodeStreet(AddressResult addressResult) {
        Address addr = addressResult.getAddress();

        List<AddrFeat> streetCandidates = this.geocodeByNameZip(addr.getStreet(), addressResult.getPotentialZips());

        System.out.println("\tStreet: " + addr.getStreet());
        System.out.println("\t# of Potential Zips:" + addressResult.getPotentialZips().size() );
        addressResult.getPotentialZips().forEach(item -> System.out.print(item + "|"));

        System.out.println("\t# of streetCandidates:" + streetCandidates.size());


        if (streetCandidates != null) {
            streetCandidates = new RankAlgo().rankCandidates(addressResult, streetCandidates);
            addr = this.setAddressData(streetCandidates.get(0), addr);
            // Attempt to interpolate number. If an error hapens, we just set it to null.
            addr.setCoordinate(new Interpolation().interpolate(streetCandidates.get(0),addr.getNumber()));
            System.out.println("Address = " + addr.toString());
        } else {
            //No candidates found
            return null;
        }
        return addr;
    }


    /**
     * Get all AddrFeats matching streetname and zipcodes.
     * @param streetName
     * @param zipString
     * @return
     */
    private List<AddrFeat> geocodeByNameZip(String streetName, List<String> zipString) {
        try {
            return  addrFeatService.findFuzzy_NameZip(streetName, zipString);
        } catch (Exception ex) {
            System.out.println("Error in GeocodeStreet.geocodeByNameZip");
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * set street, city, state, zip from found result
     * @param result
     * @param addr
     * @return
     */
    private Address setAddressData(AddrFeat result, Address addr) {
        addr.setStreet(result.getFullname());
        addr.setCity(result.getLcity());
        addr.setZip(result.getZipl());
        return addr;
    }

}
