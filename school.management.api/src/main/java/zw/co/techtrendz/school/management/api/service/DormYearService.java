package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.view.DormYearDto;

import java.util.List;
import java.util.Map;

@Service
public interface DormYearService {

    public DormYear save(DormYear dormYear);

    public List<DormYear> saveAll(List<DormYear> dormYears);

    public DormYear getById(Long id);

    public Page<DormYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormYear> getAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public DormYearDto getViewById(Long id);

    public Page<DormYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormYearDto> getViewAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public List<YearDropdown> getAllDormYearForDropdown();
}
