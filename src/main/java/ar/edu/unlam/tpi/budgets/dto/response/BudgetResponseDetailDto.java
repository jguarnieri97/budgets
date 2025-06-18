package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetResponseDetailDto {

    private Long id;
    private String budgetNumber;
    private Boolean isRead;
    private Long applicantId;
    private String applicantName;
    private String category;
    private String state;
    private String createdAt;
    private List<String> files;
    private BudgetDetailResponseDto detail;
    private List<BudgetDataResponseDto> budgets;

}
