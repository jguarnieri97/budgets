package ar.edu.unlam.tpi.budgets.controller.handler;

import ar.edu.unlam.tpi.budgets.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.budgets.exceptions.InternalException;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BudgetExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseDto> handleEmptyException(InternalException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponseDto.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponseDto.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

}

