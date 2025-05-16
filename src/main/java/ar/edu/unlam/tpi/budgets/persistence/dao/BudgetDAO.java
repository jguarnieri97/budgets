package ar.edu.unlam.tpi.budgets.persistence.dao;

import ar.edu.unlam.tpi.budgets.model.BudgetRequest;

import java.util.List;

public interface BudgetDAO extends GenericInterfaceDAO<BudgetRequest, String>{

    List<BudgetRequest> findByApplicantId(Long applicantId);

}
