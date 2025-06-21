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
import java.util.Optional;

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
            log.error("Error al buscar todos los presupuestos: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public BudgetRequestEntity findById(Long id) {
        Optional<BudgetRequestEntity> entity;

        try {
            entity = repository.findById(id);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }

        if (entity.isEmpty()) {
            log.error("Budget id {} not found", id);
            throw new NotFoundException("Presupuesto no encontrado con ID: " + id);
        }

        return entity.get();
    }

    @Override
    public BudgetRequestEntity save(BudgetRequestEntity entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            log.error("Error al guardar presupuesto: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar presupuesto: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<BudgetRequestEntity> findByApplicantId(Long applicantId) {
        try {
            return repository.findByApplicantId(applicantId);
        } catch (Exception e) {
            log.error("Error buscando presupuestos por solicitante: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<BudgetRequestEntity> findBySupplierId(Long supplierId) {
        try {
            return repository.findBySupplierId(supplierId);
        } catch (Exception e) {
            log.error("Error buscando presupuestos por proveedor: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public BudgetRequestEntity findByIdAndSupplierId(Long id, Long supplierId) {
        try {
            return repository.findByIdAndSupplierId(id, supplierId).orElseThrow(() -> {;
                log.error("Presupuesto con ID {} no encontrado para el proveedor con ID {}", id, supplierId);
                return new NotFoundException("Presupuesto no encontrado con ID: " + id + " para el proveedor: " + supplierId);
            });
        } catch (Exception e) {
            log.error("Error al buscar todos los presupuestos: {}", e.getMessage());
            throw new InternalException(e.getMessage());
        }
    }


}
