package ar.edu.unlam.tpi.budgets.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Budget {

    private Long supplierId;
    private Float price;
    private Integer daysCount;
    private Integer workerCount;
    private String detail;

}
