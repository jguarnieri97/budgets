package ar.edu.unlam.tpi.budgets.controller.handler;

import ar.edu.unlam.tpi.budgets.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetExceptionHandlerTest {

    private final BudgetExceptionHandler handler = new BudgetExceptionHandler();

    @Test
    void givenInternalException_whenHandled_thenReturnsProperErrorResponse() {
        // given
        String message = "INTERNAL_SERVER_ERROR";
        String detail = "Detalle técnico";
        Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        InternalException ex = new InternalException(detail);

        // when
        ResponseEntity<ErrorResponseDto> response = handler.handleEmptyException(ex);

        // then
        assertEquals(code, response.getStatusCodeValue());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(detail, response.getBody().getDetail());
        assertEquals(code, response.getBody().getCode());
    }

    @Test
    void givenNotFoundException_whenHandled_thenReturnsProperErrorResponse() {
        // given
        String message = "NOT_FOUND_EXCEPTION";
        String detail = "El recurso no existe";
        Integer code = HttpStatus.NOT_FOUND.value();
        NotFoundException ex = new NotFoundException(detail);

        // when
        ResponseEntity<ErrorResponseDto> response = handler.handleNotFoundException(ex);

        // then
        assertEquals(code, response.getStatusCodeValue());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(detail, response.getBody().getDetail());
        assertEquals(code, response.getBody().getCode());
    }

    @Test
    void givenMethodArgumentNotValidException_whenHandled_thenReturnsValidationErrors() {
        // given
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Campo inválido");
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // when
        ResponseEntity<ErrorResponseDto> response = handler.handleValidationExceptions(ex);

        // then
        assertEquals(Constants.STATUS_INTERNAL, response.getStatusCodeValue());
        assertEquals(Constants.INTERNAL_ERROR, response.getBody().getMessage());
        assertTrue(response.getBody().getDetail().contains("fieldName"));
        assertTrue(response.getBody().getDetail().contains("Campo inválido"));
    }
}