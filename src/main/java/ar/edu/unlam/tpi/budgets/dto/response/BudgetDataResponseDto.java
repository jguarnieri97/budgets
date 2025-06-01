package ar.edu.unlam.tpi.budgets.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDataResponseDto {

    private Long supplierId;
    private String supplierName;
    private Double price;
    private Integer daysCount;
    private Integer workerCount;
    private String detail;
    private String state;
    private Boolean hired;

}
