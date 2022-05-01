package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Map;

import java.util.Optional;

import com.example.demo.model.CovidData;

import java.util.Calendar;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

@Component
public class Cache {

    enum STATISTICS {
        REQUESTS, SIZE, HITS, MISSES
    }

    private Map <Pair<String,String>,Pair<LocalDateTime,CovidData>> cache;
    private Map<STATISTICS, Integer> statistics;
    private Calendar c = Calendar.getInstance();
    public Cache(){
        this.cache = new HashMap<Pair<String,String>,Pair<LocalDateTime,CovidData>>();
        this.statistics = new HashMap<STATISTICS,Integer>();
        this.statistics.put(STATISTICS.REQUESTS,0);
        this.statistics.put(STATISTICS.SIZE,0);
        this.statistics.put(STATISTICS.HITS,0);
        this.statistics.put(STATISTICS.MISSES,0);
    }

    public void addMetrics(String query,String date, CovidData CovidMetrics){
        /*adds new entry to cache*/
        if(!this.containsMetrics(query)){
            this.statistics.put(STATISTICS.SIZE, this.statistics.get(STATISTICS.SIZE)+1);
        }
        if(date == null){
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
            this.cache.put(Pair.of(query,currentYear+"-"+currentMonth+"-"+currentDay),Pair.of(LocalDateTime.now().plus(5,ChronoUnit.MINUTES ),CovidMetrics));
        }
        this.cache.put(Pair.of(query,date),Pair.of(LocalDateTime.now().plus(5,ChronoUnit.MINUTES ),CovidMetrics));
    }

    public boolean containsMetrics(String query){
        //CHECKS IF ENTRY EXISTS IN CACHE
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
        if (cache.containsKey(Pair.of(query,currentYear+"-"+currentMonth+"-"+currentDay))){
            if(LocalDateTime.now().compareTo(cache.get(Pair.of(query,currentYear+"-"+currentMonth+"-"+currentDay)).getKey()) <0){
                return true;
            }
            cache.remove(Pair.of(query,currentYear+"-"+currentMonth+"-"+currentDay));
            this.statistics.put(STATISTICS.SIZE, this.statistics.get(STATISTICS.SIZE)-1);
        }
        return false;
    }

    public boolean containsMetricsbyDate(String query, String Date){
        //CHECKS IF ENTRY EXISTS IN CACHE
        if (cache.containsKey(Pair.of(query,Date))){
            if(LocalDateTime.now().compareTo(cache.get(Pair.of(query,Date)).getKey()) <0){
                return true;
            }
            cache.remove(Pair.of(query,Date));
            this.statistics.put(STATISTICS.SIZE, this.statistics.get(STATISTICS.SIZE)-1);
        }
        return false;
    }

    public Optional<CovidData> getMetrics(String query){
        //GETS ENTRY FROM CACHE
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
        this.statistics.put(STATISTICS.REQUESTS, this.statistics.get(STATISTICS.REQUESTS)+1);
        if(this.containsMetrics(query)){
            this.statistics.put(STATISTICS.HITS, this.statistics.get(STATISTICS.HITS)+1);
            return Optional.of(cache.get(Pair.of(query,currentYear+"-"+currentMonth+"-"+currentDay)).getValue());
        }
        else{
            this.statistics.put(STATISTICS.MISSES, this.statistics.get(STATISTICS.MISSES)+1);  
            return Optional.empty();
        }
    }

    public Optional<CovidData> getMetricsbyDate(String query,String date){
        //GETS ENTRY FROM CACHE
        this.statistics.put(STATISTICS.REQUESTS, this.statistics.get(STATISTICS.REQUESTS)+1);
        if(this.containsMetricsbyDate(query,date)){
            this.statistics.put(STATISTICS.HITS, this.statistics.get(STATISTICS.HITS)+1);
            return Optional.of(cache.get(Pair.of(query,date)).getValue());
        }
        else{
            this.statistics.put(STATISTICS.MISSES, this.statistics.get(STATISTICS.MISSES)+1);  
            return Optional.empty();
        }
    }

    public int getSize(){
        return this.statistics.get(STATISTICS.SIZE);
    }

    public int getRequests(){
        return this.statistics.get(STATISTICS.REQUESTS);
    }

    public int getHits(){
        return this.statistics.get(STATISTICS.HITS);
    }

    public int getMisses(){
        return this.statistics.get(STATISTICS.MISSES);
    }


}
