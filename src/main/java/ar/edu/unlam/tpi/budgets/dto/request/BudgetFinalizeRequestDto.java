package ar.edu.unlam.tpi.budgets.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetFinalizeRequestDto {

    @NotNull
    private Long supplierHired;
    
}