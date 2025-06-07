package ar.edu.unlam.tpi.budgets.persistence.dao.impl;

import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
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
        String id = "123";
        BudgetRequestEntity expected = BudgetRequestEntity.builder().id(id).build();
        given(repository.findById(id)).willReturn(Optional.of(expected));

        // When
        BudgetRequestEntity result = budgetDAO.findById(id);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void givenNonexistentId_whenFindById_thenThrowNotFoundException() {
        // Given
        String id = "999";
        given(repository.findById(id)).willReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundException.class, () -> budgetDAO.findById(id));
    }

    @Test
    void givenRepositoryThrowsException_whenFindById_thenThrowInternalException() {
        // Given
        String id = "error-id";
        given(repository.findById(id)).willThrow(new RuntimeException("Database down"));

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.findById(id));
    }

    @Test
    void givenValidEntity_whenSave_thenReturnSavedEntity() {
        // Given
        BudgetRequestEntity entity = BudgetRequestEntity.builder().id("123").build();
        given(repository.save(entity)).willReturn(entity);

        // When
        BudgetRequestEntity result = budgetDAO.save(entity);

        // Then
        assertEquals(entity, result);
    }

    @Test
    void givenRepositoryThrowsException_whenSave_thenThrowInternalException() {
        // Given
        BudgetRequestEntity entity = BudgetRequestEntity.builder().id("123").build();
        given(repository.save(entity)).willThrow(new RuntimeException("Write failed"));

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.save(entity));
    }

    @Test
    void givenValidId_whenDelete_thenNoExceptionThrown() {
        // Given
        String id = "123";

        // When / Then
        assertDoesNotThrow(() -> budgetDAO.delete(id));
        then(repository).should().deleteById(id);
    }

    @Test
    void givenRepositoryThrowsException_whenDelete_thenThrowInternalException() {
        // Given
        String id = "fail-id";
        willThrow(new RuntimeException("Delete failed")).given(repository).deleteById(id);

        // When / Then
        assertThrows(InternalException.class, () -> budgetDAO.delete(id));
    }

    @Test
    void givenValidApplicantId_whenFindByApplicantId_thenReturnList() {
        // Given
        Long applicantId = 1L;
        List<BudgetRequestEntity> expected = List.of(BudgetRequestEntity.builder().applicantId(applicantId).build());
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
