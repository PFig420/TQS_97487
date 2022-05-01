package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;

import com.example.demo.DemoApplication;
import com.example.demo.service.CacheService;
import com.example.demo.service.CovidDataService;

/**
 * AirMetricRestControllerTest_IT
 */

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT,classes =DemoApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CovidControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CovidDataService CovService;

    @Test 
    @Order(1)    
    public void whenGetCacheStats_thenstatus200() throws Exception{
        CovService.getMetricsByLocationAndDay("Italy", "2022-04-13");
        mvc.perform(get("/covidstats/Italy/2022-04-13").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
          
    }

    @Test 
    @Order(2)
    public void whenGetNow_thenReturnOneValue() throws Exception{
        mvc.perform(get("/covidstats/Italy").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}