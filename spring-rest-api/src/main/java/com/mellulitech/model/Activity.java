package com.mellulitech.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "activities")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("timestamp")
    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;

    @JsonProperty("ip")
    @Column(name = "ip")
    private String ip;

    @JsonProperty("country")
    @Column(name = "country")
    private String country;

    @JsonProperty("city")
    @Column(name = "city")
    private String city;
}