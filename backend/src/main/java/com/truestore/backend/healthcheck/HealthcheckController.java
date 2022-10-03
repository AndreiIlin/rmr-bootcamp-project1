package com.truestore.backend.healthcheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/healthchecks")
public class HealthcheckController {
    @GetMapping("/app")
    public ResponseEntity<?> checkAppHealthy() {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
