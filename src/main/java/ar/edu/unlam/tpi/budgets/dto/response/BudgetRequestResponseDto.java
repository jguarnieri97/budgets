package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetRequestResponseDto {

    private String id;
    private Long applicantId;
    private String date;

}
