package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
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
    public List<BudgetRequestResponseDto> getBudgetsByApplicantId(Long applicantId) {
        log.info("Buscando presupuestos para solicitante con ID {}", applicantId);

        List<BudgetRequestEntity> budgetEntities = budgetDAO.findByApplicantId(applicantId);
        log.info("Cantidad de presupuestos encontrados: {}", budgetEntities.size());

        return Converter.toBudgetListResponse(budgetEntities);
    }

    @Override
    public List<BudgetRequestResponseDto> getBudgetsBySupplierId(Long supplierId) {
        log.info("Buscando presupuestos para proveedor con ID {}", supplierId);

        List<BudgetRequestEntity> budgetEntities = budgetDAO.findBySupplierId(supplierId);
        log.info("Cantidad de presupuestos encontrados para proveedor {}: {}", supplierId, budgetEntities.size());

        return Converter.toBudgetListResponse(budgetEntities);
    }

    @Override
    public BudgetResponseDto getBudgetDetailById(String budgetId) {
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
    public void update(String budgetId, BudgetUpdateRequestDto request) {
        BudgetRequestEntity entity = budgetDAO.findById(budgetId);

        log.info("Actualizando presupuesto con ID {} a estado {}", budgetId, request.getState());
        budgetValidator.validateAndApplyStateTransition(entity, request);
        log.info("Estado actualizado correctamente");

        budgetDAO.save(entity);
    }

}
