package com.example.weatherservice.service;

import com.example.weatherservice.domain.WeatherDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Slf4j
@Service
public class WeatherApiClient {

    @Value("${openweather.apiurl}")
    private String URL;

    @Value("${openweather.apikey}")
    private String KEY;

    public WeatherDataResponse getWeatherData(String city) {
        String url = URL + city + "&appid=" + KEY;
        log.info("URL formed: {}", url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherDataResponse> response = restTemplate.getForEntity(url , WeatherDataResponse.class);
        return response.getBody();
    }

}
