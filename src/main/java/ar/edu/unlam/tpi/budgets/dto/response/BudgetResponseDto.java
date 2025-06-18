package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetResponseDto {

    private Long id;
    private String budgetNumber;
    private Long applicantId;
    private String applicantName;
    private String category;
    private String state;
    private String date;

}
