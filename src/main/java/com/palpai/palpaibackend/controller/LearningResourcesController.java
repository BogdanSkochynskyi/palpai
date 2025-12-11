package com.palpai.palpaibackend.controller;

import com.palpai.palpaibackend.response.BookDetailsResponse;
import com.palpai.palpaibackend.response.LearningResourcesResponse;
import com.palpai.palpaibackend.service.LearningResourcesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:5173")
public class LearningResourcesController {

    private final LearningResourcesService learningResourcesService;

    public LearningResourcesController(LearningResourcesService learningResourcesService) {
        this.learningResourcesService = learningResourcesService;
    }

    @GetMapping
    public ResponseEntity<LearningResourcesResponse> getResources(@RequestParam String topic) {
        LearningResourcesResponse response = learningResourcesService.getResources(topic);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book/{workKey}")
    public ResponseEntity<BookDetailsResponse> getBookDetails(@PathVariable String workKey) {
        BookDetailsResponse details = learningResourcesService.getBookDetails(workKey);
        return ResponseEntity.ok(details);
    }

}

