package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.repository.SubjectRepository;
import zw.co.techtrendz.school.management.api.service.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject save(Subject subject) {
        return this.subjectRepository.save(subject);
    }

    @Override
    public List<Subject> saveAll(List<Subject> subjects) {
        return this.subjectRepository.saveAll(subjects);
    }

    @Override
    public Subject getById(Long id) {
        return this.subjectRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Subject> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.subjectRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Subject> getAllByExample(Subject subject, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (subject == null) {
            return null;
        } else {
            Example<Subject> example = Example.of(subject);
            return this.subjectRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
