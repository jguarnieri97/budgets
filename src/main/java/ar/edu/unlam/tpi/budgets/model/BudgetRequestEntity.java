package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BUDGET_REQUEST", schema = "BUDGETS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BudgetRequestEntity {

    @Id
    private Long id;

    @Column(name = "budget_number")
    private String budgetNumber;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicantEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private CategoryType category;

    private BudgetRequestState state;

    @ElementCollection
    @CollectionTable(name = "budget_files", schema = "BUDGETS", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "file_id")
    private List<String> files;

    @Embedded
    private BudgetDetail budgetDetail;

    @OneToMany(mappedBy = "budgetRequestEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

}