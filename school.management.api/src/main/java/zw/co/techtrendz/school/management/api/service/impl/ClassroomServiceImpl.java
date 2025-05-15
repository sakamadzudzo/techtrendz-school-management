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
        return this.classroomRepository.saveAll(classrooms);
    }

    @Override
    public Classroom getById(Long id) {
        return this.classroomRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Classroom> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.classroomRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Classroom> getAllByExample(Classroom classroom, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (classroom == null) {
            return null;
        } else {
            Example<Classroom> example = Example.of(classroom);
            return this.classroomRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
