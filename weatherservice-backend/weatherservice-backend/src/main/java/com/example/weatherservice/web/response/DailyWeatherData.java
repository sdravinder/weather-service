package com.example.weatherservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyWeatherData {
    private String date;
    private double minTemp;
    private double maxTemp;
    private String weatherCondition;
    private String weatherDescription;
    private String advisory;
}
