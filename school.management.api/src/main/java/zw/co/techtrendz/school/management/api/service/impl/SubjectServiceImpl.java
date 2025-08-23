package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.repository.SubjectRepository;
import zw.co.techtrendz.school.management.api.service.SubjectService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;
import java.util.Optional;

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
        return subjects.stream().map(this::save).toList();
    }

    @Override
    public Subject getById(Long id) {
        Optional<Subject> subject = this.subjectRepository.findById(id);
        if (subject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Subject with id '%d' was not found", id));
        }
        return subject.get();
    }

    @Override
    public Page<Subject> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.subjectRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public List<Subject> getAllList(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.subjectRepository.findAll(Sort.by(sortDir, sortFields));
    }

    @Override
    public Page<Subject> getAllByExample(Subject subject, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (subject == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Subject> example = Example.of(subject);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Subject> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Subject> spec = Specification.where(null);
            for (var field : Subject.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
