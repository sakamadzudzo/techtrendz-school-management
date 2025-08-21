package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;

import java.util.List;
import java.util.Map;

@Service
public interface ClassroomYearService {

    public ClassroomYear save(ClassroomYear classroomYear);

    public List<ClassroomYear> saveAll(List<ClassroomYear> classroomYears);

    public ClassroomYear getById(Long id);

    public Page<ClassroomYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ClassroomYear> getAllByExample(ClassroomYear classroomYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ClassroomYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public ClassroomYearDto getViewById(Long id);

    public Page<ClassroomYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ClassroomYearDto> getViewAllByExample(ClassroomYear classroomYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ClassroomYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public List<YearDropdown> getAllClassroomYearForDropdown();
}
