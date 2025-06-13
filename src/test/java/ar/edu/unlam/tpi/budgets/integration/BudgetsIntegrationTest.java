package ar.edu.unlam.tpi.budgets.integration;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.budgets.utils.BudgetUpdatedDataRequestHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class BudgetsIntegrationTest {

    //@Autowired
    private MockMvc mockMvc;

    //@Autowired
    private ObjectMapper objectMapper;

    //@Autowired
    private BudgetRepository budgetRepository;

    @AfterEach
    void cleanUp() {
        budgetRepository.deleteAll();
    }

    @Test
    @Disabled
    void givenValidRequest_whenCreateBudget_thenReturns200AndBudgetId() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                1L, "Juan Pérez",
                List.of(
                        BudgetDataHelper.supplier(10L, "Proveedor A"),
                        BudgetDataHelper.supplier(11L, "Proveedor B")));

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    @Disabled
    void givenInvalidRequest_whenCreateBudget_thenReturns500WithErrorDetails() throws Exception {
        String json = objectMapper.writeValueAsString(BudgetDataHelper.createInvalidRequest());

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    @Disabled
    void givenBudgetsExistForApplicant_whenGetBudgetsByApplicantId_thenReturns200AndBudgets() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                80L, "Tester",
                List.of(BudgetDataHelper.supplier(10L, "Proveedor A")));

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/budgets/v1/user/applicant/80"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @Disabled
    void givenBudgetsExistForSupplier_whenGetBudgetsBySupplierId_thenReturns200AndBudgets() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                500L, "Solicitante X",
                List.of(BudgetDataHelper.supplier(123L, "Proveedor A")));

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/budgets/v1/user/supplier/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @Disabled
    void givenBudgetExists_whenGetBudgetDetailById_thenReturns200AndDetails() throws Exception {
        var s1 = BudgetDataHelper.supplier(1L, "ElectraSol");
        var s2 = BudgetDataHelper.supplier(2L, "Voltix");
        var s3 = BudgetDataHelper.supplier(3L, "Lumenek");

        BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
                .applicantId(999L)
                .applicantName("Logibyte")
                .workResume("Reparación de panel interior")
                .workDetail("Se reparar paneles interiores.")
                .files(List.of("file1.pdf", "file2.docx"))
                .suppliers(List.of(s1, s2, s3))
                .build();

        String response = mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String createdId = objectMapper.readTree(response).path("data").path("id").asText();

        mockMvc.perform(get("/budgets/v1/budget/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(createdId))
                .andExpect(jsonPath("$.data.applicantName").value("Logibyte"))
                .andExpect(jsonPath("$.data.files.length()").value(2))
                .andExpect(jsonPath("$.data.detail.workResume").value("Reparación de panel interior"))
                .andExpect(jsonPath("$.data.budgets.length()").value(3))
                .andExpect(jsonPath("$.data.budgets[0].supplierName").value("ElectraSol"));
    }

    @Test
    @Disabled
    void givenValidRequest_whenFinalizeBudget_thenReturns200() throws Exception {

        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                1L, "Juan Pérez",
                List.of(
                        BudgetDataHelper.supplier(10L, "Proveedor A"),
                        BudgetDataHelper.supplier(11L, "Proveedor B")));

        String response = mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String createdId = objectMapper.readTree(response).path("data").path("id").asText();

        BudgetFinalizeRequestDto updateRequest = BudgetFinalizeRequestDto.builder()
                .supplierHired(10L)
                .build();

        mockMvc.perform(put("/budgets/v1/budget/{id}", createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());


    }


    @Test
    @Disabled
    void givenNonExistentBudget_whenFinalizeBudget_thenReturns404() throws Exception {
        BudgetFinalizeRequestDto request = BudgetFinalizeRequestDto.builder()
                .supplierHired(999L)
                .build();

        mockMvc.perform(
                        put("/budgets/v1/budget/inventado-1234")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_EXCEPTION"))
                .andExpect(jsonPath("$.detail").value("Budget request not found"));
    }

    @Test
    @Disabled
    void givenNonExistentProvider_whenUpdateBudget_thenReturns400() throws Exception {
        var s1 = BudgetDataHelper.supplier(1L, "ElectraSol");

        BudgetCreationRequestDto createRequest = BudgetCreationRequestDto.builder()
                .applicantId(999L)
                .applicantName("Logibyte")
                .workResume("Reparación de panel interior")
                .workDetail("Se reparar paneles interiores.")
                .files(List.of("file1.pdf", "file2.docx"))
                .suppliers(List.of(s1))
                .build();

        String response = mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String createdId = objectMapper.readTree(response).path("data").path("id").asText();

        Long nonExistentProviderId = 999L;

        mockMvc.perform(put("/budgets/v1/budget/{id}/user/{providerId}", createdId, nonExistentProviderId))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled
    void givenNonExistentBudget_whenUpdateBudget_thenReturns404() throws Exception {
        BudgetUpdateDataRequestDto updateRequest = BudgetUpdatedDataRequestHelper.getBudgetUpdateDataRequestDto();
        String nonExistentBudgetId = "nonexistent123";
        Long providerId = 1L;

        mockMvc.perform(put("/budgets/v1/budget/{id}/user/{providerId}", nonExistentBudgetId, providerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    void givenValidId_whenFinalizeRequestOnly_thenReturns200() throws Exception {
        // Crear un presupuesto con estado inicial (ej: INITIATED)
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                1L, "Juan Pérez",
                List.of(BudgetDataHelper.supplier(10L, "Proveedor A")));

        String response = mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String createdId = objectMapper.readTree(response).path("data").path("id").asText();

        // Ejecutar el endpoint de finalize-request
        mockMvc.perform(put("/budgets/v1/budget/{id}/finalize-request", createdId))
                .andExpect(status().isOk());
    }


}


