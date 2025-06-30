package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.view.DormDto;

import java.util.List;

@Service
public interface DormService {

    public Dorm save(Dorm dorm);

    public List<Dorm> saveAll(List<Dorm> dorms);

    public Dorm getById(Long id);

    public Page<Dorm> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Dorm> getAllByExample(Dorm dorm, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Dorm> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public DormDto getViewById(Long id);

    public Page<DormDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormDto> getViewAllByExample(Dorm dorm, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<DormDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);
}
