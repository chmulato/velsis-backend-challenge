package com.mulato.api.speedviolation.controller;

import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.service.ViolationQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/violations")
public class ViolationQueryController {

    private final ViolationQueryService service;

    public ViolationQueryController(ViolationQueryService service) {
        this.service = service;
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<List<Violation>> find(@PathVariable String licensePlate) {

        if (!licensePlate.matches("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(service.findByLicensePlate(licensePlate));
    }
}
