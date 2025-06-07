package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDetailDto;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import ar.edu.unlam.tpi.budgets.persistence.dao.BudgetDAO;
import ar.edu.unlam.tpi.budgets.utils.BudgetCreationResponseBuilder;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.budgets.utils.BudgetRequestEntityHelper;
import ar.edu.unlam.tpi.budgets.utils.BudgetUpdatedDataRequestHelper;
import ar.edu.unlam.tpi.budgets.utils.BudgetValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceImplTest {

    @Mock
    private BudgetDAO budgetDAO;

    @Mock
    private BudgetCreationResponseBuilder budgetCreationResponseBuilder;

    @Mock
    private BudgetValidator budgetValidator;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Test
    public void givenValidRequest_whenCreate_thenReturnResponse() {
        // Arrange
        BudgetCreationRequestDto request = BudgetDataHelper.createValidRequest(
                1L, "Juan Pérez",
                List.of(
                        BudgetDataHelper.supplier(10L, "Proveedor A"),
                        BudgetDataHelper.supplier(11L, "Proveedor B")));

        BudgetRequestEntity entity = BudgetDataHelper.createBudgetRequestEntity("abc123", 1L, "Juan Pérez");
        BudgetCreationResponseDto expectedResponse = BudgetCreationResponseDto.builder().id("abc123").build();

        when(budgetDAO.save(any(BudgetRequestEntity.class))).thenReturn(entity);
        when(budgetCreationResponseBuilder.build(entity)).thenReturn(expectedResponse);

        // Act
        BudgetCreationResponseDto result = budgetService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals("abc123", result.getId());
    }

    @Test
    public void givenApplicantId_whenGetBudgets_thenReturnList() {
        // Arrange
        List<BudgetRequestEntity> entities = List.of(
                BudgetDataHelper.createBudgetRequestEntity("id1", 1L, "Juan"),
                BudgetDataHelper.createBudgetRequestEntity("id2", 1L, "Juan"));

        when(budgetDAO.findByApplicantId(1L)).thenReturn(entities);

        // Act
        List<BudgetResponseDto> result = budgetService.getBudgetsByApplicantId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void givenBudgetId_whenGetDetail_thenReturnBudgetResponseDto() {
        // Arrange
        String id = "abc123";
        BudgetRequestEntity entity = BudgetDataHelper.createBudgetRequestEntity(id, 1L, "Logibyte");

        when(budgetDAO.findById(id)).thenReturn(entity);

        // Act
        BudgetResponseDetailDto result = budgetService.getBudgetDetailById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void givenValidUpdateRequest_whenUpdate_thenUpdateBudget() {
        // Arrange
        String budgetId = "abc123";
        Long providerId = 1L;
        BudgetUpdateDataRequestDto updateRequest = BudgetUpdatedDataRequestHelper.getBudgetUpdateDataRequestDto();

        // Create a budget with multiple providers using ArrayList for mutability
        List<Budget> budgets = BudgetDataHelper.getListOfBudgets();

        BudgetRequestEntity existingBudget = BudgetRequestEntityHelper.getBudgetRequestEntity(budgetId, budgets);

        when(budgetDAO.findById(budgetId)).thenReturn(existingBudget);
        when(budgetDAO.save(any(BudgetRequestEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        budgetService.update(budgetId, providerId, updateRequest);

        // Assert
        verify(budgetDAO).findById(budgetId);
        verify(budgetDAO).save(argThat(savedBudget -> {
            // Verify only one budget remains (the updated one)
            assertEquals(2, savedBudget.getBudgets().size());
            
            // Verify the updated budget has the correct values
            Budget updatedBudget = savedBudget.getBudgets().get(0);
            assertEquals(providerId, updatedBudget.getSupplierId());
            assertEquals(BudgetState.ACCEPTED, updatedBudget.getState());
            assertEquals(updateRequest.getPrice(), updatedBudget.getPrice());
            assertEquals(updateRequest.getDaysCount(), updatedBudget.getDaysCount());
            assertEquals(updateRequest.getWorkerCount(), updatedBudget.getWorkerCount());
            assertEquals(updateRequest.getDetail(), updatedBudget.getDetail());
            
            return true;
        }));
    }

    @Test
    public void givenNonExistentProvider_whenUpdate_thenThrowException() {
        // Arrange
        String budgetId = "abc123";
        Long nonExistentProviderId = 999L;
        BudgetUpdateDataRequestDto updateRequest = BudgetUpdatedDataRequestHelper.getBudgetUpdateDataRequestDto();

        // Create a budget with a single provider using ArrayList for mutability
        List<Budget> budgets = BudgetDataHelper.getOnlyBudget();

        BudgetRequestEntity existingBudget = BudgetRequestEntityHelper.getBudgetRequestEntity(budgetId, budgets);

        when(budgetDAO.findById(budgetId)).thenReturn(existingBudget);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            budgetService.update(budgetId, nonExistentProviderId, updateRequest)
        );
        assertEquals("No se encontró el proveedor con ID: " + nonExistentProviderId, exception.getMessage());
    }

    @Test
    void givenValidBudgetIdAndRequest_whenFinalizeBudgetRequest_thenValidatorAndSaveAreCalled() {
        // Given
        String budgetId = "budget123";
        BudgetRequestEntity mockEntity = BudgetRequestEntity.builder().build();
        BudgetFinalizeRequestDto requestDto = BudgetFinalizeRequestDto.builder().build();

        given(budgetDAO.findById(budgetId)).willReturn(mockEntity);

        // When
        budgetService.finalizeBudgetRequest(budgetId, requestDto);

        // Then
        then(budgetValidator).should().validateSupplierHired(mockEntity, requestDto);
        then(budgetDAO).should().save(mockEntity);
    }

    @Test
    void givenExistingBudgetId_whenFinalizeRequestOnly_thenStateIsFinalizedAndSaved() {
        // Given
        String budgetId = "budget456";
        BudgetRequestEntity mockEntity = BudgetRequestEntity.builder().build();
        given(budgetDAO.findById(budgetId)).willReturn(mockEntity);

        // When
        budgetService.finalizeRequestOnly(budgetId);

        // Then
        assertEquals(BudgetState.FINALIZED, mockEntity.getState());
        then(budgetDAO).should().save(mockEntity);
    }


    @Test
void givenValidId_whenFinalizeRequestOnly_thenStateIsUpdatedToFinalized() {
    // Given
    String budgetId = "123";
    BudgetRequestEntity entity = BudgetRequestEntity.builder().id(budgetId).state(BudgetState.INITIATED).build();

    when(budgetDAO.findById(budgetId)).thenReturn(entity);

    // When
    budgetService.finalizeRequestOnly(budgetId);

    // Then
    assertEquals(BudgetState.FINALIZED, entity.getState());
    verify(budgetDAO).findById(budgetId);
    verify(budgetDAO).save(entity);
}

}
