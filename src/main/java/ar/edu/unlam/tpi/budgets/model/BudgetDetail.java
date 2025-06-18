package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDetail {

    private String workResume;
    private String workDetail;

}
