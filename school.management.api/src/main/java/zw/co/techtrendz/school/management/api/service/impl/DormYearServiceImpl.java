package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.repository.DormYearRepository;
import zw.co.techtrendz.school.management.api.service.DormYearService;

import java.util.List;

@Service
public class DormYearServiceImpl implements DormYearService {

    @Autowired
    private DormYearRepository dormYearRepository;

    @Override
    public DormYear save(DormYear dormYear) {
        return this.dormYearRepository.save(dormYear);
    }

    @Override
    public List<DormYear> saveAll(List<DormYear> dormYears) {
        return dormYears.stream().map(this::save).toList();
    }

    @Override
    public DormYear getById(Long id) {
        return this.dormYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<DormYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.dormYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<DormYear> getAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (dormYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<DormYear> example = Example.of(dormYear);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.dormYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
