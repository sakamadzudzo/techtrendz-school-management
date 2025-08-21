package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricular;

import java.util.List;

@Service
public interface ExtraCurricularService {

    public ExtraCurricular save(ExtraCurricular extraCurricular);

    public List<ExtraCurricular> saveAll(List<ExtraCurricular> extraCurriculars);

    public ExtraCurricular getById(Long id);

    public Page<ExtraCurricular> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public List<ExtraCurricular> getAllList(List<String> sortBy, Sort.Direction sortDir);

    public Page<ExtraCurricular> getAllByExample(ExtraCurricular extraCurricular, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ExtraCurricular> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);
}
