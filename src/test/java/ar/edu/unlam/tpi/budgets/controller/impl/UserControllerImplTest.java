package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.budgets.utils.Constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerImplTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private UserControllerImpl userController;

    public static final Long BUDGET_ID_1 = 1L;
    public static final Long BUDGET_ID_2 = 2L;

    @Test
    void givenApplicantId_whenGetBudgetsByApplicantId_thenReturnGenericResponse() {
        // Arrange
        Long applicantId = 1L;
        List<BudgetResponseDto> expectedList = List.of(
            BudgetDataHelper.budgetRequest(BUDGET_ID_1),
            BudgetDataHelper.budgetRequest(BUDGET_ID_2)
        );

        when(budgetService.getBudgetsByApplicantId(applicantId)).thenReturn(expectedList);

        // Act
        GenericResponse<List<BudgetResponseDto>> response = userController.getBudgetsByApplicantId(applicantId);

        // Assert
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
    }

    @Test
    void givenSupplierId_whenGetBudgetsBySupplierId_thenReturnGenericResponse() {
        // Given
        Long supplierId = 1L;
        List<BudgetSupplierResponseDto> expectedList = List.of(
                BudgetDataHelper.createBudgetSupplierResponse(BUDGET_ID_1),
                BudgetDataHelper.createBudgetSupplierResponse(BUDGET_ID_2)
        );

        when(budgetService.getBudgetsBySupplierId(supplierId)).thenReturn(expectedList);

        // When
        GenericResponse<List<BudgetSupplierResponseDto>> response = userController.getBudgetsBySupplierId(supplierId);

        // Then
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(Constants.STATUS_OK, response.getCode(), "El código de estado debería ser 200");
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage(), "El mensaje debería ser de éxito");
        assertEquals(2, response.getData().size(), "Deberían retornarse 2 presupuestos");
    }

}