package ar.edu.unlam.tpi.budgets.persistence.repository;

import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetRequestEntity, Long> {

    @Query("SELECT br FROM BudgetRequestEntity br " +
            "WHERE br.applicantEntity.id = ?1")
    List<BudgetRequestEntity> findByApplicantId(Long applicantId);

    @Query("SELECT br FROM BudgetRequestEntity br " +
            "JOIN Budget b ON br.id = b.budgetRequestEntity.id " +
            "WHERE b.supplierEntity.id = ?1")
    List<BudgetRequestEntity> findBySupplierId(Long supplierId);

    @Query("SELECT br FROM BudgetRequestEntity br " +
            "JOIN Budget b ON br.id = b.budgetRequestEntity.id " +
            "WHERE b.supplierEntity.id = ?1 AND br.id = ?2")
    Optional<BudgetRequestEntity> findByIdAndSupplierId(Long id, Long supplierId);

}
