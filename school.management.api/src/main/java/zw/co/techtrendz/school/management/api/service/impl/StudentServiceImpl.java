package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.service.StudentService;

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
        return this.studentRepository.saveAll(students);
    }

    @Override
    public Student getById(Long id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Student> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.studentRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Student> getAllByExample(Student student, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (student == null) {
            return null;
        } else {
            Example<Student> example = Example.of(student);
            return this.studentRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
