package com.example.batteryapi.unit.controller;

import com.example.batteryapi.controller.BatteryController;
import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;
import com.example.batteryapi.service.BatteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Created by AyakuthPathan on 10-May-25.
 */

class BatteryControllerTest {

    @Mock
    private BatteryService batteryService;

    @InjectMocks
    private BatteryController batteryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBatteries_shouldCallServiceAndReturnOk() {
        // Given
        List<BatteryRequestDTO> requests = List.of(
                new BatteryRequestDTO("A", "1000", 60),
                new BatteryRequestDTO("B", "1001", 80)
        );

        // When
        ResponseEntity<Void> response = batteryController.addBatteries(requests);

        // Then
        verify(batteryService, times(1)).saveBatteries(requests);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void getBatteries_shouldReturnStatsResponse() {
        BatteryStatsDTO stats = new BatteryStatsDTO(
                List.of("Battery1", "Battery2"), 300.0, 150.0
        );

        when(batteryService.getBatteriesByPostcodeRange("1000", "2000", null, null))
                .thenReturn(stats);

        ResponseEntity<BatteryStatsDTO> response = batteryController.getBatteries("1000", "2000", null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(stats, response.getBody());
    }
}

