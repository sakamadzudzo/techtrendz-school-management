package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.querydsl.RatingQueryService;
import zw.co.techtrendz.school.management.api.repository.RatingRepository;
import zw.co.techtrendz.school.management.api.service.RatingService;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingQueryService ratingQueryService;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingQueryService ratingQueryService) {
        this.ratingRepository = ratingRepository;
        this.ratingQueryService = ratingQueryService;
    }

    @Override
    public Rating save(Rating rating) {
        return this.ratingRepository.save(rating);
    }

    @Override
    public List<Rating> saveAll(List<Rating> ratings) {
        return this.ratingRepository.saveAll(ratings);
    }

    @Override
    public Rating getById(long id) {
        return this.ratingQueryService.getById(id);
    }

    @Override
    public List<Rating> getAll() {
        return this.ratingQueryService.getAll();
    }

    @Override
    public List<Rating> getAllByActivityId(long activityId) {
        return this.ratingQueryService.getAllByActivityId(activityId);
    }

    @Override
    public List<Rating> getAllByActivityIds(List<Long> activityIds) {
        return this.ratingQueryService.getAllByActivityIds(activityIds);
    }

    @Override
    public List<Rating> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, int year) {
        return this.ratingQueryService.getAllByStudentIdByActivityIdByYear(studentId, activityId, year);
    }

    @Override
    public List<Rating> getAllByStudentIdByYear(long studentId, int year) {
        return this.ratingQueryService.getAllByStudentIdByYear(studentId, year);
    }

    @Override
    public List<Rating> getAllByActivityIdByYear(long activityId, int year) {
        return this.ratingQueryService.getAllByActivityIdByYear(activityId, year);
    }

    @Override
    public List<Rating> getAllByTeacherIdByYear(long teacherId, int year) {
        return this.ratingQueryService.getAllByTeacherIdByYear(teacherId, year);
    }
}
