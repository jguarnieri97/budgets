package ar.edu.unlam.tpi.budgets.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BudgetRepository extends MongoRepository<BudgetRequest, String> {

    List<BudgetRequest> findByApplicantId(Long applicantId);

    @Query("{'budgets': {$elemMatch: {'supplierId': ?0}}}")
    List<BudgetRequest> findBySupplierId(Long supplierId);

}
