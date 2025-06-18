package ar.edu.unlam.tpi.budgets.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "SUPPLIER_COMPANY", schema = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SupplierEntity {

    @Id
    private Long id;

    @Column(name = "user_name")
    private String name;

    @OneToMany(mappedBy = "supplierEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;


}
