package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetRequestResponse {

    private String id;
    private Long applicantId;
    private String date;

}
