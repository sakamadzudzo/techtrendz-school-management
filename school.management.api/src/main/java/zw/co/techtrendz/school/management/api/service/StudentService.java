package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.view.StudentDto;

import java.util.List;

@Service
public interface StudentService {

    public Student save(Student student);

    public List<Student> saveAll(List<Student> students);

    public Student getById(Long id);

    public Page<Student> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Student> getAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Student> getAllByClassroomId(long classroomId, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Student> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public StudentDto getViewById(Long id);

    public Page<StudentDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<StudentDto> getViewAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<StudentDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public List<YearDropdown> getAllStudentsForDropdown();
}
