package zw.co.techtrendz.school.management.api.academic.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.entity.Coursework;
import zw.co.techtrendz.school.management.api.academic.enums.CourseworkType;

import java.util.List;

@Service
public interface CourseworkService {

    public Coursework save(Coursework coursework);

    public List<Coursework> saveAll(List<Coursework> courseworks);

    public Coursework getById(long id);

    public List<Coursework> getAll();

    public List<Coursework> getAllByType(CourseworkType type);

    public List<Coursework> getAllBySubjectId(long subjectId);

    public List<Coursework> getAllByTypeBySubjectId(CourseworkType type, long subjectId);

    public List<Coursework> getAllBySubjectIds(List<Long> ids);
}
