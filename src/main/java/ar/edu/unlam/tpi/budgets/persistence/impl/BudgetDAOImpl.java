package ar.edu.unlam.tpi.budgets.persistence.impl;

import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
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
    public List<BudgetRequest> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public BudgetRequest findById(String id) {
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
    public BudgetRequest save(BudgetRequest entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<BudgetRequest> findByApplicantId(Long applicantId) {
        try {
            List<BudgetRequest> results = repository.findByApplicantId(applicantId);
            if (results.isEmpty()) {
                throw new NotFoundException("No se encontraron presupuestos para el applicantId: " + applicantId);
            }
            return results;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getDetail());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

}
