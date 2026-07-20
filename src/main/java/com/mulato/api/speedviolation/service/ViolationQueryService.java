package com.mulato.api.speedviolation.service;

import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.repository.ViolationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationQueryService {

    private final ViolationRepository repository;

    public ViolationQueryService(ViolationRepository repository) {
        this.repository = repository;
    }

    public List<Violation> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate);
    }
}
