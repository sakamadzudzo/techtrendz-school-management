package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;

import java.util.List;

@Service
public interface MarkService {

    public Mark save(Mark mark);

    public List<Mark> saveAll(List<Mark> marks);

    public Mark getById(long id);

    public List<Mark> getAll();

    public List<Mark> getAllByCourseworkId(long courseworkId);

    public List<Mark> getAllByStudentId(long studentId);

    public List<Mark> getAllBySubjectId(long subjectId);

    public List<Mark> getAllBySubjectIdByCourseworkId(long subjectId, long courseworkId);

    public List<Mark> getAllByStudentIdBySubjectId(long studentId, long subjectId);

    public Mark getByStudentIdByCourseworkId(long studentId, long courseworkId);

    public List<Mark> getAllBySubjectIdByCourseworkType(long subjectId, CourseworkType type);

    public List<Mark> getAllByStudentIdByCourseworkType(long studentId, CourseworkType type);

    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(long studentId, long subjectId, CourseworkType type);

    public List<Mark> getAllByCourseworkIds(List<Long> courseworkIds);

    // Report-specific data retrieval methods
    public List<Mark> getAllByStudentIdByClassroomIdByYear(long studentId, long classroomId, int year);

    public List<Mark> getAllByStudentIdBySubjectIdByYear(long studentId, long subjectId, int year);

    public List<Mark> getAllByStudentIdByYear(long studentId, int year);

    public List<Mark> getAllByClassroomIdByYear(long classroomId, int year);

    public List<Mark> getAllBySubjectIdByYear(long subjectId, int year);

    public List<Mark> getAllByTeacherIdByYear(long teacherId, int year);

    public List<Mark> getAllByYear(int year);
}
