package ar.edu.unlam.tpi.budgets.beans;

import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetDetail;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import ar.edu.unlam.tpi.budgets.persistence.repository.BudgetRepository;
import ar.edu.unlam.tpi.budgets.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class BudgetDataInitializer implements CommandLineRunner {

    private final BudgetRepository budgetRepository;

    @Override
    public void run(String... args) throws Exception {

        BudgetRequest budget1 = BudgetRequest.builder()
                .id("6826712de38b3e1cafe67291")
                .budgetNumber(9165074002368021221L)
                .applicantId(8L)
                .createdAt(DateTimeUtils.toLocalDateTime("2025-05-15T22:56:45"))
                .state(BudgetState.PENDING)
                .files(Arrays.asList("file1.pdf", "file2.docx"))
                .budgetDetail(BudgetDetail.builder()
                        .isUrgent(true)
                        .estimatedDate(DateTimeUtils.toLocalDateTime("2024-01-10T03:00:00"))
                        .workResume("Pintura de interiores")
                        .workDetail("Se necesita pintar las paredes y techos de las oficinas principales.")
                        .build())
                .budgets(Arrays.asList(
                        Budget.builder().build(),
                        Budget.builder().build()
                ))
                .build();

        BudgetRequest budget2 = BudgetRequest.builder()
                .id("6826712de38b3e1cafe67292")
                .budgetNumber(9165074002368021222L)
                .applicantId(15L)
                .createdAt(DateTimeUtils.toLocalDateTime("2025-05-15T23:00:05"))
                .state(BudgetState.PENDING)
                .files(Arrays.asList("file3.pdf", "file4.docx"))
                .budgetDetail(BudgetDetail.builder()
                        .isUrgent(false)
                        .estimatedDate(DateTimeUtils.toLocalDateTime("2024-01-10T03:10:00"))
                        .workResume("Reparación de techos")
                        .workDetail("Se necesita reparar las goteras en los techos del edificio principal.")
                        .build())
                .budgets(Arrays.asList(
                        Budget.builder().build(),
                        Budget.builder().build()
                ))
                .build();

        BudgetRequest budget3 = BudgetRequest.builder()
                .id("6826712de38b3e1cafe67293")
                .budgetNumber(9165074002368021223L)
                .applicantId(20L)
                .createdAt(DateTimeUtils.toLocalDateTime("2025-05-15T23:03:25"))
                .state(BudgetState.PENDING)
                .files(Arrays.asList("file5.pdf", "file6.docx"))
                .budgetDetail(BudgetDetail.builder()
                        .isUrgent(true)
                        .estimatedDate(DateTimeUtils.toLocalDateTime("2024-01-10T03:20:00"))
                        .workResume("Instalación eléctrica")
                        .workDetail("Se necesita instalar un sistema eléctrico en el nuevo edificio.")
                        .build())
                .budgets(Arrays.asList(
                        Budget.builder().build(),
                        Budget.builder().build()
                ))
                .build();

        budgetRepository.saveAll(Arrays.asList(budget1, budget2, budget3));

        log.info("Datos de presupuestos inicializados correctamente");
    }
}
