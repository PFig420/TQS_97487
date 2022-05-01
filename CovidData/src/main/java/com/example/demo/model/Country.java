package com.example.demo.model;

public class Country {
    private String iso;
    private String name;



    public Country(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public Country() {
    }

    public String getIso() {
        return this.iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "{" +
            " iso='" + getIso() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }

}