package com.mirea.makhankodv.recyclerviewapp;

public class Country {
    private String countryName;
    private String flagName;
    private int population;

    public Country(String countryName, String flagName, int population) {
        this.countryName = countryName;
        this.flagName = flagName;
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getFlagName() {
        return flagName;
    }

    @Override
    public String toString() {
        return this.countryName + " (Population: " + this.population + ")";
    }
} 