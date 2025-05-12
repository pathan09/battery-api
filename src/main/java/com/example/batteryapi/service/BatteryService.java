package com.example.batteryapi.service;

import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;

import java.util.List;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
public interface BatteryService {
    void saveBatteries(List<BatteryRequestDTO> batteryDTOs);

    BatteryStatsDTO getBatteriesByPostcodeRange(String minPostcode, String maxPostcode, Integer minCap, Integer maxCap);
}
