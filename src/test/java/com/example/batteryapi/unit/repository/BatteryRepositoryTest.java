package com.example.batteryapi.unit.repository;

import com.example.batteryapi.entity.Battery;
import com.example.batteryapi.repository.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
/**
 * Created by AyakuthPathan on 12-May-25.
 */
class BatteryRepositoryTest {

    @Mock
    private BatteryRepository batteryRepository; // Mock the repository

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void shouldReturnBatteriesWhenPostcodeRangeIsValid() {
        // Arrange
        Battery battery1 = new Battery(1L, "Battery1", "1000", 200);
        Battery battery2 = new Battery(2L, "Battery2", "2000", 250);
        Battery battery3 = new Battery(3L, "Battery3", "3000", 300);
        List<Battery> mockBatteries = List.of(battery1, battery2, battery3);

        when(batteryRepository.findByPostcodeBetween("1000", "3000")).thenReturn(mockBatteries);

        // Act
        List<Battery> result = batteryRepository.findByPostcodeBetween("1000", "3000");

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(b -> b.getPostcode().equals("1000")));
        assertTrue(result.stream().anyMatch(b -> b.getPostcode().equals("2000")));
        assertTrue(result.stream().anyMatch(b -> b.getPostcode().equals("3000")));
    }

    @Test
    void shouldReturnEmptyListWhenNoBatteriesMatchPostcodeRange() {
        // Arrange
        when(batteryRepository.findByPostcodeBetween("5000", "6000")).thenReturn(List.of());

        // Act
        List<Battery> result = batteryRepository.findByPostcodeBetween("5000", "6000");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
