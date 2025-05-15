package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Teacher;

import java.util.List;

@Service
public interface TeacherService {

    public Teacher save(Teacher teacher);

    public List<Teacher> saveAll(List<Teacher> teachers);

    public Teacher getById(Long id);

    public Page<Teacher> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir);

    public Page<Teacher> getAllByExample(Teacher teacher, int page, int size, String[] sortBy, Sort.Direction sortDir);
}
