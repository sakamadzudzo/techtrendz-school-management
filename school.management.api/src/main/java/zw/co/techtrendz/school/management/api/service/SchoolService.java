package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.School;

import java.util.List;

@Service
public interface SchoolService {

    public School save(School school);

    public List<School> saveAll(List<School> schools);

    public School getById(Long id);

    public Page<School> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir);

    public Page<School> getAllByExample(School school, int page, int size, String[] sortBy, Sort.Direction sortDir);
}
