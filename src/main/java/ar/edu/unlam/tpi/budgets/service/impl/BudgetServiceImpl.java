package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDetailDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestState;
import ar.edu.unlam.tpi.budgets.model.BudgetState;
import ar.edu.unlam.tpi.budgets.persistence.dao.BudgetDAO;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.BudgetCreationResponseBuilder;
import ar.edu.unlam.tpi.budgets.utils.BudgetValidator;
import ar.edu.unlam.tpi.budgets.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetDAO budgetDAO;
    private final BudgetCreationResponseBuilder budgetCreationResponseBuilder;
    private final BudgetValidator budgetValidator;

    @Override
    public BudgetCreationResponseDto create(BudgetCreationRequestDto request) {
        log.info("Iniciando creación de presupuesto para solicitante ID {} - nombre: {}", request.getApplicantId(),
                request.getApplicantName());

        BudgetRequestEntity budgetRequest = Converter.toBudgetRequest(request);
        log.debug("Objeto BudgetRequest generado: {}", budgetRequest);

        BudgetRequestEntity saved = budgetDAO.save(budgetRequest);
        log.info("Presupuesto creado con ID: {}", saved.getId());

        return budgetCreationResponseBuilder.build(saved);
    }

    @Override
    public List<BudgetResponseDto> getBudgetsByApplicantId(Long applicantId) {
        log.info("Buscando presupuestos para solicitante con ID {}", applicantId);

        List<BudgetRequestEntity> budgetEntities = budgetDAO.findByApplicantId(applicantId);
        log.info("Cantidad de presupuestos encontrados: {}", budgetEntities.size());

        return Converter.toBudgetListResponse(budgetEntities);
    }

    @Override
    public List<BudgetSupplierResponseDto> getBudgetsBySupplierId(Long supplierId) {
        log.info("Buscando presupuestos enviados por el proveedor con ID {}", supplierId);
    
        List<BudgetRequestEntity> budgetEntities = budgetDAO.findBySupplierId(supplierId);
        log.info("Cantidad de solicitudes encontradas donde participó el proveedor {}: {}", supplierId, budgetEntities.size());
    
        return Converter.toBudgetSupplierResponseList(budgetEntities, supplierId);
    }

    @Override
    public BudgetResponseDetailDto getBudgetDetailById(Long budgetId) {
        log.info("Buscando detalle de presupuesto con ID {}", budgetId);

        try {
            BudgetRequestEntity entity = budgetDAO.findById(budgetId);
            log.info("Detalle de presupuesto obtenido para ID {}", budgetId);
            return Converter.toBudgetResponse(entity);
        } catch (Exception ex) {
            log.error("Error al obtener detalle del presupuesto con ID {}: {}", budgetId, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void finalizeBudgetRequest(Long budgetId, BudgetFinalizeRequestDto request) {
        BudgetRequestEntity entity = budgetDAO.findById(budgetId);

        log.info("Validando proveedor contratado");
        budgetValidator.validateSupplierHired(entity, request);

        log.info("Guardando presupuesto elegido");
        budgetDAO.save(entity);
    }

    @Override
    public void finalizeRequestOnly(Long id) {
        BudgetRequestEntity entity = budgetDAO.findById(id);
        log.info("Finalizando presupuesto con ID {}", id);
        entity.setState(BudgetRequestState.FINALIZED);
        log.info("Presupuesto finalizado con ID: {}", id);
        budgetDAO.save(entity);
    }

    public void update(Long id, Long providerId,  BudgetUpdateDataRequestDto request) {

        BudgetRequestEntity existingBudget = updateFieldsOfBudget(budgetDAO.findById(id), request, providerId);
        log.info("Actualizando presupuesto con ID {}", id);

        budgetDAO.save(existingBudget);
        log.info("Presupuesto actualizado con ID: {}", existingBudget.getId());
    }

    private BudgetRequestEntity updateFieldsOfBudget(BudgetRequestEntity existingBudget, BudgetUpdateDataRequestDto request, Long providerId) {
    
        existingBudget.getBudgets().stream()
            .filter(b -> b.getSupplierEntity().getId().equals(providerId))
            .findFirst()
            .ifPresentOrElse(budget -> {
                budget.setState(BudgetState.ACCEPTED);
                budget.setPrice(request.getPrice());
                budget.setDaysCount(request.getDaysCount());
                budget.setWorkerCount(request.getWorkerCount());
                budget.setDetail(request.getDetail());
            }, () -> {
                throw new IllegalArgumentException("No se encontró el proveedor con ID: " + providerId);
            });
    
        return existingBudget;
    }
    
}
