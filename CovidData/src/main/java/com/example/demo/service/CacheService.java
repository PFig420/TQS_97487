package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheService {
    @Autowired
    private  Cache Cache;

    public int getCacheSize(){
        return Cache.getSize();
    }

    public int getCacheRequests(){
        return Cache.getRequests();
    }

    public int getCacheMisses(){
        return Cache.getMisses();
    }

    public int getCacheHits(){
        return Cache.getHits();
    }

    public Map<String,Integer> getAllStats(){
        Map<String,Integer> stats = new HashMap<String,Integer>();

        stats.put("SIZE", Cache.getSize());
        stats.put("HITS", Cache.getHits());
        stats.put("MISSES", Cache.getMisses());
        stats.put("REQUESTS", Cache.getRequests());
        return stats;
    } 
}
