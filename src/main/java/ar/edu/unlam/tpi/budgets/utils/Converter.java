package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetDetail;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    public static BudgetRequest toBudgetRequest(BudgetCreationRequest request) {
        BudgetDetail detail = BudgetDetail.builder()
                .isUrgent(request.isUrgent())
                .estimatedDate(DateTimeUtils.toLocalDateTime(request.getEstimatedDate()))
                .workResume(request.getWorkResume())
                .workDetail(request.getWorkDetail())
                .build();

        List<Budget> budgets = request.getSuppliers().stream()
                .map(supplierId -> Budget.builder()
                        .supplierId((long) supplierId)
                        .build())
                .collect(Collectors.toList());

        return BudgetRequest.builder()
                .applicantId(request.getApplicantId())
                .createdAt(LocalDateTime.now())
                .state(BudgetState.PENDING)
                .files(request.getFiles())
                .budgetDetail(detail)
                .budgets(budgets)
                .build();
    }

    public static BudgetRequestResponse toBudgetRequestResponse(BudgetRequest budget) {
        if (budget == null) {
            return null;
        }
        return BudgetRequestResponse.builder()
                .id(budget.getId())
                .applicantId(budget.getApplicantId())
                .date(DateTimeUtils.toString(budget.getCreatedAt()))
                .build();
    }

    public static BudgetRequestListResponse toBudgetListResponse(List<BudgetRequest> budgetRequests) {
        List<BudgetRequestResponse> responses = budgetRequests.stream()
                .map(Converter::toBudgetRequestResponse)
                .collect(Collectors.toList());
        return BudgetRequestListResponse.builder()
                .budgets(responses)
                .build();
    }

    public static BudgetResponse toBudgetResponse(BudgetRequest entity) {
        if (entity == null) {
            return null;
        }
        return BudgetResponse.builder()
                .id(entity.getId())
                .applicantId(entity.getApplicantId())
                .createdAt(DateTimeUtils.toString(entity.getCreatedAt()))
                .files(entity.getFiles())
                .detail(toBudgetDetailResponse(entity.getBudgetDetail()))
                .budgets(toBudgetDataResponseList(entity.getBudgets()))
                .build();
    }

    public static BudgetDetailResponse toBudgetDetailResponse(BudgetDetail detail) {
        return BudgetDetailResponse.builder()
                .isUrgent(detail.isUrgent())
                .estimatedDate(DateTimeUtils.toString(detail.getEstimatedDate()))
                .workResume(detail.getWorkResume())
                .workDetail(detail.getWorkDetail())
                .build();
    }

    public static List<BudgetDataResponse> toBudgetDataResponseList(List<Budget> budgets) {
        return budgets.stream()
                .map(b -> BudgetDataResponse.builder()
                        .supplierId(b.getSupplierId())
                        .price(b.getPrice() != null ? Double.valueOf(b.getPrice()) : null)
                        .daysCount(b.getDaysCount() != null ? b.getDaysCount() : 0)
                        .workerCount(b.getWorkerCount() != null ? b.getWorkerCount() : 0)
                        .detail(b.getDetail() != null ? b.getDetail() : "")
                        .build())
                .toList();
    }

}
