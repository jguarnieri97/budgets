package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import ar.edu.unlam.tpi.budgets.persistence.dao.BudgetDAO;
import ar.edu.unlam.tpi.budgets.utils.BudgetCreationResponseBuilder;
import ar.edu.unlam.tpi.budgets.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.budgets.utils.BudgetValidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @DisplayName("Crear presupuesto exitosamente")
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
    @DisplayName("Buscar presupuestos por solicitante ID")
    public void givenApplicantId_whenGetBudgets_thenReturnList() {
        // Arrange
        List<BudgetRequestEntity> entities = List.of(
                BudgetDataHelper.createBudgetRequestEntity("id1", 1L, "Juan"),
                BudgetDataHelper.createBudgetRequestEntity("id2", 1L, "Juan"));

        when(budgetDAO.findByApplicantId(1L)).thenReturn(entities);

        // Act
        List<BudgetRequestResponseDto> result = budgetService.getBudgetsByApplicantId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Buscar presupuestos por proveedor ID")
    public void givenSupplierId_whenGetBudgets_thenReturnList() {
        // Arrange
        List<BudgetRequestEntity> entities = List.of(
                BudgetDataHelper.createBudgetRequestEntity("id1", 1L, "Juan"));

        when(budgetDAO.findBySupplierId(100L)).thenReturn(entities);

        // Act
        List<BudgetRequestResponseDto> result = budgetService.getBudgetsBySupplierId(100L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar detalle de presupuesto por ID")
    public void givenBudgetId_whenGetDetail_thenReturnBudgetResponseDto() {
        // Arrange
        String id = "abc123";
        BudgetRequestEntity entity = BudgetDataHelper.createBudgetRequestEntity(id, 1L, "Logibyte");

        when(budgetDAO.findById(id)).thenReturn(entity);

        // Act
        BudgetResponseDto result = budgetService.getBudgetDetailById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Actualizar presupuesto exitosamente a FINALIZED")
    public void givenValidUpdateRequest_whenUpdate_thenValidatorAndSaveAreCalled() {
        // Arrange
        String budgetId = "budget123";
        BudgetUpdateRequestDto updateRequest = BudgetUpdateRequestDto.builder()
                .state(BudgetState.FINALIZED)
                .supplierHired(1L)
                .build();

        BudgetRequestEntity entity = BudgetDataHelper.createBudgetRequestWithBudgets(budgetId,
                List.of(BudgetDataHelper.createBudget(1L, false, BudgetState.PENDING)));
        entity.setState(BudgetState.INITIATED);

        when(budgetDAO.findById(budgetId)).thenReturn(entity);

        // Act
        budgetService.update(budgetId, updateRequest);

        // Assert
        verify(budgetValidator, times(1)).validateAndApplyStateTransition(entity, updateRequest);
        verify(budgetDAO, times(1)).save(entity);
    }

    @Test
    @DisplayName("Lanzar NotFoundException si el presupuesto no existe al actualizar")
    public void givenInvalidBudgetId_whenUpdate_thenThrowNotFoundException() {
        // Arrange
        String invalidId = "inexistente-999";
        BudgetUpdateRequestDto request = BudgetUpdateRequestDto.builder()
                .state(BudgetState.FINALIZED)
                .supplierHired(101L)
                .build();
    
        when(budgetDAO.findById(invalidId)).thenThrow(new NotFoundException("Presupuesto no encontrado"));
    
        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                budgetService.update(invalidId, request));
    
        assertEquals("Presupuesto no encontrado", exception.getDetail());
    }
}