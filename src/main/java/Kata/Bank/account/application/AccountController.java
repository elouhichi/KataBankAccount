package Kata.Bank.account.application;

import Kata.Bank.account.domain.Account;
import Kata.Bank.account.domain.EnoughException;
import Kata.Bank.account.domain.Operation;
import Kata.Bank.account.domain.OperationType;
import Kata.Bank.account.domain.ports.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    final AccountService accountService;

    @PostMapping("/create")
    public Account create(@RequestParam Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/deposit/{id}")
    public Account deposit(@RequestParam BigDecimal amount , @PathVariable long id) {
       return accountService.deposit(amount, id);
    }

    @PostMapping("/withdraw/{id}")
    public Account withdraw(@RequestParam BigDecimal amount, @PathVariable long id) throws EnoughException {
        return accountService.withdraw(amount , id);
    }

    @GetMapping("/operations/{id}/{type}")
    public List<Operation> getOperationsByTye(@PathVariable long id,@PathVariable OperationType type){
        return  accountService.getOperationsByType(id,type);
    }

}
