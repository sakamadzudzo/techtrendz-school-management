package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Work;
import zw.co.techtrendz.school.management.api.enums.WorkType;

import java.util.List;

@Service
public interface WorkService {
    public Work save(Work work);

    public List<Work> saveAll(List<Work> works);

    public Work getById(long id);

    public List<Work> getAll();

    public List<Work> getAllByType(WorkType type);

    public List<Work> getAllBySubjectId(long subjectId);

    public List<Work> getAllByTypeBySubjectId(WorkType type, long subjectId);
}
