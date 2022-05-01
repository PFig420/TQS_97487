package com.example.demo.model;

public class CovidData {
    
    private String date;
    private int ConfCases;
    private int deaths;
    private int recovered;
    private int confirmed_diff;
    private int deaths_diff;
    private int recovered_diff;
  
    private int active;
    private int active_diff;

    private double fatality_rate;
    private Country country;

    public CovidData(String date, int ConfCases, int deaths, int recovered, int confirmed_diff, int deaths_diff, int recovered_diff, int active, int active_diff, double fatality_rate, Country country) {
        this.date = date;
        this.ConfCases = ConfCases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.confirmed_diff = confirmed_diff;
        this.deaths_diff = deaths_diff;
        this.recovered_diff = recovered_diff;
        this.active = active;
        this.active_diff = active_diff;
        this.fatality_rate = fatality_rate;
        this.country = country;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Country getCountry() {
        return this.country;
    }
    @Override
    public String toString() {
        return "{" +
            " date='" + getDate() + "'" +
            ", ConfCases='" + getConfCases() + "'" +
            ", deaths='" + getDeaths() + "'" +
            ", recovered='" + getRecovered() + "'" +
            ", confirmed_diff='" + getConfirmed_diff() + "'" +
            ", deaths_diff='" + getDeaths_diff() + "'" +
            ", recovered_diff='" + getRecovered_diff() + "'" +
            ", active='" + getActive() + "'" +
            ", active_diff='" + getActive_diff() + "'" +
            ", fatality_rate='" + getFatality_rate() + 
            "}";
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    public CovidData(){

    }

    public int getConfCases() {
        return this.ConfCases;
    }

    public void setConfCases(int ConfCases) {
        this.ConfCases = ConfCases;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return this.recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getConfirmed_diff() {
        return this.confirmed_diff;
    }

    public void setConfirmed_diff(int confirmed_diff) {
        this.confirmed_diff = confirmed_diff;
    }

    public int getDeaths_diff() {
        return this.deaths_diff;
    }

    public void setDeaths_diff(int deaths_diff) {
        this.deaths_diff = deaths_diff;
    }

    public int getRecovered_diff() {
        return this.recovered_diff;
    }

    public void setRecovered_diff(int recovered_diff) {
        this.recovered_diff = recovered_diff;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive_diff() {
        return this.active_diff;
    }

    public void setActive_diff(int active_diff) {
        this.active_diff = active_diff;
    }

    public double getFatality_rate() {
        return this.fatality_rate;
    }

    public void setFatality_rate(double fatality_rate) {
        this.fatality_rate = fatality_rate;
    }

    
}
