package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.repository.ClassroomYearRepository;
import zw.co.techtrendz.school.management.api.service.ClassroomYearService;

import java.util.List;

@Service
public class ClassroomYearServiceImpl implements ClassroomYearService {

    @Autowired
    private ClassroomYearRepository classroomYearRepository;

    @Override
    public ClassroomYear save(ClassroomYear classroomYear) {
        return this.classroomYearRepository.save(classroomYear);
    }

    @Override
    public List<ClassroomYear> saveAll(List<ClassroomYear> classroomYears) {
        return this.classroomYearRepository.saveAll(classroomYears);
    }

    @Override
    public ClassroomYear getById(Long id) {
        return this.classroomYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ClassroomYear> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.classroomYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<ClassroomYear> getAllByExample(ClassroomYear classroomYear, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (classroomYear == null) {
            return null;
        } else {
            Example<ClassroomYear> example = Example.of(classroomYear);
            return this.classroomYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
