package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.Classroom;
import zw.co.techtrendz.school.management.api.repository.ClassroomRepository;
import zw.co.techtrendz.school.management.api.service.ClassroomService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;
import java.util.Optional;

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
        Optional<Classroom> classroom = this.classroomRepository.findById(id);
        if (classroom.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Classroom with id '%d' was not found", id));
        }
        return classroom.get();
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

    @Override
    public Page<Classroom> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Classroom> spec = Specification.where(null);
            for (var field : Classroom.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.classroomRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
