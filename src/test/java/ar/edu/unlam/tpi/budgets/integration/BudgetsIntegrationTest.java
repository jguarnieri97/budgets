package ar.edu.unlam.tpi.budgets.integration;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.SupplierDataRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    
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

      //Crear proveedores manualmente
      SupplierDataRequest supplier1 = new SupplierDataRequest();
      supplier1.setSupplierId(10L);
      supplier1.setSupplierName("Proveedor A");
  
      SupplierDataRequest supplier2 = new SupplierDataRequest();
      supplier2.setSupplierId(11L);
      supplier2.setSupplierName("Proveedor B");
    // Crear request inválido
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
}
