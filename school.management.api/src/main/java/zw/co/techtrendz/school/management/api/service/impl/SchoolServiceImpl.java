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
import zw.co.techtrendz.school.management.api.entity.School;
import zw.co.techtrendz.school.management.api.repository.SchoolRepository;
import zw.co.techtrendz.school.management.api.service.SchoolService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School save(School school) {
        return this.schoolRepository.save(school);
    }

    @Override
    public List<School> saveAll(List<School> schools) {
        return schools.parallelStream().map(this::save).toList();
    }

    @Override
    public School getById(Long id) {
        Optional<School> school = this.schoolRepository.findById(id);
        if (school.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("School with id '%d' was not found", id));
        }
        return school.get();
    }

    @Override
    public Page<School> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.schoolRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public List<School> getAllList(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.schoolRepository.findAll(Sort.by(sortDir, sortFields));
    }

    @Override
    public Page<School> getAllByExample(School school, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (school == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<School> example = Example.of(school);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.schoolRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<School> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<School> spec = Specification.where(null);
            for (var field : School.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.schoolRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
