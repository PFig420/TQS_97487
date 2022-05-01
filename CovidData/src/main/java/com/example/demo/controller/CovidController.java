package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.CovidData;
import com.example.demo.service.CacheService;
import com.example.demo.service.CovidDataService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CovidController {
    
    @Autowired
    public CacheService cacheStatisticsService;

    @Autowired
    public CovidDataService CovService;

    @GetMapping("/cache/stats")
    @ResponseBody
    public  ResponseEntity<Map<String,Integer>> cacheAllStats(){
        return ResponseEntity.ok().body(cacheStatisticsService.getAllStats());
    }

    @GetMapping("/cache/stats/size")
    @ResponseBody
    public  ResponseEntity<Integer>  cacheSize(){
        return ResponseEntity.ok().body(cacheStatisticsService.getCacheSize());
    }

    @GetMapping("/cache/stats/requests")
    @ResponseBody
    public  ResponseEntity<Integer> cacheRequests(){
        return ResponseEntity.ok().body(cacheStatisticsService.getCacheRequests());
    }

    @GetMapping("/cache/stats/hits")
    @ResponseBody
    public  ResponseEntity<Integer> cacheHits(){
        return ResponseEntity.ok().body(cacheStatisticsService.getCacheHits());
    }

    @GetMapping("/cache/stats/misses")
    @ResponseBody
    public  ResponseEntity<Integer> cacheMisses(){
        return ResponseEntity.ok().body(cacheStatisticsService.getCacheMisses());
    }


    @GetMapping("/covidstats/{country}")
    @ResponseBody
    public ResponseEntity  currentMetric(@PathVariable(value="country") String country) throws IOException, MalformedURLException, InterruptedException{
        Optional <CovidData> CovMetric = CovService.getCurrentMetricByLocation(country);
        //System.out.println(CovMetric);
        //JSONObject jo = new JSONObject(CovMetric);
        if(CovMetric.isPresent()){
            return ResponseEntity.ok().body(CovMetric);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find data for this city check spelling" );
    }

    @GetMapping("/covidstats/{country}/{date}")
    public ResponseEntity metricsByDayandLocation(@PathVariable(value="country") String country, @PathVariable(value = "date") String date)throws IOException, MalformedURLException, InterruptedException{
            Optional<CovidData> CovidMetrics = CovService.getMetricsByLocationAndDay(country,date);

            if(CovidMetrics.isPresent()){
                CovidData CovMetrics = CovidMetrics.get();
            
                return ResponseEntity.ok().body(CovMetrics);
            }
            System.out.println(country);
            System.out.println(date);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find data for this city check spelling" );
    }

    
}
