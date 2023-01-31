package Kata.Bank.account.domain.ports;

import Kata.Bank.account.domain.Account;
import Kata.Bank.account.domain.Operation;
import Kata.Bank.account.domain.OperationType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public interface AccountService {

    Account createAccount(Account account);
    Optional<Account> findAccount(long id);
    Account withdraw(BigDecimal amountToWithdraw , long idAccount) ;

    void deposit(BigDecimal amountToDeposit, long idAccount) ;

    BigDecimal checkBalance(OperationType operationType, BigDecimal amount);

    List<Operation> getWithdrawOperations(long idAccount);

    List<Operation> getDepositOperations(long idAccount);
}
