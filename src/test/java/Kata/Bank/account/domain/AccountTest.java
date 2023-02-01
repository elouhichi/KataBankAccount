package Kata.Bank.account.domain;

import Kata.Bank.account.application.AccountServiceImpl;
import Kata.Bank.account.domain.ports.AccountRepository;
import Kata.Bank.account.domain.ports.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AccountTest {

    Account account;

    @Mock
    AccountRepository accountRepository;
    AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl(accountRepository);

        this.account = new Account(123L,new BigDecimal(100), LocalDate.now(),new ArrayList<>());
        when(accountService.findAccount(account.getId())).thenReturn(Optional.of(account));

    }

    @Test
    public void withdrawInvalidAmount() throws EnoughException {
        Throwable exception = assertThrows(EnoughException.class, () -> accountService.withdraw(new BigDecimal(200), account.getId()));
        assertEquals("pas assez de solde pour retirer!", exception.getMessage());
    }

    @Test
    public void withdrawAmountEqualBalance() throws EnoughException {
        //When
        accountService.withdraw(new BigDecimal(100), account.getId());
        //Then
        assertEquals(new BigDecimal(0), this.account.getBalance());
        assertEquals(OperationType.WITHDRAW , this.account.getOperations().stream().findFirst().get().getType());

    }

    @Test
    public void withdrawValidAmount() throws EnoughException {
        //When
        accountService.withdraw(new BigDecimal(50), account.getId());

        //Then
        assertEquals(new BigDecimal(50), this.account.getBalance());
        assertEquals(OperationType.WITHDRAW , this.account.getOperations().stream().findFirst().get().getType());
    }

    @Test()
    public void depositValidAmount(){
        //When
        accountService.deposit(new BigDecimal(100), account.getId());

        //Then
        assertEquals(new BigDecimal(200), this.account.getBalance());
        assertEquals(OperationType.DEPOSIT , this.account.getOperations().get(0).getType());
    }

    @Test()
    public void depositInvalidAmount() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->  accountService.deposit(new BigDecimal(-50), account.getId()));
        assertEquals("Invalid amount to deposit", exception.getMessage());
    }

    @Test
    public void depositAndWithdrawHistory() throws EnoughException {
        //When
        accountService.deposit(new BigDecimal(50), account.getId());
        accountService.withdraw(new BigDecimal(50), account.getId());
        //Then
        assertEquals(OperationType.DEPOSIT, this.account.getOperations().get(0).getType());
        assertEquals(OperationType.WITHDRAW, this.account.getOperations().get(1).getType());
    }
}