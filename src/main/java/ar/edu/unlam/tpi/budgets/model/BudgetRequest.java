package ar.edu.unlam.tpi.budgets.model;

import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "budgets")
public class BudgetRequest {

    @Id
    private String id;
    private String budgetNumber;
    private Long applicantId;
    private String applicantName;
    private LocalDateTime createdAt;
    private BudgetState state;
    private List<String> files;
    private BudgetDetail budgetDetail;
    private List<Budget> budgets;

}