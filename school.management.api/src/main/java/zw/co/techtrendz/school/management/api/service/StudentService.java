package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Student;

import java.util.List;

@Service
public interface StudentService {

    public Student save(Student student);

    public List<Student> saveAll(List<Student> students);

    public Student getById(Long id);

    public Page<Student> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Student> getAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Student> getAllByClassroomId(long classroomId, int page, int size, List<String> sortBy, Sort.Direction sortDir);
}
