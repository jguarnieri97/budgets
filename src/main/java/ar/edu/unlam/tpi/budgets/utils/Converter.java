package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetDetail;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class Converter {

    
    public static BudgetRequestEntity toBudgetRequest(BudgetCreationRequestDto request) {
        BudgetDetail detail = BudgetDetail.builder()
                .workResume(request.getWorkResume())
                .workDetail(request.getWorkDetail())
                .build();

        List<Budget> budgets = request.getSuppliers().stream()
                .map(data -> Budget.builder()
                        .supplierId(data.getSupplierId())
                        .supplierName(data.getSupplierName())
                        .hired(false)
                        .state(BudgetState.PENDING)
                        .build())
                .collect(Collectors.toList());

        return BudgetRequestEntity.builder()
                .applicantId(request.getApplicantId())
                .applicantName(request.getApplicantName())
                .budgetNumber(formatBuildNumber(new Random().nextInt(1000000), 7)) // Genera un número único basado en UUID
                .createdAt(LocalDateTime.now())
                .state(BudgetState.INITIATED)
                .isRead(false)
                .category(request.getCategory())
                .files(request.getFiles())
                .budgetDetail(detail)
                .budgets(budgets)
                .build();
    }

    public static String formatBuildNumber(int buildNumber, int minDigits) {
        return String.format("%0" + minDigits + "d", buildNumber);
    }

    public static BudgetRequestResponseDto toBudgetRequestResponse(BudgetRequestEntity budget) {
        if (budget == null) {
            return null;
        }
        return BudgetRequestResponseDto.builder()
                .id(budget.getId())
                .budgetNumber(budget.getBudgetNumber())
                .applicantId(budget.getApplicantId())
                .applicantName(budget.getApplicantName())
                .category(budget.getCategory())
                .state(budget.getState().name())
                .isRead(budget.getIsRead())
                .date(DateTimeUtils.toString(budget.getCreatedAt()))
                .build();
    }

    public static List<BudgetRequestResponseDto> toBudgetListResponse(List<BudgetRequestEntity> budgetRequests) {
        return budgetRequests.stream()
                .map(Converter::toBudgetRequestResponse)
                .collect(Collectors.toList());
    }

    public static BudgetResponseDto toBudgetResponse(BudgetRequestEntity entity) {
        if (entity == null) {
            return null;
        }
        return BudgetResponseDto.builder()
                .id(entity.getId())
                .applicantId(entity.getApplicantId())
                .applicantName(entity.getApplicantName())
                .createdAt(DateTimeUtils.toString(entity.getCreatedAt()))
                .files(entity.getFiles())
                .detail(toBudgetDetailResponse(entity.getBudgetDetail()))
                .state(entity.getState().name())
                .isRead(entity.getIsRead())
                .category(entity.getCategory())
                .budgetNumber(entity.getBudgetNumber())
                .budgets(toBudgetDataResponseList(entity.getBudgets()))
                .build();
    }

    public static BudgetDetailResponseDto toBudgetDetailResponse(BudgetDetail detail) {
        return BudgetDetailResponseDto.builder()
                .workResume(detail.getWorkResume())
                .workDetail(detail.getWorkDetail())
                .build();
    }

    public static List<BudgetDataResponseDto> toBudgetDataResponseList(List<Budget> budgets) {
        return budgets.stream()
                .map(b -> BudgetDataResponseDto.builder()
                        .supplierId(b.getSupplierId())
                        .supplierName(b.getSupplierName())
                        .price(b.getPrice() != null ? Double.valueOf(b.getPrice()) : null)
                        .daysCount(b.getDaysCount() != null ? b.getDaysCount() : 0)
                        .workerCount(b.getWorkerCount() != null ? b.getWorkerCount() : 0)
                        .detail(b.getDetail() != null ? b.getDetail() : "")
                        .state(b.getState().name())
                        .hired(b.getHired())
                        .build())
                .toList();
    }

}
