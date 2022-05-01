package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.APIrequest;
import com.example.demo.Cache;
import com.example.demo.model.CovidData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovidDataService {
    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //private final String API_URL = "https://covid-19-statistics.p.rapidapi.com/reports?";
  
    @Autowired
    private Cache cache;

    @Autowired
    private APIrequest externalAPI;


   

    public Optional<CovidData> getCurrentMetricByLocation(String country) throws IOException, MalformedURLException, InterruptedException, NullPointerException{
            Calendar c = Calendar.getInstance();
            LocalDate currentdate = LocalDate.now();
            String currentDay = String.valueOf(currentdate.getDayOfMonth());
            String currentMonth = String.valueOf(c.get(Calendar.MONTH));
            if(c.get(Calendar.MONTH) < 10){
                currentMonth = "0"+currentMonth;
            }
            if(Integer.parseInt(currentDay) < 10){
                currentDay = "0"+currentDay;
            }
            String currentYear = String.valueOf(currentdate.getYear());
            Optional oCache = cache.getMetrics(country);
            if(oCache.isPresent()){
                //System.out.println("Cachereturn");
                //System.out.println(oCache.get());
                return oCache;
            }
            
            CovidData CovMetric = externalAPI.getMetricsFromAPI(country, currentYear+"-"+currentMonth+"-"+currentDay);
            cache.addMetrics(country, CovMetric.getDate(), CovMetric);
            return Optional.of(CovMetric);
        
        
    }

    public Optional<CovidData> getMetricsByLocationAndDay(String country,String date)throws IOException, MalformedURLException, InterruptedException{

                Optional oCache = cache.getMetricsbyDate(country,date);
                if(oCache.isPresent()){
                    LOGGER.log(Level.INFO, "\nValue found in cache!");
                    return oCache;
                }

                LOGGER.log(Level.INFO, "\nValue not found in cache!\nFetching from api");
                
                CovidData CovMetric = externalAPI.getMetricsFromAPI(country,date);
                cache.addMetrics(country, CovMetric.getDate(), CovMetric);
                return Optional.of(CovMetric);
        
        
    }
}
