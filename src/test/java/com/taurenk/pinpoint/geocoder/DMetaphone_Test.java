package com.taurenk.pinpoint.geocoder;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.util.StringUtils;

/**
 * Created by tauren on 3/27/15.
 */
public class DMetaphone_Test {

    public DMetaphone_Test() {

    }



    public void testCityCompare(String addr, String city ) {
        String[] cityTokens = city.split(" ");
        System.out.println("\tCity Token:" + city);
        System.out.println("\tLength of CityToken: " + cityTokens.length);
        String[] addrTokens = addr.split(" ");

        // We want to take the LONGEST VALUE HERE.
        // Mcguirk street East Hampton -> [Hampton], [East Hampton]
        // Sort by length?

        String[] metaAddrTokens = this.createMetaphoneList(addrTokens);
        String[] metaCityTokens = this.createMetaphoneList(cityTokens);


    }

    // Simply return a list of corresponding metaphone values.
    public String[] createMetaphoneList(String[] items) {
        String[] newArray = new String[items.length];
        DoubleMetaphone dm = new DoubleMetaphone();

        for (int i=0; i<items.length;i++){
            newArray[i] = dm.doubleMetaphone(items[i]);
        }
        //items = simpleSort(newArray);
        return newArray;
    }

    // Sort by size
    public String[] simpleSort(String[] items) {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        String temp;   //holding variable

        System.out.println("UnSorted List = " + items.toString());
        while ( flag ) {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < items.length -1;  j++ )  {
                int item1Count = StringUtils.countOccurrencesOf(items[j]," ");
                int item2Count = StringUtils.countOccurrencesOf(items[j+1]," ");

                if ( item1Count < item2Count ) {
                    temp = items[ j ];                //swap elements
                    items[ j ] = items[ j+1 ];
                    items[ j+1 ] = temp;
                    flag = true;              //shows a swap occurred
                }
            }
        }
        System.out.println("Sorted List = " + items[0]);
        return null;
    }

    public static void main(String[] args) {

        String[] anArray = {"Caputo Drive Manorville", "Caputo Drive East Hampton", "Manorville", "Caputo Drive", "Caputo", "Drive"};

        for (String testString : anArray) {
            //String dm = new DoubleMetaphone().doubleMetaphone(testString);
            //System.out.println(testString + " : " + dm);
        }

        DMetaphone_Test dm = new DMetaphone_Test();
        dm.testCityCompare("Caputo Drive Manorville", "Manorville");
    }


}
