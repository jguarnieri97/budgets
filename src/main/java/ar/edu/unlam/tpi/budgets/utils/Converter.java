package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.SupplierDataRequest;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    
    public static BudgetRequestEntity toBudgetRequest(BudgetCreationRequestDto request, String budgetNumber) {
        var detail = BudgetDetail.builder()
                .workResume(request.getWorkResume())
                .workDetail(request.getWorkDetail())
                .build();

        List<Budget> budgets = request.getSuppliers().stream()
                .map(Converter::buildBudget)
                .collect(Collectors.toList());

        var applicant = ApplicantEntity.builder()
                .id(request.getApplicantId())
                .name(request.getApplicantName())
                .build();

        var entity = new BudgetRequestEntity(budgetNumber, applicant,
                CategoryType.valueOf(request.getCategory()), request.getFiles(), detail, budgets);

        budgets.forEach(budget -> budget.setBudgetRequestEntity(entity));

        return entity;
    }

   private static Budget buildBudget(SupplierDataRequest request) {
       var supplier = SupplierEntity.builder()
               .id(request.getSupplierId())
               .name(request.getSupplierName())
               .build();

       return new Budget(supplier);
   }

    public static BudgetResponseDto toBudgetRequestResponse(BudgetRequestEntity budget) {
        if (budget == null) {
            return null;
        }
        return BudgetResponseDto.builder()
                .id(budget.getId())
                .budgetNumber(budget.getBudgetNumber())
                .applicantId(budget.getApplicantEntity().getId())
                .applicantName(budget.getApplicantEntity().getName())
                .category(budget.getCategory().toString())
                .state(budget.getState().name())
                .date(DateTimeUtils.toString(budget.getCreatedAt()))
                .build();
    }

    public static List<BudgetResponseDto> toBudgetListResponse(List<BudgetRequestEntity> budgetRequests) {
        return budgetRequests.stream()
                .map(Converter::toBudgetRequestResponse)
                .collect(Collectors.toList());
    }

    public static BudgetResponseDetailDto toBudgetResponse(BudgetRequestEntity entity) {
        if (entity == null) {
            return null;
        }
        return BudgetResponseDetailDto.builder()
                .id(entity.getId())
                .applicantId(entity.getApplicantEntity().getId())
                .applicantName(entity.getApplicantEntity().getName())
                .createdAt(DateTimeUtils.toString(entity.getCreatedAt()))
                .files(entity.getFiles())
                .detail(toBudgetDetailResponse(entity.getBudgetDetail()))
                .state(entity.getState().name())
                .category(entity.getCategory().toString())
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
                        .supplierId(b.getSupplierEntity().getId())
                        .supplierName(b.getSupplierEntity().getName())
                        .price(b.getPrice() != null ? Double.valueOf(b.getPrice()) : null)
                        .daysCount(b.getDaysCount() != null ? b.getDaysCount() : 0)
                        .workerCount(b.getWorkerCount() != null ? b.getWorkerCount() : 0)
                        .detail(b.getDetail() != null ? b.getDetail() : "")
                        .state(b.getState().name())
                        .hired(b.getHired())
                        .build())
                .toList();
    }

    public static List<BudgetSupplierResponseDto> toBudgetSupplierResponseList(List<BudgetRequestEntity> entities, Long supplierId) {
        return entities.stream()
            .flatMap(request -> request.getBudgets().stream()
                .filter(b -> b.getSupplierEntity().getId().equals(supplierId))
                .map(b -> BudgetSupplierResponseDto.builder()
                    .id(request.getId()) //ID de la solicitud
                    .budgetNumber(request.getBudgetNumber())
                    .applicantId(request.getApplicantEntity().getId())
                    .applicantName(request.getApplicantEntity().getName())
                    .category(request.getCategory().toString())
                    .budgetState(b.getState().name()) //Estado del presupuesto individual
                    .budgetRequestState(request.getState().name()) //Estado de la solicitud de presupuesto
                    .date(DateTimeUtils.toString(request.getCreatedAt()))
                    .isHired(b.getHired())
                    .build()))
            .toList();
    }

}
