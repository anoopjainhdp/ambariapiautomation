package com.apache.apiautomation.ambariserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajain on 9/9/15.
 */
public class AmbariServer {
    public static List<String> getHostNames(){
        List<String> hostName = new ArrayList<String>();

        //Get actual data from DB
        hostName.add("c6401.ambari.apache.org");
        hostName.add("c6402.ambari.apache.org");
        hostName.add("c6403.ambari.apache.org");

        return hostName;
    }
}
