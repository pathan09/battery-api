package com.example.batteryapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryStatsDTO {
    private List<String> batteryNames;
    private double totalWattCapacity;
    private double averageWattCapacity;
}
