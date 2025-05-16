package ar.edu.unlam.tpi.budgets.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GenericException {

    private static final String MESSAGE = "NOT_FOUND_EXCEPTION";
    public NotFoundException(String detail) {
        super(HttpStatus.NOT_FOUND.value(), MESSAGE, detail);
    }
}