package com.mulato.api.speedviolation.controller;

import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.model.enums.Severity;
import com.mulato.api.speedviolation.service.ViolationQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViolationQueryController.class)
class ViolationQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViolationQueryService queryService;

    @Test
    void shouldReturnEmptyListWhenNoViolationsExist() throws Exception {
        Mockito.when(queryService.findByLicensePlate("ABC1234"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/violations/ABC1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnListOfViolations() throws Exception {
        Violation v1 = new Violation(Severity.MEDIUM, "218-I", Instant.now());
        Violation v2 = new Violation(Severity.SERIOUS, "218-II", Instant.now());

        Mockito.when(queryService.findByLicensePlate("ABC1234"))
                .thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/api/v1/violations/ABC1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].severity").value("MEDIUM"))
                .andExpect(jsonPath("$[0].ctbCode").value("218-I"))
                .andExpect(jsonPath("$[1].severity").value("SERIOUS"))
                .andExpect(jsonPath("$[1].ctbCode").value("218-II"));
    }

    @Test
    void shouldReturn400WhenLicensePlateIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/violations/INVALID-PLATE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
