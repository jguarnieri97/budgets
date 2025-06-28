package ar.edu.unlam.tpi.budgets.controller.integration;

import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenApplicantId_whenGetBudgetsByApplicant_thenReturnsBudgets() throws Exception {
        Long applicantId = 1L;

        MvcResult result = mockMvc.perform(get("/budgets/v1/user/applicant/{applicantId}", applicantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<List<BudgetResponseDto>> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(
                        GenericResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, BudgetResponseDto.class)
                )
        );

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
    }

    @Test
    void givenSupplierId_whenGetBudgetsBySupplier_thenReturnsBudgets() throws Exception {
        Long supplierId = 1L;

        MvcResult result = mockMvc.perform(get("/budgets/v1/user/supplier/{supplierId}", supplierId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<List<BudgetSupplierResponseDto>> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(
                        GenericResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, BudgetSupplierResponseDto.class)
                )
        );

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
    }
}