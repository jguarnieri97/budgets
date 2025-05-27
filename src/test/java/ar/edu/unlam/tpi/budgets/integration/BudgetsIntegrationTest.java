package ar.edu.unlam.tpi.budgets.integration;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.SupplierDataRequest;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;

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
    //Crear proveedores manualmente
    SupplierDataRequest supplier1 = new SupplierDataRequest();
    supplier1.setSupplierId(10L);
    supplier1.setSupplierName("Proveedor A");

    SupplierDataRequest supplier2 = new SupplierDataRequest();
    supplier2.setSupplierId(11L);
    supplier2.setSupplierName("Proveedor B");

    //Crear request
    BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
            .applicantId(1L)
            .applicantName("Juan Pérez")
            .isUrgent(false)
            .estimatedDate(null)
            .workResume("Instalación eléctrica")
            .workDetail("Se requiere instalación completa en oficina")
            .files(List.of("base64img1", "base64img2"))
            .suppliers(List.of(supplier1, supplier2))
            .build();

    //Convertir a JSON
    String json = objectMapper.writeValueAsString(request);

    //Ejecutar el endpoint
    mockMvc.perform(post("/budgets/v1/budget")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").exists());
}


@Test
void testCreateBudget_invalidRequest() throws Exception {

      SupplierDataRequest supplier1 = new SupplierDataRequest();
      supplier1.setSupplierId(10L);
      supplier1.setSupplierName("Proveedor A");
  
      SupplierDataRequest supplier2 = new SupplierDataRequest();
      supplier2.setSupplierId(11L);
      supplier2.setSupplierName("Proveedor B");
    //Crear request inválido
    BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
            .applicantId(null)
            .applicantName(null)
            .isUrgent(false)
            .estimatedDate(null)
            .workResume("Instalación eléctrica")
            .workDetail("Se requiere instalación completa en oficina")
            .files(List.of("base64img1", "base64img2"))
            .suppliers(List.of(supplier1, supplier2))
            .build();

    //Convertir a JSON
    String json = objectMapper.writeValueAsString(request);

    //Ejecutar el endpoint
    mockMvc.perform(post("/budgets/v1/budget")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.message").value("INTERNAL_ERROR"))
            .andExpect(jsonPath("$.detail").exists());

    }


@Test
void testGetBudgetsByApplicantId_success() throws Exception {
    // Crear presupuesto para que haya datos
    SupplierDataRequest supplier1 = new SupplierDataRequest();
    supplier1.setSupplierId(10L);
    supplier1.setSupplierName("Proveedor A");

    BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
    .applicantId(80L)
    .applicantName("Tester")
    .isUrgent(false)
    .estimatedDate(null)
    .workResume("Trabajo test")
    .workDetail("Detalle test")
    .files(List.of("testimg"))
    .suppliers(List.of(supplier1))
    .build();

    String json = objectMapper.writeValueAsString(request);

    //primero se crea el presupuesto
    mockMvc.perform(post("/budgets/v1/budget")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

    //Consultar por applicantId
    mockMvc.perform(get("/budgets/v1/user/applicant/80"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(1));
}


@Test
void testGetBudgetsBySupplierId_success() throws Exception {

    SupplierDataRequest supplier1 = new SupplierDataRequest();
    supplier1.setSupplierId(123L);
    supplier1.setSupplierName("Proveedor A");

    BudgetCreationRequestDto request = BudgetCreationRequestDto.builder()
            .applicantId(500L)
            .applicantName("Solicitante X")
            .isUrgent(false)
            .estimatedDate(null)
            .workResume("Instalación de equipos")
            .workDetail("Se necesita instalación completa")
            .files(List.of("imagenB64"))
            .suppliers(List.of(supplier1))
            .build();

    String json = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/budgets/v1/budget")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isOk());

    mockMvc.perform(get("/budgets/v1/user/supplier/123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data.length()").value(1));
}

@Test
void testGetBudgetDetailById_success() throws Exception {
    //Crear proveedores
    SupplierDataRequest s1 = new SupplierDataRequest();
    s1.setSupplierId(1L);
    s1.setSupplierName("ElectraSol");

    SupplierDataRequest s2 = new SupplierDataRequest();
    s2.setSupplierId(2L);
    s2.setSupplierName("Voltix");        

    SupplierDataRequest s3 = new SupplierDataRequest();
    s3.setSupplierId(3L);
    s3.setSupplierName("Lumenek");

    //Crear request
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

    //Crear presupuesto
    String json = objectMapper.writeValueAsString(request);

    String id = mockMvc.perform(post("/budgets/v1/budget")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    //Extraer ID con Jackson
    String createdId = objectMapper.readTree(id).path("data").path("id").asText();

    //Consultar detalle por ID
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
