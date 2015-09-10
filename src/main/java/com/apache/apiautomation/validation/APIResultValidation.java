package com.apache.apiautomation.validation;

import java.util.List;

/**
 * Created by ajain on 9/9/15.
 */
public class APIResultValidation {

    public static boolean validate(List a, List b){
        int listAElementCount = a.size();
        int listBElementCount = b.size();

        //List must be of same size
        if(listAElementCount != listBElementCount)
            return false;

        for(int i=0;i<listAElementCount;i++)
            b.remove(a.get(i));

        return (b.size()==0);
    }

}
