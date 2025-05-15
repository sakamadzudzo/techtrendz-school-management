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
        return this.schoolRepository.saveAll(schools);
    }

    @Override
    public School getById(Long id) {
        return this.schoolRepository.findById(id).orElse(null);
    }

    @Override
    public Page<School> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.schoolRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<School> getAllByExample(School school, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (school == null) {
            return null;
        } else {
            Example<School> example = Example.of(school);
            return this.schoolRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
