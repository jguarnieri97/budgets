package ar.edu.unlam.tpi.budgets.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetCreationRequest {

    private Long applicantId;
    private boolean isUrgent;
    private String estimatedDate;
    private String workResume;
    private String workDetail;
    private List<String> files;
    private List<Integer> suppliers;

}
