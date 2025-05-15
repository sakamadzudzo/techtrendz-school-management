package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Dorm;

import java.util.List;

@Service
public interface DormService {

    public Dorm save(Dorm dorm);

    public List<Dorm> saveAll(List<Dorm> dorms);

    public Dorm getById(Long id);

    public Page<Dorm> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir);

    public Page<Dorm> getAllByExample(Dorm dorm, int page, int size, String[] sortBy, Sort.Direction sortDir);
}
