package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetResponse {

    private String id;
    private Long applicantId;
    private String createdAt;
    private List<String> files;
    private BudgetDetailResponse detail;
    private List<BudgetDataResponse> budgets;

}
