package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.dto.ReportsFilters;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;

import java.util.List;

@Service
public interface ReportsDropdownsService {

    public ReportsFilters getAllDropdowns();

    public List<YearDropdown> getAllStudentsForDropdown();

    public List<YearDropdown> getAllTeachersForDropdown();

    public List<CourseworkDropdown> getAllCourseworksForDropdown();

    public List<YearDropdown> getAllClassroomsForDropdown();

    public List<YearDropdown> getAllSubjectsForDropdown();

    public List<YearDropdown> getAllExtraCurricularsForDropdown();

    public List<Integer> getAllCalendarYearsForDropdown();
}