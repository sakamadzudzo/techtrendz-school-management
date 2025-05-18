package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;

import java.util.List;

@Service
public interface ExtraCurricularYearService {

    public ExtraCurricularYear save(ExtraCurricularYear extraCurricularYear);

    public List<ExtraCurricularYear> saveAll(List<ExtraCurricularYear> extraCurricularYears);

    public ExtraCurricularYear getById(Long id);

    public Page<ExtraCurricularYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<ExtraCurricularYear> getAllByExample(ExtraCurricularYear extraCurricularYear, int page, int size, List<String> sortBy, Sort.Direction sortDir);
}
