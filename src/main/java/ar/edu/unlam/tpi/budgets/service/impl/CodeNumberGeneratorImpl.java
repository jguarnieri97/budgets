package ar.edu.unlam.tpi.budgets.service.impl;

import ar.edu.unlam.tpi.budgets.service.CodeNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeNumberGeneratorImpl implements CodeNumberGenerator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generateCodeNumber() {
        String sql = "SELECT NEXT VALUE FOR budget_sequence";
        Long nextValue = jdbcTemplate.queryForObject(sql, Long.class);
        if (nextValue == null) {
            throw new RuntimeException("No se pudo obtener el siguiente valor de la secuencia");
        }
        return String.format("%05d", nextValue);
    }
}
