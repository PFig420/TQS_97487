package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.Cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CacheServiceTest {
    @Mock(lenient=true)
    private Cache Cache;

    @InjectMocks
    private CacheService cacheStatisticsService;

    @BeforeEach
    public void setUp() {
        when(Cache.getSize()).thenReturn(5);
        when(Cache.getRequests()).thenReturn(3);
        when(Cache.getHits()).thenReturn(3);
        when(Cache.getMisses()).thenReturn(4);
    }

    @Test
    public void whenGetSize_thenSizeShouldBeReturned(){
        assertEquals(cacheStatisticsService.getCacheSize(),5, "Cache Service getting wrong cache size");
        verify(Cache, VerificationModeFactory.times(1)).getSize();
    }

    @Test
    public void whenGetRequests_thenRequestsShouldBeReturned(){
        assertEquals(cacheStatisticsService.getCacheRequests(),3, "Cache Service getting wrong cache request number");
        verify(Cache, VerificationModeFactory.times(1)).getRequests();
    }

    @Test
    public void whenGetHits_thenHitsShouldBeReturned(){
        assertEquals(cacheStatisticsService.getCacheHits(),3, "Cache Service getting wrong cache hit number");
        verify(Cache, VerificationModeFactory.times(1)).getHits();
    }

    @Test
    public void whenGetMisses_thenMissesShouldBeReturned(){
        assertEquals(cacheStatisticsService.getCacheMisses(),4, "Cache Service getting wrong cache miss number");
        verify(Cache, VerificationModeFactory.times(1)).getMisses();
    }

    @Test
    public void whenGetAllStats_thenAllStatsShouldBeReturned(){
        Map<String,Integer> stats = new HashMap<String,Integer>();

        stats.put("SIZE", 5);
        stats.put("HITS", 3);
        stats.put("MISSES", 4);
        stats.put("REQUESTS", 3);

        assertEquals(cacheStatisticsService.getAllStats(),stats, "Cache Service getting wrong overall stats");
    }
}
