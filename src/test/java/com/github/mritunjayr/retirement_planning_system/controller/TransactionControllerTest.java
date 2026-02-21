package com.github.mritunjayr.retirement_planning_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test type: Integration Test
 * Validation: Tests all REST API endpoints for retirement planning system
 * Command: mvn test
 */
@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testParseTransactions() throws Exception {
        String request = "[{\"date\":\"2023-10-12 20:15:30\",\"amount\":250}]";

        mockMvc.perform(post("/challenge/v1/transactions:parse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ceiling").value(300.0))
                .andExpect(jsonPath("$[0].remanent").value(50.0));
    }

    @Test
    void testValidateTransactions() throws Exception {
        String request = "{\"wage\":50000,\"transactions\":[" +
                "{\"date\":\"2023-01-15 10:30:00\",\"amount\":2000,\"ceiling\":2100,\"remanent\":100}," +
                "{\"date\":\"2023-01-15 10:30:00\",\"amount\":3000,\"ceiling\":3000,\"remanent\":0}" +
                "]}";

        mockMvc.perform(post("/challenge/v1/transactions:validator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").isArray())
                .andExpect(jsonPath("$.invalid").isArray());
    }

    @Test
    void testFilterTransactions() throws Exception {
        String request = "{\"q\":[{\"fixed\":0,\"start\":\"2023-07-01 00:00:00\",\"end\":\"2023-07-31 23:59:59\"}]," +
                "\"p\":[{\"extra\":25,\"start\":\"2023-10-01 08:00:00\",\"end\":\"2023-12-31 19:59:59\"}]," +
                "\"k\":[{\"start\":\"2023-03-01 00:00:00\",\"end\":\"2023-11-30 23:59:59\"}]," +
                "\"transactions\":[{\"date\":\"2023-03-15 10:30:00\",\"amount\":375}]}";

        mockMvc.perform(post("/challenge/v1/transactions:filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").isArray())
                .andExpect(jsonPath("$.invalid").isArray());
    }

    @Test
    void testCalculateNPSReturns() throws Exception {
        String request = "{\"age\":29,\"wage\":50000,\"inflation\":5.5," +
                "\"q\":[{\"fixed\":0,\"start\":\"2023-07-01 00:00:00\",\"end\":\"2023-07-31 23:59:59\"}]," +
                "\"p\":[{\"extra\":25,\"start\":\"2023-10-01 08:00:00\",\"end\":\"2023-12-31 19:59:59\"}]," +
                "\"k\":[{\"start\":\"2023-01-01 00:00:00\",\"end\":\"2023-12-31 23:59:59\"}]," +
                "\"transactions\":[{\"date\":\"2023-10-12 20:15:00\",\"amount\":250}]}";

        mockMvc.perform(post("/challenge/v1/returns:nps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTransactionAmount").exists())
                .andExpect(jsonPath("$.totalCeiling").exists())
                .andExpect(jsonPath("$.savingsByDates").isArray());
    }

    @Test
    void testCalculateIndexReturns() throws Exception {
        String request = "{\"age\":29,\"wage\":50000,\"inflation\":5.5," +
                "\"q\":[{\"fixed\":0,\"start\":\"2023-07-01 00:00:00\",\"end\":\"2023-07-31 23:59:59\"}]," +
                "\"p\":[{\"extra\":25,\"start\":\"2023-10-01 08:00:00\",\"end\":\"2023-12-31 19:59:59\"}]," +
                "\"k\":[{\"start\":\"2023-01-01 00:00:00\",\"end\":\"2023-12-31 23:59:59\"}]," +
                "\"transactions\":[{\"date\":\"2023-10-12 20:15:00\",\"amount\":250}]}";

        mockMvc.perform(post("/challenge/v1/returns:index")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTransactionAmount").exists())
                .andExpect(jsonPath("$.totalCeiling").exists())
                .andExpect(jsonPath("$.savingsByDates").isArray());
    }

    @Test
    void testGetPerformance() throws Exception {
        mockMvc.perform(get("/challenge/v1/performance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.memory").exists())
                .andExpect(jsonPath("$.threads").exists());
    }

    @Test
    void testFilterTransactionsWithDuplicates() throws Exception {
        String request = "{\"q\":[],\"p\":[],\"k\":[{\"start\":\"2023-01-01 00:00:00\",\"end\":\"2023-12-31 23:59:59\"}]," +
                "\"transactions\":[" +
                "{\"date\":\"2023-03-15 10:30:00\",\"amount\":375}," +
                "{\"date\":\"2023-03-15 10:30:00\",\"amount\":500}" +
                "]}";

        mockMvc.perform(post("/challenge/v1/transactions:filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invalid").isArray());
    }
}
