package zw.co.techtrendz.school.management.api.finance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.GenericService;
import zw.co.techtrendz.school.management.api.finance.dto.OutstandingBalance;
import zw.co.techtrendz.school.management.api.finance.entity.Fee;

import java.util.List;

@Service
public interface FeeService extends GenericService<Fee, Long> {

    public Page<Fee> getAllByStudentId(int page, int size, List<String> sortBy, Sort.Direction sortDir, long studentId);

    public Page<Fee> getAllByCalendarYear(int page, int size, List<String> sortBy, Sort.Direction sortDir, int calendarYear);

    public Page<Fee> getAllByStudentIdAndCalendarYear(int page, int size, List<String> sortBy, Sort.Direction sortDir, long studentId, int calendarYear);

    public List<Fee> getAllByStudentId(List<String> sortBy, Sort.Direction sortDir, long studentId);

    public List<Fee> getAllByCalendarYear(List<String> sortBy, Sort.Direction sortDir, int calendarYear);

    public List<Fee> getAllByStudentIdAndCalendarYear(List<String> sortBy, Sort.Direction sortDir, long studentId, int calendarYear);

    public OutstandingBalance getOutstandingBalance(long studentId);
}
