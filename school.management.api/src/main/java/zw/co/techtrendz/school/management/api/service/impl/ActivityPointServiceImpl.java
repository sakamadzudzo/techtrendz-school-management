package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.querydsl.ActivityPointQueryService;
import zw.co.techtrendz.school.management.api.repository.ActivityPointRepository;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;

import java.util.List;

@Service
public class ActivityPointServiceImpl implements ActivityPointService {

    private final ActivityPointRepository activityPointRepository;
    private final ActivityPointQueryService activityPointQueryService;

    public ActivityPointServiceImpl(ActivityPointRepository activityPointRepository, ActivityPointQueryService activityPointQueryService) {
        this.activityPointRepository = activityPointRepository;
        this.activityPointQueryService = activityPointQueryService;
    }

    @Override
    public ActivityPoint save(ActivityPoint activityPoint) {
        return this.activityPointRepository.save(activityPoint);
    }

    @Override
    public List<ActivityPoint> saveAll(List<ActivityPoint> activityPoints) {
        return this.activityPointRepository.saveAll(activityPoints);
    }

    @Override
    public ActivityPoint getById(long id) {
        ActivityPoint activityPoint = this.activityPointQueryService.getById(id);
        if (activityPoint == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Activity point with id '%d' was not found", id));
        }
        return activityPoint;
    }

    @Override
    public List<ActivityPoint> getAll() {
        return this.activityPointQueryService.getAll();
    }

    @Override
    public List<ActivityPoint> getAllByActivityId(long activityId) {
        return this.activityPointQueryService.getAllByActivityId(activityId);
    }

    @Override
    public List<ActivityPoint> getAllByStudentId(long studentId) {
        return this.activityPointQueryService.getAllByStudentId(studentId);
    }

    @Override
    public ActivityPoint getByStudentIdByActivityId(long studentId, long activityId) {
        return this.activityPointQueryService.getByStudentIdByActivityId(studentId, activityId);
    }
}
