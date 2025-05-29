package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularRepository;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

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
        return extraCurriculars.stream().map(this::save).toList();
    }

    @Override
    public ExtraCurricular getById(Long id) {
        return this.extraCurricularRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ExtraCurricular> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.extraCurricularRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<ExtraCurricular> getAllByExample(ExtraCurricular extraCurricular, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (extraCurricular == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<ExtraCurricular> example = Example.of(extraCurricular);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<ExtraCurricular> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<ExtraCurricular> spec = Specification.where(null);
            for (var field : ExtraCurricular.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
