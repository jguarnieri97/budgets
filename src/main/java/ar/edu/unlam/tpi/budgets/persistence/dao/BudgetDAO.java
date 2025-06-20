package ar.edu.unlam.tpi.budgets.persistence.dao;

import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;

import java.util.List;

public interface BudgetDAO extends GenericInterfaceDAO<BudgetRequestEntity, Long>{

    List<BudgetRequestEntity> findByApplicantId(Long applicantId);
    List<BudgetRequestEntity> findBySupplierId(Long supplierId);
    BudgetRequestEntity findByIdAndSupplierId(Long id, Long supplierId);
}
