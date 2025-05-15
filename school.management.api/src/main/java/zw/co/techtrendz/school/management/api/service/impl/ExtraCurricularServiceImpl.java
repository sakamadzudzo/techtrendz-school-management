package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularRepository;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularService;

import java.util.List;

@Service
public class ExtraCurricularServiceImpl implements ExtraCurricularService {

    @Autowired
    private ExtraCurricularRepository extraCurricularRepository;

    @Override
    public ExtraCurricular save(ExtraCurricular extraCurricular) {
        return this.extraCurricularRepository.save(extraCurricular);
    }

    @Override
    public List<ExtraCurricular> saveAll(List<ExtraCurricular> extraCurriculars) {
        return this.extraCurricularRepository.saveAll(extraCurriculars);
    }

    @Override
    public ExtraCurricular getById(Long id) {
        return this.extraCurricularRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ExtraCurricular> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.extraCurricularRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<ExtraCurricular> getAllByExample(ExtraCurricular extraCurricular, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (extraCurricular == null) {
            return null;
        } else {
            Example<ExtraCurricular> example = Example.of(extraCurricular);
            return this.extraCurricularRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
