package ar.edu.unlam.tpi.budgets.controller;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("budgets/v1/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create budget request")
    public BudgetCreationResponse createBudgetRequest(@Valid @RequestBody BudgetCreationRequest request) {
       return budgetService.create(request);
    }

    @GetMapping("/user/{applicantId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get applicant's budget requests")
    public BudgetRequestListResponse getBudgetsByApplicantId(@PathVariable Long applicantId) {
        return budgetService.getBudgetsByApplicantId(applicantId);
    }

    @GetMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get budget detail by id")
    public BudgetResponse getBudgetDetailById(@PathVariable String budgetId) {
        return budgetService.getBudgetDetailById(budgetId);
    }
}
