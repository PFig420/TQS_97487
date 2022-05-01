package com.example.demo.service;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.demo.APIrequest;
import com.example.demo.model.Country;
import com.example.demo.model.CovidData;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CovidServiceTest {

    @Mock
    private com.example.demo.Cache Cache;

    @Mock
    private APIrequest extApi;

    @InjectMocks
    private CovidDataService CovService;

    private CovidData CovMetric;
    

    @BeforeEach
    public void setUp() {

        CovMetric = new CovidData("2022-04-02", 2,2,20,2,1,2,
        2,2, 2.0, new Country("ITA", "Italy"));
        
    }

    @AfterEach
    public void tearDown() {
        Cache = null;
        CovMetric = null;
    }

    @Test
    public void whenGetCurrentByLocationAirMetricsInCache_thenCacheShoulBeConsulted() throws IOException, MalformedURLException, InterruptedException{
        when(Cache.getMetricsbyDate("Italy","2022-04-02")).thenReturn(Optional.of(CovMetric));
        assertEquals(CovService.getMetricsByLocationAndDay("Italy","2022-04-02").get(),CovMetric, "AirMetric Service getting wrong cache Metric");
        verify(Cache, VerificationModeFactory.times(1)).getMetricsbyDate("Italy","2022-04-02");
    }

   @Test
    public void whenGetAirMetricsByLocationAndTimeInCache_thenCacheShoulBeConsulted() throws IOException, MalformedURLException, InterruptedException{
        when(Cache.getMetricsbyDate("Italy","2022-04-02")).thenReturn(Optional.of(CovMetric));
        assertEquals(CovService.getMetricsByLocationAndDay("Italy","2022-04-02").get(),CovMetric, "AirMetric Service getting wrong cache Metric");
        verify(Cache, VerificationModeFactory.times(1)).getMetricsbyDate("Italy","2022-04-02");
    }

    @Test
    public void whenGetAirMetricsByLocationAndTimeNotInCache_thenAPIShoulBeConsulted() throws IOException, MalformedURLException, InterruptedException{
        when(Cache.getMetricsbyDate("Italy","2022-04-02")).thenReturn(Optional.empty());
        when(extApi.getMetricsFromAPI("Italy","2022-04-02")).thenReturn(CovMetric);
        assertEquals(CovService.getMetricsByLocationAndDay("Italy","2022-04-02").get(),CovMetric, "AirMetric Service getting wrong api Metrics");
        verify(extApi, VerificationModeFactory.times(1)).getMetricsFromAPI("Italy","2022-04-02");
        verify(Cache, VerificationModeFactory.times(1)).addMetrics("Italy","2022-04-02", CovMetric);
    }
    
}
