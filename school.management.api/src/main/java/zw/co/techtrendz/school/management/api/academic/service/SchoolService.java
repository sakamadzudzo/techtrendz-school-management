package zw.co.techtrendz.school.management.api.academic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.entity.School;

import java.util.List;

@Service
public interface SchoolService {

    public School save(School school);

    public List<School> saveAll(List<School> schools);

    public School getById(Long id);

    public Page<School> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public List<School> getAllList(List<String> sortBy, Sort.Direction sortDir);

    public Page<School> getAllByExample(School school, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<School> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);
}
