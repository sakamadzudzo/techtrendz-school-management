package zw.co.techtrendz.school.management.api.academic.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.entity.ActivityPoint;

import java.util.List;

@Service
public interface ActivityPointService {

    public ActivityPoint save(ActivityPoint activityPoint);

    public List<ActivityPoint> saveAll(List<ActivityPoint> activityPoints);

    public ActivityPoint getById(long id);

    public List<ActivityPoint> getAll();

    public List<ActivityPoint> getAllByActivityId(long activityId);

    public List<ActivityPoint> getAllByActivityIds(List<Long> activityIds);

    public List<ActivityPoint> getAllByStudentId(long studentId);

    public ActivityPoint getByStudentIdByActivityId(long studentId, long activityId);

    // Report-focused variants that enrich ActivityPoints with additional report data
    public List<ActivityPoint> getAllByActivityIdForReports(long activityId);

    public List<ActivityPoint> getAllByStudentIdForReports(long studentId);

    // Report-specific data retrieval methods
    public List<ActivityPoint> getAllByTeacherId(long teacherId);

    public List<ActivityPoint> getAllByClassroomId(long classroomId);

    public List<ActivityPoint> getAllByFacilitatorId(long facilitatorId);

    // Year-based filtering methods for reports
    public List<ActivityPoint> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, Integer year);

    public List<ActivityPoint> getAllByStudentIdByYear(long studentId, Integer year);

    public List<ActivityPoint> getAllByActivityIdByYear(long activityId, Integer year);

    public List<ActivityPoint> getAllByTeacherIdByYear(long teacherId, Integer year);
}