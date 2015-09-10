package com.apache.apiautomation.apimanager;

import com.apache.apiautomation.validation.APIResultValidation;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;



/**
 * Created by ajain on 9/9/15.
 */
public class APIManager {

    public static String  apiFramework = "RestAssured";




    private static javax.ws.rs.core.Response doGet_Jersey(String apiPath){

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(feature) ;
        Client client = ClientBuilder.newClient(clientConfig);
        javax.ws.rs.core.Response response = client.target("http://c6401.ambari.apache.org:8080/api/v1" + apiPath).request().get();

        return response;

    }

    private static Response doGet_RestAssured(String apiPath){
        return given().auth().preemptive().basic("admin", "admin").when().get("http://c6401.ambari.apache.org:8080/api/v1" + apiPath);
    }

    public static Object doGet(String apiPath) throws Exception{
        if(apiFramework.equals("RestAssured"))
            return doGet_RestAssured(apiPath);
        else
            return doGet_Jersey(apiPath);

    }




    private static boolean validateResult_Jersey(String apiPath,String key,List<String> value){
        try {

            String jsonResponse = doGet_Jersey(apiPath).readEntity(String.class);
            Gson g = new Gson();
            Map jsonMap = g.fromJson(jsonResponse, Map.class);

            String[] splitKey = key.split("\\.");

            List hostInfo = (ArrayList) jsonMap.get(splitKey[0]);

            List<String> hostNames = new ArrayList<String>();

            for(int i=0;i<hostInfo.size();i++)
                hostNames.add(((Map) ((Map) hostInfo.get(i)).get(splitKey[1])).get(splitKey[2]).toString());


            return APIResultValidation.validate(value,hostNames);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean validateResult_RestAssured(String apiPath,String key,List<String> value){
        try {
            RestAssured.registerParser("text/plain", com.jayway.restassured.parsing.Parser.JSON);
//            System.out.println(doGet_RestAssured(apiPath).getBody().asString());
            doGet_RestAssured(apiPath).then().body(key, hasItems(value.toArray(new String[0])));
            return true;
         }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean validateResult(String apiPath,String key,List<String> value) throws Exception{
        if(apiFramework.equals("RestAssured"))
            return validateResult_RestAssured(apiPath,key,value);
        else
            return validateResult_Jersey(apiPath,key,value);
    }
}
