package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularYearRepository;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularYearService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

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
        return extraCurricularYears.stream().map(this::save).toList();
    }

    @Override
    public ExtraCurricularYear getById(Long id) {
        return this.extraCurricularYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ExtraCurricularYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.extraCurricularYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<ExtraCurricularYear> getAllByExample(ExtraCurricularYear extraCurricularYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (extraCurricularYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<ExtraCurricularYear> example = Example.of(extraCurricularYear);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<ExtraCurricularYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<ExtraCurricularYear> spec = Specification.where(null);
            for (var field : ExtraCurricularYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
