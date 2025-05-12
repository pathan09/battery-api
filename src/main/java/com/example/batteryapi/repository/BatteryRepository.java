package com.example.batteryapi.repository;

import com.example.batteryapi.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetween(String minPostcode, String maxPostcode);
}
