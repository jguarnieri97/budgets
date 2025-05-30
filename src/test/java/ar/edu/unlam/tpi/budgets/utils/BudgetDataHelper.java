package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.SupplierDataRequest;

import java.util.List;

public class BudgetDataHelper {

    public static SupplierDataRequest supplier(Long id, String name) {
        SupplierDataRequest supplier = new SupplierDataRequest();
        supplier.setSupplierId(id);
        supplier.setSupplierName(name);
        return supplier;
    }

    public static BudgetCreationRequestDto createValidRequest(Long applicantId, String applicantName, List<SupplierDataRequest> suppliers) {
        return BudgetCreationRequestDto.builder()
                .applicantId(applicantId)
                .applicantName(applicantName)
                .isUrgent(false)
                .estimatedDate(null)
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
                .isUrgent(false)
                .estimatedDate(null)
                .workResume("Trabajo inválido")
                .workDetail("Sin datos")
                .files(List.of("img"))
                .suppliers(List.of(supplier(1L, "Proveedor X")))
                .build();
    }
}
