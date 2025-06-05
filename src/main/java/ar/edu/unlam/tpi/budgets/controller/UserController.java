package ar.edu.unlam.tpi.budgets.controller;

import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("budgets/v1/user")
@Validated
public interface UserController {

    @GetMapping("/applicant/{applicantId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get applicant's budget requests")
    GenericResponse<List<BudgetRequestResponseDto>> getBudgetsByApplicantId(
            @PathVariable("applicantId") @NotNull Long applicantId);

            @GetMapping("/supplier/{supplierId}")
            @ResponseStatus(HttpStatus.OK)
            @Operation(summary = "Get applicant's budget requests")
            GenericResponse<List<BudgetSupplierResponseDto>> getBudgetsBySupplierId(
                    @PathVariable("supplierId") @NotNull Long supplierId);
        
}
