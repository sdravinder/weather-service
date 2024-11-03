package com.example.weatherservice.service;

import com.example.weatherservice.domain.WeatherData;
import com.example.weatherservice.domain.WeatherDataResponse;
import com.example.weatherservice.web.response.DailyWeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    @Autowired
    WeatherApiClient weatherApiClient;

    @Value("${useInstoreDb}")
    private boolean useInstoreDb;

    HashMap<String, List<DailyWeatherData>> inStoreDB = new HashMap<>();

    public List<DailyWeatherData> getWeatherByCity(String city) {
        if(!useInstoreDb) {
            WeatherDataResponse weatherDataResponse;
            try {
                weatherDataResponse = weatherApiClient.getWeatherData(city);
            }catch(Exception e){
                return inStoreDB.get(city);
            }
            List<DailyWeatherData> dailyWeatherDataList = new ArrayList<>();
            //TODO: group the response based on date.
            Map<String, List<WeatherData>> groupedByDate = weatherDataResponse.getList()
                    .stream()
                    .collect(Collectors.groupingBy(item -> item.getDt_txt().split(" ")[0]));
            //loop over all the object group the min and max temp per day

            for (String date : groupedByDate.keySet()) {
                double minTemp = groupedByDate.get(date).stream().mapToDouble(item -> item.getMain().getTemp_min()).min().orElse(0);
                double maxTemp = groupedByDate.get(date).stream().mapToDouble(item -> item.getMain().getTemp_max()).max().orElse(0);
                DailyWeatherData dailyWeatherData = new DailyWeatherData();
                dailyWeatherData.setDate(date);
                dailyWeatherData.setMinTemp(minTemp);
                dailyWeatherData.setMaxTemp(maxTemp);
                dailyWeatherData.setWeatherCondition(groupedByDate.get(date).get(0).getWeather().get(0).getMain());
                dailyWeatherData.setWeatherDescription(groupedByDate.get(date).get(0).getWeather().get(0).getDescription());
                dailyWeatherData.setAdvisory(generateAdvisory(groupedByDate.get(date), maxTemp));
                dailyWeatherDataList.add(dailyWeatherData);
            }
            inStoreDB.put(city, dailyWeatherDataList);
            return dailyWeatherDataList;
        }
        else{
            return inStoreDB.get(city);
        }
    }

    private String generateAdvisory(List<WeatherData> weatherDataList, double maxTemp){
        if(weatherDataList.stream().anyMatch(item -> item.getWeather().get(0).getMain().equalsIgnoreCase("Rain"))){
            return "Carry umbrella";
        }else if(maxTemp>40){
            return "Use sunscreen lotion";
        }else if(weatherDataList.stream().anyMatch(item -> item.getWind().getSpeed()>10)){
            return "It’s too windy, watch out!";
        } else if(weatherDataList.stream().anyMatch(item -> item.getWeather().get(0).getMain().equalsIgnoreCase("Thunderstorm"))){
            return "Don’t step out! A Storm is brewing!";
        }
        return "";
    }
}
