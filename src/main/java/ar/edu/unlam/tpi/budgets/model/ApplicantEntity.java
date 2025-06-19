package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "APPLICANT_COMPANY", schema = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantEntity {
    @Id
    private Long id;

    @Column(name = "user_name")
    private String name;

    @OneToMany(mappedBy = "applicantEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetRequestEntity> budgets;
}
