package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Teacher;
import zw.co.techtrendz.school.management.api.repository.TeacherRepository;
import zw.co.techtrendz.school.management.api.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        return this.teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> saveAll(List<Teacher> teachers) {
        return this.teacherRepository.saveAll(teachers);
    }

    @Override
    public Teacher getById(Long id) {
        return this.teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Teacher> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.teacherRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Teacher> getAllByExample(Teacher teacher, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (teacher == null) {
            return null;
        } else {
            Example<Teacher> example = Example.of(teacher);
            return this.teacherRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
