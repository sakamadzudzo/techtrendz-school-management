package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.DormYear;

import java.util.List;

@Service
public interface DormYearService {

    public DormYear save(DormYear dormYear);

    public List<DormYear> saveAll(List<DormYear> dormYears);

    public DormYear getById(Long id);

    public Page<DormYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormYear> getAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);
}
