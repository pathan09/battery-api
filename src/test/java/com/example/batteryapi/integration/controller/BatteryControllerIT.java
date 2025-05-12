package com.example.batteryapi.integration.controller;

import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;
import com.example.batteryapi.integration.BaseIntegrationTest;
import com.example.batteryapi.repository.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by AyakuthPathan on 12-May-25.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatteryControllerIT extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BatteryRepository batteryRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        batteryRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/api/batteries";
    }

    @Test
    void shouldSaveBatteriesAndReturnSuccess() {
        List<BatteryRequestDTO> batteries = List.of(
                new BatteryRequestDTO("Alpha", "2000", 100),
                new BatteryRequestDTO("Beta", "2500", 200)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<BatteryRequestDTO>> request = new HttpEntity<>(batteries, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldFetchBatteryStatsForPostcodeRange() {
        // Save batteries first
        List<BatteryRequestDTO> batteries = List.of(
                new BatteryRequestDTO("Delta", "3000", 150),
                new BatteryRequestDTO("Omega", "3200", 250)
        );
        restTemplate.postForEntity(baseUrl, new HttpEntity<>(batteries), Void.class);

        // Query the stats
        String url = baseUrl + "?minPostcode=2990&maxPostcode=3300";

        ResponseEntity<BatteryStatsDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        BatteryStatsDTO stats = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stats).isNotNull();
        assertThat(stats.getBatteryNames()).containsExactlyInAnyOrder("Delta", "Omega");
        assertThat(stats.getTotalWattCapacity()).isEqualTo(400.0);
        assertThat(stats.getAverageWattCapacity()).isEqualTo(200.0);
    }
}
