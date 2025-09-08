package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.querydsl.MarkQueryService;
import zw.co.techtrendz.school.management.api.repository.MarkRepository;
import zw.co.techtrendz.school.management.api.service.MarkService;

import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final MarkQueryService markQueryService;

    public MarkServiceImpl(MarkRepository markRepository, MarkQueryService markQueryService) {
        this.markRepository = markRepository;
        this.markQueryService = markQueryService;
    }

    @Override
    public Mark save(Mark mark) {
        return this.markRepository.save(mark);
    }

    @Override
    public List<Mark> saveAll(List<Mark> marks) {
        return this.markRepository.saveAll(marks);
    }

    @Override
    public Mark getById(long id) {
        Mark mark = this.markQueryService.getById(id);
        if (mark == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Mark with id '%d' was not found", id));
        }
        return mark;
    }

    @Override
    public List<Mark> getAll() {
        return this.markQueryService.getAll();
    }

    @Override
    public List<Mark> getAllByCourseworkId(long courseworkId) {
        return this.markQueryService.getAllByCourseworkId(courseworkId);
    }

    @Override
    public List<Mark> getAllByStudentId(long studentId) {
        return this.markQueryService.getAllByStudentId(studentId);
    }

    @Override
    public List<Mark> getAllBySubjectId(long subjectId) {
        return this.markQueryService.getAllBySubjectId(subjectId);
    }

    @Override
    public List<Mark> getAllBySubjectIdByCourseworkId(long subjectId, long courseworkId) {
        return this.markQueryService.getAllBySubjectIdByCourseworkId(subjectId, courseworkId);
    }

    @Override
    public List<Mark> getAllByStudentIdBySubjectId(long studentId, long subjectId) {
        return this.markQueryService.getAllByStudentIdBySubjectId(studentId, subjectId);
    }

    @Override
    public Mark getByStudentIdByCourseworkId(long studentId, long courseworkId) {
        return this.markQueryService.getByStudentIdByCourseworkId(studentId, courseworkId);
    }

    @Override
    public List<Mark> getAllBySubjectIdByCourseworkType(long subjectId, CourseworkType type) {
        return this.markQueryService.getAllBySubjectIdByCourseworkType(subjectId, type);
    }

    @Override
    public List<Mark> getAllByStudentIdByCourseworkType(long studentId, CourseworkType type) {
        return this.markQueryService.getAllByStudentIdByCourseworkType(studentId, type);
    }

    @Override
    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(long studentId, long subjectId, CourseworkType type) {
        return this.markQueryService.getAllByStudentIdBySubjectIdByCourseworkType(studentId, subjectId, type);
    }

    @Override
    public List<Mark> getAllByCourseworkIds(List<Long> courseworkIds) {
        return this.markQueryService.getAllByCourseworkIds(courseworkIds);
    }
}
