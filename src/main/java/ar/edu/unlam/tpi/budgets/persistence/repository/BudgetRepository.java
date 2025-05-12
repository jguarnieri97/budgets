package ar.edu.unlam.tpi.budgets.persistence.repository;

import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BudgetRepository extends MongoRepository<BudgetRequest, String> {

    List<BudgetRequest> findByApplicantId(Long applicantId);

}
