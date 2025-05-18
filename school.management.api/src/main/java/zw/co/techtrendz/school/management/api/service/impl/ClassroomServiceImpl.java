package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Classroom;
import zw.co.techtrendz.school.management.api.repository.ClassroomRepository;
import zw.co.techtrendz.school.management.api.service.ClassroomService;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public Classroom save(Classroom classroom) {
        return this.classroomRepository.save(classroom);
    }

    @Override
    public List<Classroom> saveAll(List<Classroom> classrooms) {
        return classrooms.stream().map(this::save).toList();
    }

    @Override
    public Classroom getById(Long id) {
        return this.classroomRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Classroom> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.classroomRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Classroom> getAllByExample(Classroom classroom, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (classroom == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Classroom> example = Example.of(classroom);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.classroomRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
