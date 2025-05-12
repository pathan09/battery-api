package com.example.batteryapi.controller;

import com.example.batteryapi.dto.*;
import com.example.batteryapi.service.BatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by AyakuthPathan on 09-May-25.
 */

@RestController
@RequestMapping("/api/batteries")
@RequiredArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    @PostMapping
    public ResponseEntity<Void> addBatteries(@RequestBody List<BatteryRequestDTO> batteries) {
        batteryService.saveBatteries(batteries);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<BatteryStatsDTO> getBatteries(
            @RequestParam String minPostcode,
            @RequestParam String maxPostcode,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity
    ) {
        BatteryStatsDTO result = batteryService.getBatteriesByPostcodeRange(minPostcode, maxPostcode, minCapacity, maxCapacity);
        return ResponseEntity.ok(result);
    }
}
