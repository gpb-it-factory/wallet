package ru.gpbtech.wallet.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Пример интеграционного теста
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalletApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Sql("/test.sql")
    void testGetWalletBalanceSuccess() throws Exception {
        String jsonContent = "{\"clientId\":\"550e8400-e29b-41d4-a716-446655440000\",\"dateFrom\":\"2024-06-01T12:00:00Z\",\"dateTo\":\"2024-06-01T13:00:00Z\"}";
        
        mockMvc.perform(post("/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(100.00))
                .andExpect(jsonPath("$.currency").value("RUB"))
                .andExpect(jsonPath("$.lastUpdated").value("2024-06-01T13:00:00+03:00"));
    }
    
    @Test
    void testGetWalletBalanceError() throws Exception {
        String jsonContent = "{\"dateFrom\":\"2024-06-01T12:00:00Z\",\"dateTo\":\"2024-06-01T13:00:00Z\"}";
        
        mockMvc.perform(post("/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }
}
