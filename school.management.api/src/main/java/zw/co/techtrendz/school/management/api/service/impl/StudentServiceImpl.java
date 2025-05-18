package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.service.StudentService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        return this.studentRepository.save(student);
    }

    @Override
    public List<Student> saveAll(List<Student> students) {
        return students.stream().map(this::save).toList();
    }

    @Override
    public Student getById(Long id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Student> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.studentRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Student> getAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (student == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Student> example = Example.of(student);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.studentRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Student> getAllByClassroomId(long classroomId, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        Specification<Student> spec = Specification.where(
                SpecificationUtils.<Student>exactMatch("classroom.id", classroomId)
        );

        return this.studentRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }
}
