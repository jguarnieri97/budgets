package ar.edu.unlam.tpi.budgets.persistence.dao.impl;

import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.persistence.dao.BudgetDAO;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BudgetDAOImpl implements BudgetDAO {

    private final BudgetRepository repository;

    @Override
    public List<BudgetRequestEntity> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public BudgetRequestEntity findById(Long id) {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Budget request not found"));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getDetail());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public BudgetRequestEntity save(BudgetRequestEntity entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<BudgetRequestEntity> findByApplicantId(Long applicantId) {
        try {
            return repository.findByApplicantId(applicantId);
        }  catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<BudgetRequestEntity> findBySupplierId(Long supplierId) {
        try {
            return repository.findBySupplierId(supplierId);
        }  catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }


}
