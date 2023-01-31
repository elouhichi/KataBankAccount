package Kata.Bank.account.domain;

import Kata.Bank.account.domain.ports.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;s

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account account;

    @InjectMocks
    AccountService accountService;

    @BeforeEach
    void setUp() {
        this.account = new Account(123L,new BigDecimal(100), LocalDate.now(),new ArrayList<>());
    }

    @Test
    public void withdrawInvalidAmount() {
        //When
        accountService.withdraw(new BigDecimal(200), account.getId());
        //then
        assertThrows(EnoughException.class, () -> {
            Integer.parseInt("1");
        }, "EnoughException error was expected");
    }

    @Test
    public void withdrawAmountEqualBalance() {
        //When
        accountService.withdraw(new BigDecimal(100), account.getId());
        //Then
        assertEquals(new BigDecimal(0), this.account.getBalance());
        assertEquals(OperationType.WITHDRAW , this.account.getOperations().get(0).getType());

    }

    @Test
    public void withdrawValidAmount() {
        //When
        accountService.withdraw(new BigDecimal(50), account.getId());

        //Then
        assertEquals(new BigDecimal(50), this.account.getBalance());
        assertEquals(OperationType.WITHDRAW , this.account.getOperations().get(0).getType());


    }

    @Test()
    public void depositValidAmount(){
        //When
        accountService.deposit(new BigDecimal(100), account.getId());

        //Then
        assertEquals(new BigDecimal(200), this.account.getBalance());
        assertEquals(OperationType.DEPOSIT , this.account.getOperations().get(0).getType());
    }

    @Test
    public void depositInvalidAmount() {
        //When
        accountService.deposit(new BigDecimal(-50), account.getId());
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.parseInt("1");
        }, "IllegalArgumentException error was expected");
    }

    @Test
    public void depositAndWithdrawHistory() {
        //When
        accountService.deposit(new BigDecimal(50), account.getId());
        accountService.withdraw(new BigDecimal(50), account.getId());
        //Then
        assertEquals(OperationType.DEPOSIT, this.account.getOperations().get(0).getType());
        assertEquals(OperationType.WITHDRAW, this.account.getOperations().get(1).getType());

    }

}