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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetsIntegrationTest {

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
    void testCreateBudget_success() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
            1L, "Juan Pérez",
            List.of(
                BudgetDataHelper.supplier(10L, "Proveedor A"),
                BudgetDataHelper.supplier(11L, "Proveedor B")
            )
        );

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    void testCreateBudget_invalidRequest() throws Exception {
        String json = objectMapper.writeValueAsString(BudgetDataHelper.createInvalidRequest());

        mockMvc.perform(post("/budgets/v1/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    void testGetBudgetsByApplicantId_success() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
            80L, "Tester",
            List.of(BudgetDataHelper.supplier(10L, "Proveedor A"))
        );

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
    void testGetBudgetsBySupplierId_success() throws Exception {
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
            500L, "Solicitante X",
            List.of(BudgetDataHelper.supplier(123L, "Proveedor A"))
        );

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
    void testGetBudgetDetailById_success() throws Exception {
        var s1 = BudgetDataHelper.supplier(1L, "ElectraSol");
        var s2 = BudgetDataHelper.supplier(2L, "Voltix");
        var s3 = BudgetDataHelper.supplier(3L, "Lumenek");

        BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
                .applicantId(999L)
                .applicantName("Logibyte")
                .isUrgent(true)
                .estimatedDate("2024-01-10T03:00:00")
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
}

