package com.mulato.api.speedviolation.model.response;

import com.mulato.api.speedviolation.model.entity.Violation;
import java.time.Instant;

public record ViolationResponse(

        String licensePlate,
        String equipmentId,
        Integer measuredSpeed,
        Integer consideredSpeed,
        Integer speedLimit,
        Double excessPercentage,
        boolean hasViolation,
        Violation violation,
        Instant processedAt

) {}
