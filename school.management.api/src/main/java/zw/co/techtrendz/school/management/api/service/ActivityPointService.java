package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;

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

    // Report-specific data retrieval methods
    public List<ActivityPoint> getAllByTeacherId(long teacherId);

    public List<ActivityPoint> getAllByClassroomId(long classroomId);

    public List<ActivityPoint> getAllByFacilitatorId(long facilitatorId);
}
