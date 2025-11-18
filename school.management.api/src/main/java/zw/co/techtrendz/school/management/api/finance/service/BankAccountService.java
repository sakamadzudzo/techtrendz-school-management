package zw.co.techtrendz.school.management.api.finance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.GenericService;
import zw.co.techtrendz.school.management.api.finance.entity.BankAccount;
import zw.co.techtrendz.school.management.api.finance.entity.Currency;

import java.util.List;

@Service
public interface BankAccountService extends GenericService<BankAccount, Long> {

    public List<BankAccount> getAllByExample(List<String> sortBy, Sort.Direction sortDir, BankAccount bankAccount);

    public BankAccount getByAccountNumber(String accountNumber);

    public Page<BankAccount> getAllByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, BankAccount bankAccount);

    public List<BankAccount> getAll(List<String> sortBy, Sort.Direction sortDir);

    public Page<BankAccount> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);
}
