package com.qp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	
	@GetMapping("/")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok("qp-assessment service 1.0");
    }
}
