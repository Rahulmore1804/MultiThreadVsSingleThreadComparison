package com.MultiThreadVsSingleThreadComparision.Controller;

import com.MultiThreadVsSingleThreadComparision.Services.DbCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/con")
public class Home {


    @Autowired
    private DbCallService  dbCallService;
    @PostMapping("/bulk-update-single")
    public ResponseEntity<String> bulkUpdateStatusSingle(@RequestParam String status) {
        try {
            dbCallService.updateDataSingleThread(status); // Call the single-threaded method
            return ResponseEntity.ok("Single-threaded bulk update triggered with status: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bulk update failed: " + e.getMessage());
        }
    }
    // Endpoint to trigger multithreaded update
    @PostMapping("/bulk-update")
    public ResponseEntity<String> bulkUpdateStatus(@RequestParam String status) {
        try {
            dbCallService.updateDataWithThreads(status);
            return ResponseEntity.ok("Bulk update triggered successfully with status: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bulk update failed: " + e.getMessage());
        }
    }
}
