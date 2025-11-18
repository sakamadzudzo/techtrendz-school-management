package zw.co.techtrendz.school.management.api.finance.service.impl;

import org.springframework.data.domain.Sort;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.finance.entity.Currency;
import zw.co.techtrendz.school.management.api.finance.repository.CurrencyRepository;
import zw.co.techtrendz.school.management.api.finance.service.CurrencyService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class CurrencyServiceImpl extends AbstractGenericServiceImpl<Currency, Long> implements CurrencyService {

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        super(currencyRepository);
    }

    @Override
    public List<YearDropdown> getAllForDropdown(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null || sortBy.isEmpty()) sortBy = new ArrayList<>(List.of("name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        return this.getAll(sortBy, sortDir)
                .stream()
                .map(currency -> new YearDropdown(currency.getId(), currency.getName(), null))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }
}
