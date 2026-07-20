package com.mulato.api.speedviolation.controller;

import com.mulato.api.speedviolation.model.enums.Origin;
import com.mulato.api.speedviolation.model.request.ViolationRequest;
import com.mulato.api.speedviolation.model.response.ViolationResponse;
import com.mulato.api.speedviolation.service.ViolationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/violations")
public class ViolationController {

    private final ViolationService service;

    public ViolationController(ViolationService service) {
        this.service = service;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ViolationResponse> evaluate(
            @RequestHeader("x-origin") Origin origin,
            @Valid @RequestBody ViolationRequest request
    ) {
        return ResponseEntity.ok(service.evaluate(origin, request));
    }
}
