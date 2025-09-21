package com.mellulitech.service.impl;

import com.mellulitech.dto.ActivityDTO;
import com.mellulitech.exception.AlreadyActivityExistsException;
import com.mellulitech.exception.ResourceNotFoundException;
import com.mellulitech.mapper.ActivityMapper;
import com.mellulitech.model.Activity;
import com.mellulitech.repository.ActivityRepository;
import com.mellulitech.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper){
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public List<ActivityDTO> findAllActivity() {
        return activityRepository.findAll().stream().map(activityMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ActivityDTO saveActivity(ActivityDTO activityDTO) {
        boolean alreadyExists = activityRepository.existsByIpAndCountryAndCity(activityDTO.getIp(), activityDTO.getCountry(), activityDTO.getCity());
        if (alreadyExists) {
            throw new AlreadyActivityExistsException("An activity with the same IP, country, and city already exists.");
        }
        Activity activity = activityMapper.toEntity(activityDTO);
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toDTO(savedActivity);
    }

    @Override
    public void deleteActivityById(Long id) {
        Optional<Activity> activityOpt = activityRepository.findById(id);
         if(activityOpt.isPresent()){
             activityRepository.delete(activityOpt.get());
         } else {
             throw new ResourceNotFoundException("Activity not found with provided ID: "+ id);
         }
    }

    @Override
    public List<ActivityDTO> searchActivityByCategory(String category, String text) {

        List<Activity> activities;
        switch (category) {
            case "country":
                activities = activityRepository.findByCountry(text);
                break;
            case "city":
                activities = activityRepository.findByCity(text);
                break;
            case "ip":
                activities = activityRepository.findByIp(text);
                break;
            default:
                return new ArrayList<>();
        }
        return activityMapper.toDTO(activities);
    }
}
