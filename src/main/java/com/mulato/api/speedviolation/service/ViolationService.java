package com.mulato.api.speedviolation.service;

import com.mulato.api.speedviolation.config.ToleranceProperties;
import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.model.enums.Origin;
import com.mulato.api.speedviolation.model.enums.Severity;
import com.mulato.api.speedviolation.model.request.ViolationRequest;
import com.mulato.api.speedviolation.model.response.ViolationResponse;
import com.mulato.api.speedviolation.repository.ViolationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ViolationService {

    private final ToleranceProperties tolerance;
    private final ViolationRepository repository;

    public ViolationService(ToleranceProperties tolerance, ViolationRepository repository) {
        this.tolerance = tolerance;
        this.repository = repository;
    }

    public ViolationResponse evaluate(Origin origin, ViolationRequest request) {

        int consideredSpeed = calculateConsideredSpeed(request.measuredSpeed());
        double excessPercentage = calculateExcessPercentage(consideredSpeed, request.speedLimit());
        boolean hasViolation = excessPercentage > 0;

        Violation violation = null;

        if (hasViolation) {
            Severity severity = classifySeverity(excessPercentage);
            String ctbCode = resolveCtbCode(severity);

            violation = new Violation(
                    severity,
                    ctbCode,
                    Instant.now()
            );

            repository.save(request.licensePlate(), violation);
        }

        return new ViolationResponse(
                request.licensePlate(),
                request.equipmentId(),
                request.measuredSpeed(),
                consideredSpeed,
                request.speedLimit(),
                excessPercentage,
                hasViolation,
                violation,
                Instant.now()
        );
    }

    private int calculateConsideredSpeed(int measuredSpeed) {
        if (measuredSpeed <= tolerance.getLimitForPercent()) {
            return measuredSpeed - tolerance.getFixed();
        }
        double discount = measuredSpeed * (tolerance.getPercent() / 100.0);
        return (int) Math.round(measuredSpeed - discount);
    }

    private double calculateExcessPercentage(int consideredSpeed, int speedLimit) {
        if (consideredSpeed <= speedLimit) return 0.0;
        double excess = consideredSpeed - speedLimit;
        return (excess / speedLimit) * 100.0;
    }

    private Severity classifySeverity(double excessPercentage) {
        if (excessPercentage <= 20) return Severity.MEDIUM;
        if (excessPercentage <= 50) return Severity.SERIOUS;
        return Severity.VERY_SERIOUS;
    }

    private String resolveCtbCode(Severity severity) {
        return switch (severity) {
            case MEDIUM -> "218-I";
            case SERIOUS -> "218-II";
            case VERY_SERIOUS -> "218-III";
        };
    }
}
