package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.repository.SubjectYearRepository;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;

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
}
