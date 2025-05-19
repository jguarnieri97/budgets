package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetDetail;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class Converter {

    public static BudgetRequest toBudgetRequest(BudgetCreationRequestDto request) {
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
                .budgetNumber(formatBuildNumber(new Random().nextInt(1000000), 7)) // Genera un número único basado en UUID
                .createdAt(LocalDateTime.now())
                .state(BudgetState.PENDING)
                .files(request.getFiles())
                .budgetDetail(detail)
                .budgets(budgets)
                .build();
    }

    public static String formatBuildNumber(int buildNumber, int minDigits) {
        return String.format("%0" + minDigits + "d", buildNumber);
    }

    public static BudgetRequestResponseDto toBudgetRequestResponse(BudgetRequest budget) {
        if (budget == null) {
            return null;
        }
        return BudgetRequestResponseDto.builder()
                .id(budget.getId())
                .applicantId(budget.getApplicantId())
                .date(DateTimeUtils.toString(budget.getCreatedAt()))
                .build();
    }

    public static List<BudgetRequestResponseDto> toBudgetListResponse(List<BudgetRequest> budgetRequests) {
        return budgetRequests.stream()
                .map(Converter::toBudgetRequestResponse)
                .collect(Collectors.toList());
    }

    public static BudgetResponseDto toBudgetResponse(BudgetRequest entity) {
        if (entity == null) {
            return null;
        }
        return BudgetResponseDto.builder()
                .id(entity.getId())
                .applicantId(entity.getApplicantId())
                .createdAt(DateTimeUtils.toString(entity.getCreatedAt()))
                .files(entity.getFiles())
                .detail(toBudgetDetailResponse(entity.getBudgetDetail()))
                .budgets(toBudgetDataResponseList(entity.getBudgets()))
                .build();
    }

    public static BudgetDetailResponseDto toBudgetDetailResponse(BudgetDetail detail) {
        return BudgetDetailResponseDto.builder()
                .isUrgent(detail.isUrgent())
                .estimatedDate(DateTimeUtils.toString(detail.getEstimatedDate()))
                .workResume(detail.getWorkResume())
                .workDetail(detail.getWorkDetail())
                .build();
    }

    public static List<BudgetDataResponseDto> toBudgetDataResponseList(List<Budget> budgets) {
        return budgets.stream()
                .map(b -> BudgetDataResponseDto.builder()
                        .supplierId(b.getSupplierId())
                        .price(b.getPrice() != null ? Double.valueOf(b.getPrice()) : null)
                        .daysCount(b.getDaysCount() != null ? b.getDaysCount() : 0)
                        .workerCount(b.getWorkerCount() != null ? b.getWorkerCount() : 0)
                        .detail(b.getDetail() != null ? b.getDetail() : "")
                        .build())
                .toList();
    }

}
