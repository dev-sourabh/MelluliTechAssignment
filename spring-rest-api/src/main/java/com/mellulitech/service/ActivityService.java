package com.mellulitech.service;

import com.mellulitech.dto.ActivityDTO;
import java.util.List;

public interface ActivityService {
    List<ActivityDTO> findAllActivity();

    ActivityDTO saveActivity(ActivityDTO activityDTO);

    void deleteActivityById(Long id);

    List<ActivityDTO> searchActivityByCategory(String category, String text);
}
