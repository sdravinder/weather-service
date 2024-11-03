package com.example.weatherservice.controller;

import com.example.weatherservice.service.WeatherService;
import com.example.weatherservice.web.response.DailyWeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @CrossOrigin
    @GetMapping("/{city}")
    public List<DailyWeatherData> getWeatherForCity(@PathVariable String city){
       return weatherService.getWeatherByCity(city);
    }
}
