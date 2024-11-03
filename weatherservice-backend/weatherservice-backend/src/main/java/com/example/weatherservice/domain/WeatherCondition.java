package com.example.weatherservice.domain;

import lombok.Data;

@Data
public class WeatherCondition {
    private int id;
    private String main;
    private String description;
    private String icon;
}
