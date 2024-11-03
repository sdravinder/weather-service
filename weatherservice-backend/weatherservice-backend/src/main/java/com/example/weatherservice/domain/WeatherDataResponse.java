package com.example.weatherservice.domain;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDataResponse {
    String cod;
    int message;
    int cnt;
    List<WeatherData> list;
    private WeatherCity city;

}
