package ar.edu.unlam.tpi.budgets.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BudgetRepository extends MongoRepository<BudgetRequestEntity, String> {

    List<BudgetRequestEntity> findByApplicantId(Long applicantId);

    @Query("{'budgets': {$elemMatch: {'supplierId': ?0}}}")
    List<BudgetRequestEntity> findBySupplierId(Long supplierId);

}
