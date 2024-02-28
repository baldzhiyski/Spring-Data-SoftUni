package bg.softuni.springdatademo.services;

import bg.softuni.springdatademo.exceptions.EntityMissingException;
import bg.softuni.springdatademo.models.Account;
import bg.softuni.springdatademo.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withDrawMoney(BigDecimal amount, Long id) {
        Optional<Account> account =
                accountRepository.findById(id);

        if(account.isEmpty()){
            throw new EntityMissingException("Account does not exists !");
        }
        BigDecimal balance = account.get().getBalance();
        if(amount.compareTo(balance)>0){
            throw new RuntimeException("Cannot withdraw");
        }
       account.get().setBalance(balance.subtract(amount));

        this.accountRepository.save(account.get());

    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("No such Account !"));

        BigDecimal balance = account.getBalance().add(amount);
        account.setBalance(balance);

        accountRepository.save(account);
    }

}
