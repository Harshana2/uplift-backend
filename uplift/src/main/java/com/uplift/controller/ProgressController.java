package com.uplift.controller;

import com.uplift.model.Progress;
import com.uplift.service.impl.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:5173")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    // Get progress by member ID
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Progress>> getProgressByMemberId(@PathVariable String memberId) {
        List<Progress> progressList = progressService.getProgressByMemberId(memberId);
        return ResponseEntity.ok(progressList);
    }

    // Create or update progress
    @PostMapping
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        Progress savedProgress = progressService.saveProgress(progress);
        return ResponseEntity.ok(savedProgress);
    }

    // Delete progress by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable String id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
