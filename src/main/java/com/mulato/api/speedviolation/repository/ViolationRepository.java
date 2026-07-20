package com.mulato.api.speedviolation.repository;

import com.mulato.api.speedviolation.model.entity.Violation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ViolationRepository {

    private final ConcurrentHashMap<String, List<Violation>> storage = new ConcurrentHashMap<>();

    public void save(String licensePlate, Violation violation) {
        storage.computeIfAbsent(licensePlate, lp -> new ArrayList<>()).add(violation);
    }

    public List<Violation> findByLicensePlate(String licensePlate) {
        return storage.getOrDefault(licensePlate, List.of());
    }
}
