package ar.edu.unlam.tpi.budgets.persistence.dao.impl;

import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.utils.BudgetRequestEntityHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetDAOImplTest {

    @Mock
    private BudgetRepository repository;

    @InjectMocks
    private BudgetDAOImpl budgetDAO;

    public static final Long BUDGET_ID = 1L;


    @Test
    void givenRepositoryReturnsList_whenFindAll_thenReturnSameList() {
        // Given
        List<BudgetRequestEntity> expected = List.of(BudgetRequestEntity.builder().build());
        given(repository.findAll()).willReturn(expected);

        // When
        List<BudgetRequestEntity> result = budgetDAO.findAll();

        // Then
        assertEquals(expected, result);
    }

    @Test
    void givenRepositoryThrowsException_whenFindAll_thenThrowInternalException() {
        // Given
        given(repository.findAll()).willThrow(new RuntimeException("DB error"));

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.findAll());
    }

    @Test
    void givenValidId_whenFindById_thenReturnEntity() {
        // Given
        BudgetRequestEntity expected = BudgetRequestEntity.builder().id(BUDGET_ID).build();
        given(repository.findById(BUDGET_ID)).willReturn(Optional.of(expected));

        // When
        BudgetRequestEntity result = budgetDAO.findById(BUDGET_ID);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void givenNonexistentId_whenFindById_thenThrowNotFoundException() {
        // Given
        given(repository.findById(BUDGET_ID)).willReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundException.class, () -> budgetDAO.findById(BUDGET_ID));
    }

    @Test
    void givenRepositoryThrowsException_whenFindById_thenThrowInternalException() {
        // Given
        given(repository.findById(BUDGET_ID)).willThrow(new RuntimeException("Database down"));

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.findById(BUDGET_ID));
    }

    @Test
    void givenValidEntity_whenSave_thenReturnSavedEntity() {
        // Given
        BudgetRequestEntity entity = BudgetRequestEntity.builder().id(BUDGET_ID).build();
        given(repository.save(entity)).willReturn(entity);

        // When
        BudgetRequestEntity result = budgetDAO.save(entity);

        // Then
        assertEquals(entity, result);
    }

    @Test
    void givenRepositoryThrowsException_whenSave_thenThrowInternalException() {
        // Given
        BudgetRequestEntity entity = BudgetRequestEntity.builder().id(BUDGET_ID).build();
        given(repository.save(entity)).willThrow(new RuntimeException("Write failed"));

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.save(entity));
    }

    @Test
    void givenValidId_whenDelete_thenNoExceptionThrown() {
        // When / Then
        assertDoesNotThrow(() -> budgetDAO.delete(BUDGET_ID));
        then(repository).should().deleteById(BUDGET_ID);
    }

    @Test
    void givenRepositoryThrowsException_whenDelete_thenThrowInternalException() {
        willThrow(new RuntimeException("Delete failed")).given(repository).deleteById(BUDGET_ID);

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.delete(BUDGET_ID));
    }

    @Test
    void givenValidApplicantId_whenFindByApplicantId_thenReturnList() {
        // Given
        Long applicantId = 1L;
        List<BudgetRequestEntity> expected = List.of(BudgetRequestEntityHelper.getBudgetRequestEntity(BUDGET_ID));
        given(repository.findByApplicantId(applicantId)).willReturn(expected);

        // When
        List<BudgetRequestEntity> result = budgetDAO.findByApplicantId(applicantId);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void givenValidSupplierId_whenFindBySupplierId_thenReturnList() {
        // Given
        Long supplierId = 2L;
        List<BudgetRequestEntity> expected = List.of(BudgetRequestEntity.builder().build());
        given(repository.findBySupplierId(supplierId)).willReturn(expected);

        // When
        List<BudgetRequestEntity> result = budgetDAO.findBySupplierId(supplierId);

        // Then
        assertEquals(expected, result);
    }

}
