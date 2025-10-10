package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Rating;

import java.util.List;

@Service
public interface RatingService {

    public Rating save(Rating rating);

    public List<Rating> saveAll(List<Rating> ratings);

    public Rating getById(long id);

    public List<Rating> getAll();

    public List<Rating> getAllByActivityId(long activityId);

    public List<Rating> getAllByActivityIds(List<Long> activityIds);

    // Report-specific data retrieval methods
    public List<Rating> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, int year);

    public List<Rating> getAllByStudentIdByYear(long studentId, int year);

    public List<Rating> getAllByActivityIdByYear(long activityId, int year);

    public List<Rating> getAllByTeacherIdByYear(long teacherId, int year);
}
