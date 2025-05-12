package com.example.batteryapi.unit.service;

import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;
import com.example.batteryapi.entity.Battery;
import com.example.batteryapi.repository.BatteryRepository;
import com.example.batteryapi.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by AyakuthPathan on 10-May-25.
 */

class BatteryServiceImplTest {

    @Mock
    private BatteryRepository batteryRepository;

    @InjectMocks
    private BatteryServiceImpl batteryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBatteries_shouldSaveBatteriesToRepository() {
        // Given
        List<BatteryRequestDTO> requests = List.of(
                new BatteryRequestDTO("BatteryA", "1000", 50),
                new BatteryRequestDTO("BatteryB", "1001", 75)
        );

        // When
        batteryService.saveBatteries(requests);

        // Then
        ArgumentCaptor<List<Battery>> captor = ArgumentCaptor.forClass(List.class);
        verify(batteryRepository, times(1)).saveAll(captor.capture());

        List<Battery> captured = captor.getValue();
        assert captured.size() == 2;
        assert captured.get(0).getName().equals("BatteryA");
        assert captured.get(1).getWattCapacity() == 75;
    }

    @Test
    void getBatteriesByPostcodeRangeAndCapacity_shouldFilterAndReturnStats() {
        List<Battery> dbBatteries = List.of(
                new Battery(1L, "X", "2000", 100),
                new Battery(2L, "Y", "2005", 200)
        );

        when(batteryRepository.findByPostcodeBetween("2000", "2010")).thenReturn(dbBatteries);

        BatteryStatsDTO result = batteryService.getBatteriesByPostcodeRange("2000", "2010", 150, null);

        assertEquals(List.of("Y"), result.getBatteryNames());
        assertEquals(200.0, result.getTotalWattCapacity());
        assertEquals(200.0, result.getAverageWattCapacity());
    }
}

