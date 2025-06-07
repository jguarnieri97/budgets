package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import ar.edu.unlam.tpi.budgets.utils.BudgetUpdatedDataRequestHelper;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetControllerImplTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetControllerImpl budgetController;

    @Test
    void givenValidRequest_whenCreateBudget_thenReturnGenericResponse() {
        // Arrange
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
            1L, "Juan Pérez",
            java.util.List.of(
                BudgetDataHelper.supplier(10L, "Proveedor A"),
                BudgetDataHelper.supplier(11L, "Proveedor B")
            )
        );

        String fakeId = UUID.randomUUID().toString();
        BudgetCreationResponseDto response = BudgetCreationResponseDto.builder()
            .id(fakeId)
            .build();

        when(budgetService.create(request)).thenReturn(response);

        // Act
        GenericResponse<BudgetCreationResponseDto> result = budgetController.createBudget(request);

        // Assert
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(fakeId, result.getData().getId());
    }

    @Test
    void givenBudgetId_whenGetBudgetDetail_thenReturnGenericResponse() {
        // Arrange
        String id = "abc123";
        BudgetResponseDto responseDto = BudgetDataHelper.createBudgetResponse(id);

        when(budgetService.getBudgetDetailById(id)).thenReturn(responseDto);

        // Act
        GenericResponse<BudgetResponseDto> result = budgetController.getBudgetDetailById(id);

        // Assert
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(responseDto, result.getData());
    }

    @Test
    void givenValidData_whenUpdateBudget_thenReturnSuccessResponse() {
        // Given
        String budgetId = "budget123";
        Long providerId = 1L;
        BudgetUpdateDataRequestDto request = BudgetUpdatedDataRequestHelper.getBudgetUpdateDataRequestDto();

        // When
        GenericResponse<Void> response = budgetController.updateBudget(budgetId, providerId, request);

        // Then
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(Constants.STATUS_OK, response.getCode(), "El código de estado debería ser 200");
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage(), "El mensaje debería indicar actualización exitosa");
        assertNull(response.getData(), "No debería haber datos en la respuesta");
    }

    @Test
    void givenValidData_whenFinalizeBudgetRequest_thenReturnSuccessResponse() {
        // Given
        String budgetId = "budget123";
        BudgetFinalizeRequestDto request = BudgetFinalizeRequestDto.builder()
                .supplierHired(1L)
                .build();

        // When
        GenericResponse<Void> response = budgetController.finalizeBudgetRequest(budgetId, request);

        // Then
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(Constants.STATUS_OK, response.getCode(), "El código de estado debería ser 200");
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage(), "El mensaje debería indicar actualización exitosa");
        assertNull(response.getData(), "No debería haber datos en la respuesta");
    }

    @Test
    void givenValidId_whenFinalizeRequestOnly_thenReturnSuccessResponse() {
        // Given
        String budgetId = "budget123";

        // When
        GenericResponse<Void> response = budgetController.finalizeRequestOnly(budgetId);

        // Then
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(Constants.STATUS_OK, response.getCode(), "El código de estado debería ser 200");
        assertEquals(Constants.UPDATED_MESSAGE, response.getMessage(), "El mensaje debería indicar actualización exitosa");
        assertNull(response.getData(), "No debería haber datos en la respuesta");
    }
}
