package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.repository.DormRepository;
import zw.co.techtrendz.school.management.api.service.DormService;

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
        return this.dormRepository.saveAll(dorms);
    }

    @Override
    public Dorm getById(Long id) {
        return this.dormRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Dorm> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.dormRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Dorm> getAllByExample(Dorm dorm, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (dorm == null) {
            return null;
        } else {
            Example<Dorm> example = Example.of(dorm);
            return this.dormRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
