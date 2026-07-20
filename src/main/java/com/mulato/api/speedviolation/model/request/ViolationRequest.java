package com.mulato.api.speedviolation.model.request;

import jakarta.validation.constraints.*;
import java.time.Instant;

public record ViolationRequest(

        @NotBlank(message = "licensePlate is required")
        @Pattern(
            regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$|^[A-Z]{3}[0-9]{4}$",
            message = "licensePlate must follow the Brazilian standard"
        )
        String licensePlate,

        @Positive(message = "measuredSpeed must be greater than zero")
        Integer measuredSpeed,

        @Positive(message = "speedLimit must be greater than zero")
        Integer speedLimit,

        @NotBlank(message = "equipmentId is required")
        String equipmentId,

        @NotNull(message = "captureTimestamp is required")
        Instant captureTimestamp

) {}