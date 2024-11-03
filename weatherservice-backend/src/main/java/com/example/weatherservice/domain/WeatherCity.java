package com.example.weatherservice.domain;

import lombok.Data;

@Data
public class WeatherCity {
    private long id;
    private String name;
    private Coordinates coord;
    private String country;
    private long population;
    private int timeZone;
    private long sunrise;
    private long sunset;
}

class Coordinates{
    private double lat;
    private double lon;
}
