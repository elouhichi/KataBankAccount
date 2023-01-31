package Kata.Bank.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Operation {
    public static final String DATE_AAAA_MM_JJ = "yyyy-MM-dd";

    private LocalDate date;

    private BigDecimal amount;

    private OperationType type;
}
