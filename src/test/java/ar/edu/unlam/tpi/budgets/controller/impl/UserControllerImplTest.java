package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
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

    @Test
    void givenApplicantId_whenGetBudgetsByApplicantId_thenReturnGenericResponse() {
        // Arrange
        Long applicantId = 1L;
        List<BudgetRequestResponseDto> expectedList = List.of(
            BudgetRequestResponseDto.builder().id("id1").build(),
            BudgetRequestResponseDto.builder().id("id2").build()
        );

        when(budgetService.getBudgetsByApplicantId(applicantId)).thenReturn(expectedList);

        // Act
        GenericResponse<List<BudgetRequestResponseDto>> response = userController.getBudgetsByApplicantId(applicantId);

        // Assert
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
    }

    @Test
    void givenSupplierId_whenGetBudgetsBySupplierId_thenReturnGenericResponse() {
        // Arrange
        Long supplierId = 10L;
        List<BudgetRequestResponseDto> expectedList = List.of(
            BudgetRequestResponseDto.builder().id("id3").build()
        );

        when(budgetService.getBudgetsBySupplierId(supplierId)).thenReturn(expectedList);

        // Act
        GenericResponse<List<BudgetRequestResponseDto>> response = userController.getBudgetsBySupplierId(supplierId);

        // Assert
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(1, response.getData().size());
        assertEquals("id3", response.getData().get(0).getId());
    }
}