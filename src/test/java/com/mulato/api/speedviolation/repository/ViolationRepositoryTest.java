package com.mulato.api.speedviolation.repository;

import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.model.enums.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViolationRepositoryTest {

    private ViolationRepository repository;

    @BeforeEach
    void setup() {
        repository = new ViolationRepository();
    }

    @Test
    void shouldReturnEmptyListWhenNoViolationsExist() {
        List<Violation> result = repository.findByLicensePlate("ABC1234");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveViolationForLicensePlate() {
        Violation violation = new Violation(
                Severity.MEDIUM,
                "218-I",
                Instant.now()
        );

        repository.save("ABC1234", violation);

        List<Violation> result = repository.findByLicensePlate("ABC1234");

        assertEquals(1, result.size());
        assertEquals("218-I", result.get(0).ctbCode());
        assertEquals(Severity.MEDIUM, result.get(0).severity());
    }

    @Test
    void shouldSaveMultipleViolationsForSameLicensePlate() {
        Violation v1 = new Violation(Severity.MEDIUM, "218-I", Instant.now());
        Violation v2 = new Violation(Severity.SERIOUS, "218-II", Instant.now());

        repository.save("ABC1234", v1);
        repository.save("ABC1234", v2);

        List<Violation> result = repository.findByLicensePlate("ABC1234");

        assertEquals(2, result.size());
        assertEquals("218-I", result.get(0).ctbCode());
        assertEquals("218-II", result.get(1).ctbCode());
    }

    @Test
    void shouldIsolateViolationsByDifferentLicensePlates() {
        Violation v1 = new Violation(Severity.MEDIUM, "218-I", Instant.now());
        Violation v2 = new Violation(Severity.SERIOUS, "218-II", Instant.now());

        repository.save("ABC1234", v1);
        repository.save("XYZ9876", v2);

        List<Violation> abc = repository.findByLicensePlate("ABC1234");
        List<Violation> xyz = repository.findByLicensePlate("XYZ9876");

        assertEquals(1, abc.size());
        assertEquals(Severity.MEDIUM, abc.get(0).severity());

        assertEquals(1, xyz.size());
        assertEquals(Severity.SERIOUS, xyz.get(0).severity());
    }
}
