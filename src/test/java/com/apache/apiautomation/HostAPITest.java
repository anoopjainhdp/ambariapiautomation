package com.apache.apiautomation;

import com.apache.apiautomation.ambariapi.HostAPI;
import com.apache.apiautomation.ambariserver.AmbariServer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by ajain on 9/9/15.
 */
public class HostAPITest {

    @Test
    public static void testHostNames() throws Exception{

        //Expected Result
        List<String> hostsOnServer = AmbariServer.getHostNames();
        //Validate actual with expected
        Assert.assertTrue(HostAPI.validateHostNames(hostsOnServer));

    }
}
