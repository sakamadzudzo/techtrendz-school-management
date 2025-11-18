package zw.co.techtrendz.school.management.api.finance.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.finance.entity.BankAccount;
import zw.co.techtrendz.school.management.api.finance.querydsl.BankAccountQueryService;
import zw.co.techtrendz.school.management.api.finance.repository.BankAccountRepository;
import zw.co.techtrendz.school.management.api.finance.service.BankAccountService;

import java.util.List;

@Service
public class BankAccountServiceImpl extends AbstractGenericServiceImpl<BankAccount, Long> implements BankAccountService {

    private final BankAccountQueryService bankAccountQueryService;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountQueryService bankAccountQueryService) {
        super(bankAccountRepository);
        this.bankAccountQueryService = bankAccountQueryService;
    }


    @Override
    public List<BankAccount> getAllByExample(List<String> sortBy, Sort.Direction sortDir, BankAccount bankAccount) {
        if (bankAccount == null) return null;

        Example<BankAccount> example = Example.of(bankAccount);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, BankAccount.class, "bankAccount");

        return this.bankAccountQueryService.getByPredicate(sortBy, sortDir, predicate);
    }

    @Override
    public BankAccount getByAccountNumber(String accountNumber) {
        if (accountNumber == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number is null");

        BooleanExpression predicate = null;
        predicate = AppUtils.getBooleanExpression(accountNumber, predicate, BankAccount.class, "bankAccount", null);

        BankAccount bankAccount = this.bankAccountQueryService.getByPredicate(predicate);

        if (bankAccount == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Bank account with account number '%s' was not found", accountNumber));

        return bankAccount;
    }

    @Override
    public Page<BankAccount> getAllByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, BankAccount bankAccount) {
        if (bankAccount == null) return this.getAll(page, size, sortBy, sortDir);

        Example<BankAccount> example = Example.of(bankAccount);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, BankAccount.class, "bankAccount");

        return this.bankAccountQueryService.getByPredicate(page, size, sortBy, sortDir, predicate);
    }

    @Override
    public List<BankAccount> getAll(List<String> sortBy, Sort.Direction sortDir) {
        return this.bankAccountQueryService.getAll(sortBy, sortDir);
    }

    @Override
    public Page<BankAccount> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        return this.bankAccountQueryService.getAll(page, size, sortBy, sortDir);
    }
}
