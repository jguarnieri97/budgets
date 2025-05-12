package ar.edu.unlam.tpi.budgets.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BudgetDetail {

    private boolean isUrgent;
    private LocalDateTime estimatedDate;
    private String workResume;
    private String workDetail;

}
