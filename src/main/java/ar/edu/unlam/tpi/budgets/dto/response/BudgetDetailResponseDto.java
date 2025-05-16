package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDetailResponseDto {

    private Boolean isUrgent;
    private String estimatedDate;
    private String workResume;
    private String workDetail;

}
