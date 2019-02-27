package com.example.worldcountriesautocomplete;

public class CountryItem {
    private String countryName;
    private int countryImage;

    public CountryItem(String countryName, int countryImage) {
        this.countryName = countryName;
        this.countryImage = countryImage;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryImage() {
        return countryImage;
    }
}
