package com.example.batteryapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by AyakuthPathan on 09-May-25.
 */
@Entity
@Table(name = "batteries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "battery_seq")
    @SequenceGenerator(name = "battery_seq", sequenceName = "battery_seq", allocationSize = 1000)
    private Long id;

    private String name;
    private String postcode;
    private Integer wattCapacity;
}
