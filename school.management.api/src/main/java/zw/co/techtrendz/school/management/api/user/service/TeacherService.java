package zw.co.techtrendz.school.management.api.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.user.entity.Teacher;
import zw.co.techtrendz.school.management.api.user.view.TeacherDto;

import java.util.List;

@Service
public interface TeacherService {

    public Teacher save(Teacher teacher);

    public List<Teacher> saveAll(List<Teacher> teachers);

    public Teacher getById(Long id);

    public Page<Teacher> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Teacher> getAllByExample(Teacher teacher, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Teacher> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public TeacherDto getViewById(Long id);

    public Page<TeacherDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<TeacherDto> getViewAllByExample(Teacher teacher, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<TeacherDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public List<YearDropdown> getAllTeachersForDropdown();
}
