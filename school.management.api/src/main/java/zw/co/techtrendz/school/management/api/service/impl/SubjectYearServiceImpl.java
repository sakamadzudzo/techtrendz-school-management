package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.repository.SubjectYearRepository;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;

@Service
public class SubjectYearServiceImpl implements SubjectYearService {

    @Autowired
    private SubjectYearRepository subjectYearRepository;

    @Override
    public SubjectYear save(SubjectYear subjectYear) {
        return this.subjectYearRepository.save(subjectYear);
    }

    @Override
    public List<SubjectYear> saveAll(List<SubjectYear> subjectYears) {
        return subjectYears.stream().map(this::save).toList();
    }

    @Override
    public SubjectYear getById(Long id) {
        return this.subjectYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<SubjectYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.subjectYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<SubjectYear> getAllByExample(SubjectYear subjectYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (subjectYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<SubjectYear> example = Example.of(subjectYear);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<SubjectYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<SubjectYear> spec = Specification.where(null);
            for (var field : SubjectYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
