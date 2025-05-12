package com.example.batteryapi.service.impl;

import com.example.batteryapi.dto.BatteryRequestDTO;
import com.example.batteryapi.dto.BatteryStatsDTO;
import com.example.batteryapi.entity.Battery;
import com.example.batteryapi.repository.BatteryRepository;
import com.example.batteryapi.service.BatteryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository batteryRepository;

    @Override
    public void saveBatteries(List<BatteryRequestDTO> batteryDTOs) {
        log.info("Received {} batteries to save", batteryDTOs.size());
        List<Battery> batteries = batteryDTOs.stream()
                .map(dto -> Battery.builder()
                        .name(dto.getName())
                        .postcode(dto.getPostcode())
                        .wattCapacity(dto.getCapacity())
                        .build())
                .collect(Collectors.toList());

        List<Battery> saved = batteryRepository.saveAll(batteries);
        log.info("Successfully saved {} batteries", saved.size());
    }

    @Override
    public BatteryStatsDTO getBatteriesByPostcodeRange(String minPostcode, String maxPostcode, Integer minCap, Integer maxCap) {
        Integer min = Integer.parseInt(minPostcode);
        Integer max = Integer.parseInt(maxPostcode);
        log.info("Querying batteries in postcode range {}–{} with capacity range [{}–{}]",
                min, max, minCap, maxCap);
        if (min > max) {
            log.warn("Invalid postcode range: from {} > to {}", min, max);
            throw new IllegalArgumentException("Invalid postcode range.");
        }

        List<Battery> batteries = batteryRepository.findByPostcodeBetween(minPostcode, maxPostcode);
        log.debug("Found {} batteries in postcode range", batteries.size());

        List<Battery> filtered = batteries.stream()
                .filter(b -> minCap == null || b.getWattCapacity() >= minCap)
                .filter(b -> maxCap == null || b.getWattCapacity() <= maxCap)
                .toList();

        List<String> names = filtered.stream()
                .map(Battery::getName)
                .sorted()
                .toList();

        double total = filtered.stream()
                .filter(b-> b.getWattCapacity() !=null)
                .mapToDouble(Battery::getWattCapacity)
                .sum();

        double average = filtered.isEmpty() ? 0 : total / filtered.size();
        average = Math.round(average * 10000.0) / 10000.0; // 4 decimal places

        return new BatteryStatsDTO(names, total, average);
    }
}