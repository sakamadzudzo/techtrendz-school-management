package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularYearRepository;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularYearService;

import java.util.List;

@Service
public class ExtraCurricularYearServiceImpl implements ExtraCurricularYearService {

    @Autowired
    private ExtraCurricularYearRepository extraCurricularYearRepository;

    @Override
    public ExtraCurricularYear save(ExtraCurricularYear extraCurricularYear) {
        return this.extraCurricularYearRepository.save(extraCurricularYear);
    }

    @Override
    public List<ExtraCurricularYear> saveAll(List<ExtraCurricularYear> extraCurricularYears) {
        return this.extraCurricularYearRepository.saveAll(extraCurricularYears);
    }

    @Override
    public ExtraCurricularYear getById(Long id) {
        return this.extraCurricularYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ExtraCurricularYear> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.extraCurricularYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<ExtraCurricularYear> getAllByExample(ExtraCurricularYear extraCurricularYear, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (extraCurricularYear == null) {
            return null;
        } else {
            Example<ExtraCurricularYear> example = Example.of(extraCurricularYear);
            return this.extraCurricularYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
