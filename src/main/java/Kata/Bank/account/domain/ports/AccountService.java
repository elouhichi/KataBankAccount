package Kata.Bank.account.domain.ports;

import Kata.Bank.account.domain.Account;
import Kata.Bank.account.domain.EnoughException;
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
    Account withdraw(BigDecimal amountToWithdraw , long idAccount) throws EnoughException;

    Account deposit(BigDecimal amountToDeposit, long idAccount) ;

    List<Operation> getOperationsByType(long idAccount, OperationType type) ;

    }
