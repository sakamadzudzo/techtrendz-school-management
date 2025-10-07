package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.querydsl.ActivityPointQueryService;
import zw.co.techtrendz.school.management.api.repository.ActivityPointRepository;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.RatingService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ActivityPointServiceImpl implements ActivityPointService {

    private final ActivityPointRepository activityPointRepository;
    private final ActivityPointQueryService activityPointQueryService;
    private final RatingService ratingService;

    public ActivityPointServiceImpl(ActivityPointRepository activityPointRepository, ActivityPointQueryService activityPointQueryService, RatingService ratingService) {
        this.activityPointRepository = activityPointRepository;
        this.activityPointQueryService = activityPointQueryService;
        this.ratingService = ratingService;
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
        activityPoint.setRatings(this.ratingService.getAllByActivityId(id));
        return activityPoint;
    }

    @Override
    public List<ActivityPoint> getAll() {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAll();
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByActivityId(long activityId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByActivityId(activityId);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByActivityIds(List<Long> activityIds) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByActivityIds(activityIds);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByStudentId(long studentId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByStudentId(studentId);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public ActivityPoint getByStudentIdByActivityId(long studentId, long activityId) {
        ActivityPoint activityPoint = this.activityPointQueryService.getByStudentIdByActivityId(studentId, activityId);
        activityPoint.setRatings(this.ratingService.getAllByActivityId(activityId));
        return activityPoint;
    }

    public List<ActivityPoint> getRatingsForActivityPoints(List<ActivityPoint> activityPoints) {
        List<Long> ids = activityPoints
                .parallelStream()
                .map(ActivityPoint::getId)
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));

        if (ids.isEmpty()) {
            return activityPoints;
        }

        List<Rating> ratings = this.ratingService.getAllByActivityIds(ids);
        Map<Long, List<Rating>> mappedRatings = ratings
                .parallelStream()
                .collect(Collectors.groupingBy(r -> r.getActivityPoint().getId()));

        return activityPoints
                .parallelStream()
                .map(activityPoint -> {
                    activityPoint.setRatings(mappedRatings.get(activityPoint.getId()));
                    return activityPoint;
                })
                .collect(Collectors.toCollection(
                                CopyOnWriteArrayList::new
                        )
                );
    }
}
