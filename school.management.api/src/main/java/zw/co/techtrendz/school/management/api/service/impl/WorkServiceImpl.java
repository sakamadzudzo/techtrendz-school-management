package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Work;
import zw.co.techtrendz.school.management.api.enums.WorkType;
import zw.co.techtrendz.school.management.api.querydsl.WorkQueryService;
import zw.co.techtrendz.school.management.api.repository.WorkRepository;
import zw.co.techtrendz.school.management.api.service.WorkService;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;

    private final WorkQueryService workQueryService;

    public WorkServiceImpl(WorkRepository workRepository, WorkQueryService workQueryService) {
        this.workRepository = workRepository;
        this.workQueryService = workQueryService;
    }

    @Override
    public Work save(Work work) {
        return this.workRepository.save(work);
    }

    @Override
    public List<Work> saveAll(List<Work> works) {
        return this.workRepository.saveAll(works);
    }

    @Override
    public Work getById(long id) {
        return this.workQueryService.getById(id);
    }

    @Override
    public List<Work> getAll() {
        return this.workQueryService.getAll();
    }

    @Override
    public List<Work> getAllByType(WorkType type) {
        return this.workQueryService.getAllByType(type);
    }

    @Override
    public List<Work> getAllBySubjectId(long subjectId) {
        return this.workQueryService.getAllBySubjectId(subjectId);
    }

    @Override
    public List<Work> getAllByTypeBySubjectId(WorkType type, long subjectId) {
        return this.workQueryService.getAllByTypeBySubjectId(type, subjectId);
    }
}
