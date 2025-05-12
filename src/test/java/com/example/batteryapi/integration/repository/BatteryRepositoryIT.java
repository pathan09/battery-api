package com.example.batteryapi.integration.repository;

import com.example.batteryapi.entity.Battery;
import com.example.batteryapi.integration.BaseIntegrationTest;
import com.example.batteryapi.repository.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by AyakuthPathan on 11-May-25.
 */

@SpringBootTest
@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
public class BatteryRepositoryIT extends BaseIntegrationTest {

    @Autowired
    private BatteryRepository batteryRepository;

    @BeforeEach
    void setup() {
        batteryRepository.deleteAll();

        batteryRepository.saveAll(List.of(
                Battery.builder().name("Alpha").postcode("2000").wattCapacity(100).build(),
                Battery.builder().name("Beta").postcode("2500").wattCapacity(200).build(),
                Battery.builder().name("Gamma").postcode("3000").wattCapacity(300).build()
        ));
    }

    @Test
    void shouldFindBatteriesBetweenPostcodeRange() {
        List<Battery> result = batteryRepository.findByPostcodeBetween("1999", "3001");

        assertThat(result).hasSize(3);
        assertThat(result).extracting(Battery::getName)
                .containsExactlyInAnyOrder("Alpha", "Beta", "Gamma");
    }

    @Test
    void shouldReturnEmptyIfNoMatch() {
        List<Battery> result = batteryRepository.findByPostcodeBetween("100", "1999");

        assertThat(result).isEmpty();
    }
}
