package ar.edu.unlam.tpi.budgets.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDetail {

    private String workResume;
    private String workDetail;

}
