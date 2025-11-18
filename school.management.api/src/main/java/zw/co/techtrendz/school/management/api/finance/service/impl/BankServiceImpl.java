package zw.co.techtrendz.school.management.api.finance.service.impl;

import org.springframework.data.domain.Sort;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.finance.entity.Bank;
import zw.co.techtrendz.school.management.api.finance.querydsl.BankQueryService;
import zw.co.techtrendz.school.management.api.finance.repository.BankRepository;
import zw.co.techtrendz.school.management.api.finance.service.BankService;

import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl extends AbstractGenericServiceImpl<Bank, Long> implements BankService {

    private final BankQueryService bankQueryService;

    public BankServiceImpl(BankRepository bankRepository, BankQueryService bankQueryService) {
        super(bankRepository);
        this.bankQueryService = bankQueryService;
    }

    @Override
    public List<YearDropdown> getAllForDropdown(List<String> sortBy, Sort.Direction sortDir) {
        if (sortDir == null) sortDir = Sort.Direction.DESC;
        if (sortBy == null) sortBy = new ArrayList<>(List.of("name"));
        return this.bankQueryService.getAllBankDropdown(sortBy, sortDir);
    }
}
