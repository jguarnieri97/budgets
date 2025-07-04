package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BUDGET_REQUEST", schema = "BUDGETS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BudgetRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_number")
    private String budgetNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicantEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private CategoryType category;

    @Setter
    private BudgetRequestState state;

    @ElementCollection
    @CollectionTable(name = "BUDGET_FILES", schema = "BUDGETS", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "file_data")
    private List<byte[]> files;

    @Embedded
    private BudgetDetail budgetDetail;

    @OneToMany(mappedBy = "budgetRequestEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

    public BudgetRequestEntity(String budgetNumber, ApplicantEntity applicantEntity, CategoryType category, List<byte[]> files, BudgetDetail budgetDetail, List<Budget> budgets) {
        this.budgetNumber = budgetNumber;
        this.applicantEntity = applicantEntity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.category = category;
        this.state = BudgetRequestState.INITIATED;
        this.files = files;
        this.budgetDetail = budgetDetail;
        this.budgets = budgets;
    }
}