package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.SupplierDataRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetDataResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetDetailResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetDetail;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;

import java.util.List;

public class BudgetDataHelper {

    public static SupplierDataRequest supplier(Long id, String name) {
        SupplierDataRequest supplier = new SupplierDataRequest();
        supplier.setSupplierId(id);
        supplier.setSupplierName(name);
        return supplier;
    }

    public static BudgetCreationRequestDto createValidRequest(Long applicantId, String applicantName,
            List<SupplierDataRequest> suppliers) {
        return BudgetCreationRequestDto.builder()
                .applicantId(applicantId)
                .applicantName(applicantName)
                .category("CONTRATISTA")
                .workResume("Trabajo test")
                .workDetail("Detalle test")
                .files(List.of("file1", "file2"))
                .suppliers(suppliers)
                .build();
    }

    public static BudgetCreationRequestDto createInvalidRequest() {
        return BudgetCreationRequestDto.builder()
                .applicantId(null)
                .applicantName(null)
                .workResume("Trabajo inválido")
                .workDetail("Sin datos")
                .files(List.of("img"))
                .suppliers(List.of(supplier(1L, "Proveedor X")))
                .build();
    }

    public static BudgetResponseDto createBudgetResponse(String id) {
        return BudgetResponseDto.builder()
                .id(id)
                .applicantName("Logibyte")
                .files(List.of("file1.pdf", "file2.docx"))
                .detail(BudgetDetailResponseDto.builder()
                        .workResume("Reparación de panel interior")
                        .workDetail("Detalle del trabajo")
                        .build())
                .budgets(List.of(
                        BudgetDataResponseDto.builder()
                                .supplierId(1L)
                                .supplierName("ElectraSol")
                                .price(100000.0)
                                .daysCount(2)
                                .workerCount(3)
                                .build()))
                .build();
    }

    public static BudgetRequestEntity createBudgetRequestEntity(String id, Long applicantId, String applicantName) {
        return BudgetRequestEntity.builder()
                .id(id)
                .applicantId(applicantId)
                .applicantName(applicantName)
                .state(BudgetState.INITIATED)
                .isRead(false)
                .category("CONTRATISTA")
                .budgetDetail(BudgetDetail.builder()
                        .workResume("Instalación eléctrica")
                        .workDetail("Se requiere instalación completa en oficina")
                        .build())
                .budgets(List.of(Budget.builder()
                        .supplierId(1L)
                        .supplierName("ElectraSol")
                        .price(100000f)
                        .daysCount(2)
                        .workerCount(3)
                        .detail("Tendido de cableado y tablero")
                        .state(BudgetState.PENDING)
                        .build()))
                .files(List.of("file1.pdf", "file2.docx"))
                .build();
    }

    public static BudgetRequestResponseDto budgetRequest(String id) {
        return BudgetRequestResponseDto.builder().id(id).build();
    }

    public static BudgetRequestEntity createBudgetRequestWithBudgets(String id, List<Budget> budgets) {
        return BudgetRequestEntity.builder()
                .id(id)
                .applicantId(999L)
                .applicantName("Mocked Applicant")
                .state(BudgetState.PENDING)
                .category("CONTRATISTA")
                .isRead(false)
                .budgetDetail(BudgetDetail.builder()
                        .workResume("Test")
                        .workDetail("Test detail")
                        .build())
                .budgets(budgets)
                .files(List.of("file.pdf"))
                .build();
    }

    public static Budget createBudget(Long supplierId, boolean hired, BudgetState state) {
        return Budget.builder()
                .supplierId(supplierId)
                .supplierName("Proveedor " + supplierId)
                .hired(hired)
                .price(100000f)
                .daysCount(1)
                .workerCount(2)
                .detail("Test Detail")
                .state(state)
                .build();
    }

}
