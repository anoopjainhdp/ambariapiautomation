package com.apache.apiautomation.ambariapi;

import com.apache.apiautomation.apimanager.APIManager;

import java.util.List;

/**
 * Created by ajain on 9/9/15.
 */
public class HostAPI {


    public static boolean validateHostNames(List hostNames) throws Exception{
        return APIManager.validateResult("/hosts","items.Hosts.host_name",hostNames);
     }
}
