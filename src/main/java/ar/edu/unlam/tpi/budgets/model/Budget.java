package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUDGET", schema = "BUDGETS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = false)
    private BudgetRequestEntity budgetRequestEntity;

    @Setter
    private Float price;

    @Setter
    @Column(name = "days_count")
    private Integer daysCount;

    @Setter
    @Column(name = "worker_count")
    private Integer workerCount;

    @Setter
    private String detail;

    @Setter
    private BudgetState state;

    @Setter
    private Boolean hired;

    public Budget(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
        this.state = BudgetState.PENDING;
        this.hired = false;
    }
}
