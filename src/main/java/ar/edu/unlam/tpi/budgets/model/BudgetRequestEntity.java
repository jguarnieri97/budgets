package ar.edu.unlam.tpi.budgets.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "budgets")
public class BudgetRequestEntity {

    @Id
    private String id;
    private String budgetNumber;
    private Boolean isRead;

    @NotNull(message = "El ID del solicitante no puede ser nulo")
    private Long applicantId;
    
    private String applicantName;
    private LocalDateTime createdAt;
    private String category;
    private BudgetRequestState state;
    private List<String> files;
    private BudgetDetail budgetDetail;
    private List<Budget> budgets;

}