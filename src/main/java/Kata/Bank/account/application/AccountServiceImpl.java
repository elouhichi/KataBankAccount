package Kata.Bank.account.application;

import Kata.Bank.account.domain.Account;
import Kata.Bank.account.domain.EnoughException;
import Kata.Bank.account.domain.Operation;
import Kata.Bank.account.domain.OperationType;
import Kata.Bank.account.domain.ports.AccountRepository;
import Kata.Bank.account.domain.ports.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findAccount(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account withdraw(BigDecimal amountToWithdraw, long idAccount) throws EnoughException {
        Account account = findAccount(idAccount).get();
        if (this.ifEnough(amountToWithdraw, account)) {
            account.setBalance(account.getBalance().subtract(amountToWithdraw));
            account.getOperations().add(new Operation(1L,LocalDate.now(),amountToWithdraw, OperationType.WITHDRAW));
        } else {
            throw new EnoughException();
        }
        return accountRepository.save(account);
    }

    @Override
    public Account deposit(BigDecimal amountToDeposit, long idAccount) {
        Account account = findAccount(idAccount).get();
        if (this.ifCorrectAmountToDeposit(amountToDeposit)) {
             account.setBalance(account.getBalance().add(amountToDeposit));
             account.getOperations().add(new Operation(10L,LocalDate.now(),amountToDeposit, OperationType.DEPOSIT));
        } else {
            throw new IllegalArgumentException("Invalid amount to deposit");
        }
        return accountRepository.save(account);
    }

    @Override
    public List<Operation> getOperationsByType(long idAccount, OperationType type) {
        Account account = findAccount(idAccount).get();
        return  account.getOperations().stream().filter(elem -> elem.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * check if there is enough balance in the account
     *
     * @param amount to check
     * @return true if there is enough balance in the account
     */
    private boolean ifEnough(BigDecimal amount, Account account) {
        return amount != null && account.getBalance().compareTo(amount) >= 0;
    }

    /**
     * check if the amount to deposit is not null and is positive
     *
     * @param amount to check
     * @return true if the amount to deposit is not null and is positive
     */
    private boolean ifCorrectAmountToDeposit(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
