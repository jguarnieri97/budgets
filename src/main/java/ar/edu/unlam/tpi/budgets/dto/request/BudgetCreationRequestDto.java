package ar.edu.unlam.tpi.budgets.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetCreationRequestDto {

    private Long applicantId;
    private String applicantName;
    private boolean isUrgent;
    private String estimatedDate;
    private String workResume;
    private String workDetail;
    private List<String> files;
    private List<SupplierDataRequest> suppliers;

}
