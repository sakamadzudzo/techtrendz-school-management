package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.School;
import zw.co.techtrendz.school.management.api.repository.SchoolRepository;
import zw.co.techtrendz.school.management.api.service.SchoolService;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public School save(School school) {
        return this.schoolRepository.save(school);
    }

    @Override
    public List<School> saveAll(List<School> schools) {
        return schools.stream().map(this::save).toList();
    }

    @Override
    public School getById(Long id) {
        return this.schoolRepository.findById(id).orElse(null);
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
}
