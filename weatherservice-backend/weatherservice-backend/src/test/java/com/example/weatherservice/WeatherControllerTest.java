package com.example.weatherservice;

import com.example.weatherservice.controller.WeatherController;
import com.example.weatherservice.service.WeatherService;
import com.example.weatherservice.web.response.DailyWeatherData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;  // Mock the service layer

    @Test
    public void testGetWeatherForCity() throws Exception {
        // Arrange: Create mock response data
        DailyWeatherData day1 = new DailyWeatherData("2024-10-30", 15.0, 25.0, "Sunny", "Clear skies", "Wear sunscreen");
        DailyWeatherData day2 = new DailyWeatherData("2024-10-31", 16.0, 28.0, "Rainy", "Heavy rain", "Bring an umbrella");
        DailyWeatherData day3 = new DailyWeatherData("2024-11-01", 14.0, 22.0, "Cloudy", "Overcast", "Wear a jacket");

        List<DailyWeatherData> mockWeatherData = Arrays.asList(day1, day2, day3);

        // Mock the service to return mock data when called
        Mockito.when(weatherService.getWeatherByCity("London")).thenReturn(mockWeatherData);

        // Act & Assert: Perform a GET request and verify the response
        mockMvc.perform(get("/weather/London")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verify status code 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Verify response content type
                .andExpect(jsonPath("$[0].date").value("2024-10-30"))  // Check first day's date
                .andExpect(jsonPath("$[0].maxTemp").value(25.0))  // Check first day's high temperature
                .andExpect(jsonPath("$[0].minTemp").value(15.0))  // Check first day's low temperature
                .andExpect(jsonPath("$[0].weatherCondition").value("Sunny"))  // Check first day's weather description
                .andExpect(jsonPath("$[1].date").value("2024-10-31"))  // Check second day's date
                .andExpect(jsonPath("$[1].weatherCondition").value("Rainy"));  // Check second day's weather description
    }
}
