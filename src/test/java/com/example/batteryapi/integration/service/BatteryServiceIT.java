package com.example.batteryapi.integration.service;

import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;
import com.example.batteryapi.integration.BaseIntegrationTest;
import com.example.batteryapi.repository.BatteryRepository;
import com.example.batteryapi.service.BatteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by AyakuthPathan on 12-May-25.
 */

@SpringBootTest
@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
public class BatteryServiceIT extends BaseIntegrationTest {

    @Autowired
    private BatteryService batteryService;

    @Autowired
    private BatteryRepository batteryRepository;

    @BeforeEach
    void setUp() {
        batteryRepository.deleteAll();
    }

    @Test
    void testSaveBatteriesAndGetStats() {
        List<BatteryRequestDTO> batteriesToSave = List.of(
                new BatteryRequestDTO("Alpha", "2000", 100),
                new BatteryRequestDTO("Bravo", "2200", 200),
                new BatteryRequestDTO("Charlie", "2500", 300),
                new BatteryRequestDTO("Delta", "3000", 150)
        );

        batteryService.saveBatteries(batteriesToSave);

        BatteryStatsDTO stats = batteryService.getBatteriesByPostcodeRange("2000", "2600", 100, 300);

        assertThat(stats).isNotNull();
        assertThat(stats.getBatteryNames()).containsExactly("Alpha", "Bravo", "Charlie");
        assertThat(stats.getTotalWattCapacity()).isEqualTo(600.0);
        assertThat(stats.getAverageWattCapacity()).isEqualTo(200.0);
    }

}

