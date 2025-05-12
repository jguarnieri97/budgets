package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDataResponse {

    private Long supplierId;
    private Double price;
    private Integer daysCount;
    private Integer workerCount;
    private String detail;

}
