package zw.co.techtrendz.school.management.api.academic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.academic.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.academic.view.SubjectYearDto;

import java.util.List;

@Service
public interface SubjectYearService {

    public SubjectYear save(SubjectYear subjectYear);

    public List<SubjectYear> saveAll(List<SubjectYear> subjectYears);

    public SubjectYear getById(Long id);

    public Page<SubjectYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<SubjectYear> getAllByExample(SubjectYear subjectYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<SubjectYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public SubjectYearDto getViewById(Long id);

    public Page<SubjectYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<SubjectYearDto> getViewAllByExample(SubjectYear subjectYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<SubjectYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public List<YearDropdown> getAllSubjectYearForDropdown();
}
