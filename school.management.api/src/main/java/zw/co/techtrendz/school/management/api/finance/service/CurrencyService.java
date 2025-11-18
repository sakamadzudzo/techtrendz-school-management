package zw.co.techtrendz.school.management.api.finance.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.common.service.GenericService;
import zw.co.techtrendz.school.management.api.finance.entity.Currency;

import java.util.List;

@Service
public interface CurrencyService extends GenericService<Currency, Long> {

    public List<YearDropdown> getAllForDropdown(List<String> sortBy, Sort.Direction sortDir);
}
