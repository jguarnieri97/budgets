package ar.edu.unlam.tpi.budgets.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class BudgetCreationRequestDto {

    @NotNull(message = "El ID del solicitante no puede ser nulo")
    private Long applicantId;
    @NotNull(message = "El nombre del solicitante no puede ser nulo")
    private String applicantName;
    private String workResume;
    private String workDetail;
    private String category;
    private List<String> files;
    private List<SupplierDataRequest> suppliers;

}
