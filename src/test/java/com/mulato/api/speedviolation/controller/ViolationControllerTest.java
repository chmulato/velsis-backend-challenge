package com.mulato.api.speedviolation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulato.api.speedviolation.model.enums.Origin;
import com.mulato.api.speedviolation.model.request.ViolationRequest;
import com.mulato.api.speedviolation.model.response.ViolationResponse;
import com.mulato.api.speedviolation.model.entity.Violation;
import com.mulato.api.speedviolation.model.enums.Severity;
import com.mulato.api.speedviolation.service.ViolationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViolationController.class)
class ViolationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ViolationService service;

    @Test
    void shouldReturn200AndResponseWhenValidRequest() throws Exception {

        ViolationRequest request = new ViolationRequest(
                "ABC1234",
                120,
                60,
                "EQ-01",
                Instant.now()
        );

        Violation violation = new Violation(
                Severity.SERIOUS,
                "218-II",
                Instant.now()
        );

        ViolationResponse response = new ViolationResponse(
                request.licensePlate(),
                request.equipmentId(),
                request.measuredSpeed(),
                112,
                request.speedLimit(),
                86.6,
                true,
                violation,
                Instant.now()
        );

        Mockito.when(service.evaluate(Origin.FIXED, request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/violations/evaluate")
                        .header("x-origin", "FIXED")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate").value("ABC1234"))
                .andExpect(jsonPath("$.hasViolation").value(true))
                .andExpect(jsonPath("$.violation.ctbCode").value("218-II"));
    }

    @Test
    void shouldReturn400WhenOriginHeaderIsMissing() throws Exception {

        ViolationRequest request = new ViolationRequest(
                "ABC1234",
                120,
                60,
                "EQ-01",
                Instant.now()
        );

        mockMvc.perform(post("/api/v1/violations/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenRequestBodyIsInvalid() throws Exception {

        String invalidJson = """
                {
                    "licensePlate": "",
                    "measuredSpeed": -10,
                    "speedLimit": 60,
                    "equipmentId": "",
                    "captureTimestamp": null
                }
                """;

        mockMvc.perform(post("/api/v1/violations/evaluate")
                        .header("x-origin", "FIXED")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenOriginIsInvalidEnum() throws Exception {

        ViolationRequest request = new ViolationRequest(
                "ABC1234",
                120,
                60,
                "EQ-01",
                Instant.now()
        );

        mockMvc.perform(post("/api/v1/violations/evaluate")
                        .header("x-origin", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
