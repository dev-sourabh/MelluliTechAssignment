package com.mellulitech.service.impl;

import com.mellulitech.dto.ActivityDTO;
import com.mellulitech.exception.AlreadyActivityExistsException;
import com.mellulitech.exception.ResourceNotFoundException;
import com.mellulitech.mapper.ActivityMapper;
import com.mellulitech.model.Activity;
import com.mellulitech.repository.ActivityRepository;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class ActivityServiceImplTest {

    private ActivityRepository activityRepository;
    private ActivityMapper activityMapper;
    private ActivityServiceImpl activityService;

    @Before
    public void setup() {
        activityRepository = mock(ActivityRepository.class);
        activityMapper = mock(ActivityMapper.class);
        activityService = new ActivityServiceImpl(activityRepository, activityMapper);
    }

    @Test
    public void testFindAllActivity() {
        Activity activity1 = new Activity(1L, Timestamp.valueOf("2025-09-20 12:32:52"),"10.0.0.1", "India", "Ahmedabad");
        Activity activity2 = new Activity(2L, Timestamp.valueOf("2025-09-20 12:34:18"),"10.0.0.2", "India", "Pune");

        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity1, activity2));
        when(activityMapper.toDTO(activity1)).thenReturn(new ActivityDTO(1L, Timestamp.valueOf("2025-09-20 12:32:52"), "10.0.0.1", "India", "Ahmedabad"));
        when(activityMapper.toDTO(activity2)).thenReturn(new ActivityDTO(2L, Timestamp.valueOf("2025-09-20 12:34:18"), "10.0.0.2", "India", "Pune"));

        List<ActivityDTO> result = activityService.findAllActivity();

        assertEquals(2, result.size());
        assertEquals("Ahmedabad", result.get(0).getCity());
        assertEquals("Pune", result.get(1).getCity());
    }

    @Test(expected = AlreadyActivityExistsException.class)
    public void testSaveActivityAlreadyExists() {
        ActivityDTO dto = new ActivityDTO(null, Timestamp.valueOf("2025-09-20 12:38:21"), "10.0.0.1", "India", "Ahmedabad");
        when(activityRepository.existsByIpAndCountryAndCity(dto.getIp(), dto.getCountry(), dto.getCity())).thenReturn(true);
        activityService.saveActivity(dto);
    }

    @Test
    public void testSaveActivitySuccess() {
        ActivityDTO dto = new ActivityDTO(null, Timestamp.valueOf("2025-09-20 12:38:21"), "10.0.0.1", "India", "Ahmedabad");
        Activity entity = new Activity(null, Timestamp.valueOf("2025-09-20 12:38:21"),"10.0.0.1", "India", "Ahmedabad");
        Activity saved = new Activity(1L,Timestamp.valueOf("2025-09-20 12:38:21"), "10.0.0.1", "India", "Ahmedabad");
        ActivityDTO savedDto = new ActivityDTO(1L, Timestamp.valueOf("2025-09-20 12:38:21"), "10.0.0.1", "India", "Ahmedabad");

        when(activityRepository.existsByIpAndCountryAndCity(dto.getIp(), dto.getCountry(), dto.getCity())).thenReturn(false);
        when(activityMapper.toEntity(dto)).thenReturn(entity);
        when(activityRepository.save(entity)).thenReturn(saved);
        when(activityMapper.toDTO(saved)).thenReturn(savedDto);

        ActivityDTO result = activityService.saveActivity(dto);

        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("Ahmedabad", result.getCity());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteActivityNotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());
        activityService.deleteActivityById(1L);
    }

    @Test
    public void testDeleteActivitySuccess() {
        Activity activity = new Activity(1L,Timestamp.valueOf("2025-09-20 12:32:52"), "10.0.0.1", "India", "Ahmedabad");
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        doNothing().when(activityRepository).delete(activity);

        activityService.deleteActivityById(1L);

        verify(activityRepository, times(1)).delete(activity);
    }

    @Test
    public void testSearchActivityByCategory() {
        Activity activity = new Activity(1L, Timestamp.valueOf("2025-09-20 12:32:52"),"10.0.0.1", "India", "Ahmedabad");
        ActivityDTO dto = new ActivityDTO(1L, Timestamp.valueOf("2025-09-20 12:32:52"), "10.0.0.1", "India", "Ahmedabad");

        when(activityRepository.findByCity("Ahmedabad")).thenReturn(Arrays.asList(activity));
        when(activityMapper.toDTO(Arrays.asList(activity))).thenReturn(Arrays.asList(dto));

        List<ActivityDTO> result = activityService.searchActivityByCategory("city", "Ahmedabad");

        assertEquals(1, result.size());
        assertEquals("Ahmedabad", result.get(0).getCity());
    }
}

