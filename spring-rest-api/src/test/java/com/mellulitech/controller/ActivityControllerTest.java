package com.mellulitech.controller;

import com.mellulitech.dto.ActivityDTO;
import com.mellulitech.service.impl.ActivityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@WebAppConfiguration
public class ActivityControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private ActivityServiceImpl activityService;

    @InjectMocks
    private ActivityController activityController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @Test
    public void testGetAllActivity() throws Exception {
        when(activityService.findAllActivity()).thenReturn(Arrays.asList(new ActivityDTO(1L, Timestamp.valueOf("2025-09-20 12:32:52"), "10.11.12.12", "India", "Ahmedabad")));
        mockMvc.perform(get("/activity")).andExpect(status().isOk());
        verify(activityService, times(1)).findAllActivity();
    }

    @Test
    public void testCreateActivity() throws Exception {
        ActivityDTO dto = new ActivityDTO(null, Timestamp.valueOf("2025-09-20 12:38:21"), "192.168.0.1", "USA", "New York");
        when(activityService.saveActivity(dto)).thenReturn(new ActivityDTO(3L, dto.getTimestamp(), dto.getIp(), dto.getCountry(), dto.getCity()));
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
        verify(activityService, times(1)).saveActivity(any(ActivityDTO.class));
    }

    @Test
    public void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivityById(1L);
        mockMvc.perform(delete("/activity/1")).andExpect(status().isNoContent());
        verify(activityService, times(1)).deleteActivityById(1L);
    }

    @Test
    public void testSearchActivity() throws Exception {
        when(activityService.searchActivityByCategory("city", "Ahmedabad")).thenReturn(Arrays.asList(new ActivityDTO(1L, Timestamp.valueOf("2025-09-20 12:32:52"), "10.11.12.12", "India", "Ahmedabad")));
        mockMvc.perform(get("/activity/search").param("category", "city").param("text", "Ahmedabad")).andExpect(status().isOk());
        verify(activityService, times(1)).searchActivityByCategory("city", "Ahmedabad");
    }

    @Test
    public void testSearchActivityBadRequest() throws Exception {
        mockMvc.perform(get("/activity/search")).andExpect(status().isBadRequest());
    }
}