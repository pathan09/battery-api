package com.example.batteryapi.dto;

import lombok.*;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryRequestDTO {
    private String name;
    private String postcode;
    private Integer capacity;
}