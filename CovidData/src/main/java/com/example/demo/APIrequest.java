package com.example.demo;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.util.ArrayList;

import java.util.List;

import java.util.logging.Logger;

import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.demo.model.Country;
import com.example.demo.model.CovidData;


public class APIrequest {

    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    

    public CovidData getMetricsFromAPI(String country, String date) throws IOException, MalformedURLException, InterruptedException{

        LOGGER.log(Level.INFO, String.format("Fetching from API for country %s and date %s",country,date));
        //date=2020-04-16&
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://covid-19-statistics.p.rapidapi.com/reports?date="+date+"&region_name="+country))
		.header("X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com")
		.header("X-RapidAPI-Key", "998c5eac9cmsh29758d4966279eep1e228cjsnb9226ded3d1d")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(response.body());
        JSONObject myObject = new JSONObject(response.body());
        //Make API Request and get
        System.out.println(myObject.toString());
        
        return getMetricsFromJson(myObject); 
    }

    private CovidData getMetricsFromJson(JSONObject rootobj){
        List<CovidData> dataList = new ArrayList<CovidData>();
        JSONArray List = rootobj.getJSONArray("data");

        
            
            JSONObject JObject = List.getJSONObject(0);

            String date = JObject.get("date").toString();
            int conf = Integer.parseInt(JObject.get("confirmed").toString());
            int deaths = Integer.parseInt(JObject.get("deaths").toString());
            int reco = Integer.parseInt(JObject.get("recovered").toString());
            int conf_diff = Integer.parseInt(JObject.get("confirmed_diff").toString());
            int deaths_diff = Integer.parseInt(JObject.get("deaths_diff").toString());
            int reco_diff = Integer.parseInt(JObject.get("recovered_diff").toString());
            int active = Integer.parseInt(JObject.get("active").toString());
            int activ_diff = Integer.parseInt(JObject.get("active_diff").toString());
            double fatality_rate = Double.parseDouble(JObject.get("fatality_rate").toString());

            JSONObject country =  (JSONObject) JObject.get("region");
            //System.out.println(country);    System.out.println(JObject); 
            String iso = country.get("iso").toString();
            String name = country.get("name").toString();
            //System.out.println(iso);
            dataList.add(new CovidData(date, conf, deaths, reco, conf_diff, deaths_diff, reco_diff, active, 
            activ_diff, fatality_rate, new Country(iso, name)));
        //System.out.println(dataList);
        return dataList.get(0);
    }

   
}
