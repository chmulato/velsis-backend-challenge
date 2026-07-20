package com.mulato.api.speedviolation.model.entity;

import com.mulato.api.speedviolation.model.enums.Severity;
import java.time.Instant;

public record Violation(

        Severity severity,     // Gravidade da infração (MEDIUM, SERIOUS, VERY_SERIOUS)
        String ctbCode,        // Código do CTB (218-I, 218-II, 218-III)
        Instant processedAt    // Momento em que a infração foi processada

) {}
