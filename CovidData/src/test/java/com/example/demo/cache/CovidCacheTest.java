package com.example.demo.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Cache;
import com.example.demo.model.Country;
import com.example.demo.model.CovidData;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CovidCacheTest {
    
    private Cache Cache;
    private CovidData CovMetric;
    private CovidData CovMetric2;

    @BeforeEach
    public void setUp() {

        //Create cache
        Cache = new Cache();

        //populate cache
        CovMetric = new CovidData("2022-04-02", 2,2,20,2,1,2,
        2,2, 2.0, new Country("ITA", "Italy"));
        CovMetric2 = new CovidData("2022-04-02", 2,2,20,2,1,2,
        2,2, 2.0, new Country("US", "United States"));
    }

    @AfterEach
    public void tearDown() {
        Cache = null;
        CovMetric= null;
    }

    @Test
    public void testAddMetrics(){
        Cache.addMetrics("Italy", "2022-04-02", CovMetric);
        assertTrue( Cache.containsMetricsbyDate("Italy","2022-04-02"), "addMetrics: added metrics not found in cache." );
        assertTrue(Cache.getSize()==1, "addMetrics: added metrics size not 1" );
    }

    @Test
    public void testTTL() throws InterruptedException{

        assertTrue( !Cache.containsMetrics("Italy"), "ContainsMetrics: metric should not exist" );
        Cache.addMetrics("Italy", "2022-04-02", CovMetric);
        assertTrue(Cache.containsMetricsbyDate("Italy","2022-04-02"), "ContainsMetrics: metric should  exist");
        //Thread.sleep(310*1000);
        //assertTrue(!airMetricCache.containsMetrics("Porto_22_99"), "ContainsMetrics: metric should  not exist");
    }

    @Test
    public void testGetMetricValid(){
        Cache.addMetrics("Italy","2022-04-02", CovMetric);
        assertEquals(Cache.getMetricsbyDate("Italy","2022-04-02").get(), CovMetric ,"getMetricsValid: get Metrics value wrong" );
    }

    @Test
    public void testGetMetricInexistent(){
        assertEquals(Optional.empty(), Cache.getMetrics("Italy"), "getMetricInexistent: get Metrics did not return empty");
    }

    
    @Test
    public void testStatRequest(){
        Cache.addMetrics("Italy", "2022-04-02", CovMetric);

        Cache.getMetrics("Italy");
        Cache.getMetrics("Italy");
        Cache.getMetrics("Italy");

        assertEquals(Cache.getRequests(),3, "StatRequest wrong number of requests");
    }

    @Test
    public void testStatSize(){
        Cache.addMetrics("Italy", "2022-04-02",CovMetric);
        Cache.addMetrics("Italy", "2022-04-03",CovMetric);
        Cache.addMetrics("US", "2022-04-02",CovMetric2);

        assertEquals(Cache.getSize(),3, "StatSize wrong size of cache");
    }

    @Test
    public void testStatMisses(){
        Cache.addMetrics("Italy", "2022-04-02",CovMetric);
        Cache.getMetricsbyDate("Italy", "2022-04-02");
        Cache.getMetrics("US");
        Cache.getMetricsbyDate("US", "2022-04-02");

        assertEquals(Cache.getMisses(),2, "StatSize wrong misses number");
    }

    @Test
    public void testStatHits(){
        Cache.addMetrics("Italy", "2022-04-02",CovMetric);
        Cache.getMetricsbyDate("Italy", "2022-04-02");
        Cache.getMetricsbyDate("US","2022-04-02");
        Cache.getMetrics("US");
        Cache.getMetrics("US");

        assertEquals(Cache.getHits(),1, "StatSize wrong hits number");
    }
}
