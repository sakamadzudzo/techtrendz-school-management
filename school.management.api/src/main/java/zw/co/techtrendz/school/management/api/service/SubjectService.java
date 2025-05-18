package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Subject;

import java.util.List;

@Service
public interface SubjectService {

    public Subject save(Subject subject);

    public List<Subject> saveAll(List<Subject> subjects);

    public Subject getById(Long id);

    public Page<Subject> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Subject> getAllByExample(Subject subject, int page, int size, List<String> sortBy, Sort.Direction sortDir);
}
