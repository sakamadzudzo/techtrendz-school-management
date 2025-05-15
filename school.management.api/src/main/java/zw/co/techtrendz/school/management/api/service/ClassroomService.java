package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Classroom;

import java.util.List;

@Service
public interface ClassroomService {

    public Classroom save(Classroom classroom);

    public List<Classroom> saveAll(List<Classroom> classrooms);

    public Classroom getById(Long id);

    public Page<Classroom> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir);

    public Page<Classroom> getAllByExample(Classroom classroom, int page, int size, String[] sortBy, Sort.Direction sortDir);
}
