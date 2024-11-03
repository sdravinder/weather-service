package com.example.weatherservice.domain;

import lombok.Data;

@Data
public class WindCondition {
    private double speed;
    private double deg;
    private double gust;
}
