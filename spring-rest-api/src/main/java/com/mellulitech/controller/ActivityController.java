package com.mellulitech.controller;

import com.mellulitech.dto.ActivityDTO;
import com.mellulitech.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "http://localhost:5173")
public class ActivityController {

    private final ActivityService activityService;

    ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivity() {
        List<ActivityDTO> activities = activityService.findAllActivity();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody @Valid ActivityDTO activityDTO) {
        ActivityDTO savedActivity = activityService.saveActivity(activityDTO);
        return new ResponseEntity<>(savedActivity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteActivityById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ActivityDTO>> searchActivity(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "text", required = false) String text) {

        if(category == null || text == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ActivityDTO> activities = activityService.searchActivityByCategory(category,text);
        return new ResponseEntity<>(activities,HttpStatus.OK);
    }
}