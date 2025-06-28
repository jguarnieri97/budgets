package ar.edu.unlam.tpi.budgets.controller.integration;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDetailDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BudgetsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenBudgetId_whenGetBudgetDetailById_thenReturnsBudget() throws Exception {
        Long budgetId = 1L;

        MvcResult result = mockMvc.perform(get("/budgets/v1/budget/{id}", budgetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<BudgetResponseDetailDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, BudgetResponseDetailDto.class)
        );

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void givenValidRequest_whenCreateBudget_thenReturnsCreatedBudget() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest();

        MvcResult result = mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<BudgetCreationResponseDto> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, BudgetCreationResponseDto.class)
        );

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void givenValidRequest_whenUpdateBudget_thenReturnsSuccess() throws Exception {
        Long budgetId = 1L;
        Long providerId = 1L;
        BudgetUpdateDataRequestDto request = BudgetUpdatedDataRequestHelper.getBudgetUpdateDataRequestDto();

        MvcResult result = mockMvc.perform(put("/budgets/v1/budget/{id}/user/{providerId}", budgetId, providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<Void> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, Void.class)
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage());
    }

    @Test
    void givenValidRequest_whenFinalizeBudgetRequest_thenReturnsSuccess() throws Exception {
        Long budgetId = 1L;
        BudgetFinalizeRequestDto request = BudgetFinalizeDataHelper.getBudgetFinalizeRequestDto();

        MvcResult result = mockMvc.perform(put("/budgets/v1/budget/{budgetId}", budgetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<Void> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, Void.class)
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage());
    }

    @Test
    void givenValidRequest_whenFinalizeRequestOnly_thenReturnsSuccess() throws Exception {
        Long budgetId = 1L;

        MvcResult result = mockMvc.perform(put("/budgets/v1/budget/{id}/finalize-request", budgetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<Void> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, Void.class)
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage());
    }

    @Test
    void givenValidRequest_whenRejectBudgetRequest_thenReturnsSuccess() throws Exception {
        Long budgetId = 1L;
        Long providerId = 1L;

        MvcResult result = mockMvc.perform(put("/budgets/v1/budget/{id}/user/{providerId}/reject", budgetId, providerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GenericResponse<Void> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(GenericResponse.class, Void.class)
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage());
    }
}


