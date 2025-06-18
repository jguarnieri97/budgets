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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = false)
    private BudgetRequestEntity budgetRequestEntity;

    private Float price;

    @Column(name = "days_count")
    private Integer daysCount;

    @Column(name = "worker_count")
    private Integer workerCount;

    private String detail;

    private BudgetState state;

    private Boolean hired;

}
