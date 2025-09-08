package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.entity.QActivityPoint;

import java.util.List;

@Service
public class ActivityPointQueryService {

    private final JPAQueryFactory queryFactory;

    public ActivityPointQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public ActivityPoint getById(long id) {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return queryFactory
                .select(activityPoint)
                .from(activityPoint)
                .where(activityPoint.id.eq(id))
                .fetchOne();
    }

    public List<ActivityPoint> getAll() {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return queryFactory
                .select(activityPoint)
                .from(activityPoint)
                .fetch();
    }

    public List<ActivityPoint> getAllByActivityId(long activityId) {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return queryFactory
                .select(activityPoint)
                .from(activityPoint)
                .where(activityPoint.extraCurricularYear.id.eq(activityId))
                .fetch();
    }

    public List<ActivityPoint> getAllByStudentId(long studentId) {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return queryFactory
                .select(activityPoint)
                .from(activityPoint)
                .where(activityPoint.student.id.eq(studentId))
                .fetch();
    }

    public ActivityPoint getByStudentIdByActivityId(long studentId, long activityId) {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return queryFactory
                .select(activityPoint)
                .from(activityPoint)
                .where(activityPoint.student.id.eq(studentId)
                        .and(activityPoint.extraCurricularYear.id.eq(activityId)))
                .fetchOne();
    }
}
