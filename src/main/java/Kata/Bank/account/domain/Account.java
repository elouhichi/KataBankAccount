package Kata.Bank.account.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    private Long id;
    private BigDecimal balance;
    private LocalDate openingDate;
    @OneToMany
    private List<Operation> operations;

}

