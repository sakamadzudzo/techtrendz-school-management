package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.repository.DormRepository;
import zw.co.techtrendz.school.management.api.service.DormService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;

@Service
public class DormServiceImpl implements DormService {

    @Autowired
    private DormRepository dormRepository;

    @Override
    public Dorm save(Dorm dorm) {
        return this.dormRepository.save(dorm);
    }

    @Override
    public List<Dorm> saveAll(List<Dorm> dorms) {
        return dorms.stream().map(this::save).toList();
    }

    @Override
    public Dorm getById(Long id) {
        return this.dormRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Dorm> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.dormRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Dorm> getAllByExample(Dorm dorm, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (dorm == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Dorm> example = Example.of(dorm);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.dormRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Dorm> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Dorm> spec = Specification.where(null);
            for (var field : Dorm.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.dormRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
