package com.mellulitech.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    private Long id;

    private Timestamp timestamp;

    @NotBlank(message = "IpAddress is required")
    private String ip;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;

}
