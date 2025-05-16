package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import ar.edu.unlam.tpi.budgets.persistence.dao.BudgetDAO;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
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

    @Override
    public BudgetCreationResponseDto create(BudgetCreationRequestDto request) {
        BudgetRequest budgetRequest = Converter.toBudgetRequest(request);
        BudgetRequest saved = budgetDAO.save(budgetRequest);
        return BudgetCreationResponseDto.builder()
                .id(saved.getId())
                .build();
    }

    @Override
    public BudgetRequestListResponseDto getBudgetsByApplicantId(Long applicantId) {
        List<BudgetRequest> budgetEntities = budgetDAO.findByApplicantId(applicantId);
        return Converter.toBudgetListResponse(budgetEntities);
    }

    @Override
    public BudgetResponseDto getBudgetDetailById(String budgetId) {
        BudgetRequest entity = budgetDAO.findById(budgetId);
        return Converter.toBudgetResponse(entity);
    }

}