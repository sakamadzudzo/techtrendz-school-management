package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.Coursework;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.querydsl.CourseworkQueryService;
import zw.co.techtrendz.school.management.api.repository.CourseworkRepository;
import zw.co.techtrendz.school.management.api.service.MarkService;
import zw.co.techtrendz.school.management.api.service.CourseworkService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class CourseworkServiceImpl implements CourseworkService {

    private final CourseworkRepository courseworkRepository;

    private final CourseworkQueryService courseworkQueryService;
    private final MarkService markService;

    public CourseworkServiceImpl(CourseworkRepository courseworkRepository, CourseworkQueryService courseworkQueryService, MarkService markService) {
        this.courseworkRepository = courseworkRepository;
        this.courseworkQueryService = courseworkQueryService;
        this.markService = markService;
    }

    @Override
    public Coursework save(Coursework coursework) {
        return this.courseworkRepository.save(coursework);
    }

    @Override
    public List<Coursework> saveAll(List<Coursework> courseworks) {
        return this.courseworkRepository.saveAll(courseworks);
    }

    @Override
    public Coursework getById(long id) {
        Coursework coursework = this.courseworkQueryService.getById(id);
        if (coursework == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Coursework with id '%d' was not found", id));
        }
        return coursework;
    }

    @Override
    public List<Coursework> getAll() {
        return this.courseworkQueryService.getAll();
    }

    @Override
    public List<Coursework> getAllByType(CourseworkType type) {
        return this.courseworkQueryService.getAllByType(type);
    }

    @Override
    public List<Coursework> getAllBySubjectId(long subjectId) {
        List<Coursework> courseworks = this.courseworkQueryService.getAllBySubjectId(subjectId);
        if (courseworks != null) {
            courseworks = this.getMarksForCourseworks(courseworks);
        }
        return courseworks;
    }

    @Override
    public List<Coursework> getAllByTypeBySubjectId(CourseworkType type, long subjectId) {
        List<Coursework> courseworks = this.courseworkQueryService.getAllByTypeBySubjectId(type, subjectId);
        if (courseworks != null) {
            courseworks = this.getMarksForCourseworks(courseworks);
        }
        return courseworks;
    }

    @Override
    public List<Coursework> getAllBySubjectIds(List<Long> ids) {
        List<Coursework> courseworks = this.courseworkQueryService.getAllBySubjectIds(ids);
        if (courseworks != null) {
            courseworks = this.getMarksForCourseworks(courseworks);
        }
        return courseworks;
    }

    private List<Coursework> getMarksForCourseworks(List<Coursework> courseworks) {
        List<Long> ids = courseworks
                .parallelStream()
                .map(Coursework::getId)
                .collect(
                        Collectors.toCollection(
                                CopyOnWriteArrayList::new
                        )
                );

        if (ids.isEmpty()) {
            return courseworks;
        }

        List<Mark> marks = this.markService.getAllByCourseworkIds(ids);
        Map<Long, List<Mark>> mappedMarks = marks
                .parallelStream()
                .collect(Collectors.groupingBy(m -> m.getCoursework().getId()));

        return courseworks
                .parallelStream()
                .map(w -> {
                    w.setMarks(mappedMarks.get(w.getId()));
                    return w;
                })
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));
    }
}
