package com.mellulitech.repository;

import com.mellulitech.model.Activity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    //find by country
    List<Activity> findByCountry(String country);

    //find by city
    List<Activity> findByCity(String city);

    //find by ip
    List<Activity> findByIp(String ip);

    boolean existsByIpAndCountryAndCity(String ip,String country,String city);
}