package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetRequestListResponse {

    private List<BudgetRequestResponse> budgets;

}
