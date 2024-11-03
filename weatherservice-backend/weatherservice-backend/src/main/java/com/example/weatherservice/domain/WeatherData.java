package com.example.weatherservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WeatherData {
    private long dt;
    private WeatherStat main;
    private List<WeatherCondition> weather;
    private CloudCondition clouds;
    private WindCondition wind;
    private long visibility;
    private double pop;
    private Map<String, Double> rain;
    private WeatherSys sys;
    private String dt_txt;
}


