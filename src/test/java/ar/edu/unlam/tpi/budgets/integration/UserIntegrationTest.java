package ar.edu.unlam.tpi.budgets.integration;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BudgetRepository budgetRepository;

    @AfterEach
    void cleanUp() {
        budgetRepository.deleteAll();
    }

    @Test
    void givenBudgetsExistForApplicant_whenGetBudgetsByApplicantId_thenReturns200AndBudgets() throws Exception {
        Long applicantId = 100L;
        String applicantName = "Test Applicant";
    
        BudgetCreationRequestDto request1 = BudgetCreationRequestDto.builder()
                .applicantId(applicantId)
                .applicantName(applicantName)
                .workResume("Trabajo 1")
                .workDetail("Detalle trabajo 1")
                .files(List.of("file1.pdf"))
                .suppliers(List.of(
                    BudgetDataHelper.supplier(1L, "Proveedor A"),
                    BudgetDataHelper.supplier(2L, "Proveedor B")
                ))
                .build();
    
        BudgetCreationRequestDto request2 = BudgetCreationRequestDto.builder()
                .applicantId(applicantId)
                .applicantName(applicantName)
                .workResume("Trabajo 2")
                .workDetail("Detalle trabajo 2")
                .files(List.of("file2.pdf"))
                .suppliers(List.of(
                    BudgetDataHelper.supplier(3L, "Proveedor C")
                ))
                .build();
    
        mockMvc.perform(post("/budgets/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk());
    
        mockMvc.perform(post("/budgets/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());
    
        mockMvc.perform(get("/budgets/v1/user/applicant/{applicantId}", applicantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].applicantId").value(applicantId))
                .andExpect(jsonPath("$.data[0].applicantName").value(applicantName))
                .andExpect(jsonPath("$.data[1].applicantId").value(applicantId))
                .andExpect(jsonPath("$.data[1].applicantName").value(applicantName));
    }
    
    @Test
    void givenNoBudgetsForApplicant_whenGetBudgetsByApplicantId_thenReturnsEmptyList() throws Exception {
        Long nonExistentApplicantId = 999L;
    
        mockMvc.perform(get("/budgets/v1/user/applicant/{applicantId}", nonExistentApplicantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    
    @Test
    void givenBudgetsExistForSupplier_whenGetBudgetsBySupplierId_thenReturns200AndBudgets() throws Exception {
        Long supplierId = 200L;
        String supplierName = "Test Supplier";
    
        BudgetCreationRequestDto request1 = BudgetCreationRequestDto.builder()
                .applicantId(1L)
                .applicantName("Applicant 1")
                .workResume("Trabajo 1")
                .workDetail("Detalle trabajo 1")
                .files(List.of("file1.pdf"))
                .suppliers(List.of(
                    BudgetDataHelper.supplier(supplierId, supplierName)
                ))
                .build();
    
        BudgetCreationRequestDto request2 = BudgetCreationRequestDto.builder()
                .applicantId(2L)
                .applicantName("Applicant 2")
                .workResume("Trabajo 2")
                .workDetail("Detalle trabajo 2")
                .files(List.of("file2.pdf"))
                .suppliers(List.of(
                    BudgetDataHelper.supplier(supplierId, supplierName),
                    BudgetDataHelper.supplier(3L, "Other Supplier")
                ))
                .build();
    
        mockMvc.perform(post("/budgets/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk());
    
        mockMvc.perform(post("/budgets/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());
    
        mockMvc.perform(get("/budgets/v1/user/supplier/{supplierId}", supplierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].applicantId").exists())
                .andExpect(jsonPath("$.data[0].applicantName").exists())
                .andExpect(jsonPath("$.data[1].applicantId").exists())
                .andExpect(jsonPath("$.data[1].applicantName").exists());
    }
    
    @Test
    void givenNoBudgetsForSupplier_whenGetBudgetsBySupplierId_thenReturnsEmptyList() throws Exception {
        Long nonExistentSupplierId = 999L;
    
        mockMvc.perform(get("/budgets/v1/user/supplier/{supplierId}", nonExistentSupplierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    
    @Test
    void givenInvalidApplicantId_whenGetBudgetsByApplicantId_thenReturns400() throws Exception {
        mockMvc.perform(get("/budgets/v1/user/applicant/invalid"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void givenInvalidSupplierId_whenGetBudgetsBySupplierId_thenReturns400() throws Exception {
        mockMvc.perform(get("/budgets/v1/user/supplier/invalid"))
                .andExpect(status().isBadRequest());
    }
    
}
