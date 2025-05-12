package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository repository;

    public BudgetServiceImpl(BudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public BudgetCreationResponse create(BudgetCreationRequest request) {
        BudgetRequest budgetRequest = Converter.toBudgetRequest(request);
        BudgetRequest saved = repository.save(budgetRequest);
        return BudgetCreationResponse.builder()
                .id(saved.getId())
                .build();
    }

    @Override
    public BudgetRequestListResponse getBudgetsByApplicantId(Long applicantId) {
        List<BudgetRequest> budgetEntities = repository.findByApplicantId(applicantId);
        return Converter.toBudgetListResponse(budgetEntities);
    }

    @Override
    public BudgetResponse getBudgetDetailById(String budgetId) {
        BudgetRequest entity = repository.findById(budgetId).orElse(null);
        if (entity == null) return null;

        return Converter.toBudgetResponse(entity);
    }

}