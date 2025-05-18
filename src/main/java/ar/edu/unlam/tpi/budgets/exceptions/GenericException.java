package ar.edu.unlam.tpi.budgets.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GenericException extends RuntimeException {

    private Integer code;
    private String detail;
    private String message;

    public GenericException(Integer code, String message, String detail) {
        super(message);
        this.message = message;
        this.code = code;
        this.detail = detail;
    }

}
