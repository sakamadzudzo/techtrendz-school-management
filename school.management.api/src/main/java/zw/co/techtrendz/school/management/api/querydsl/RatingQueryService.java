package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.QRating;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class RatingQueryService {

    private final JPAQueryFactory queryFactory;

    public RatingQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Rating getById(long id) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.id.eq(id))
                .fetchOne();
    }

    public List<Rating> getAll() {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .fetch();
    }

    public List<Rating> getAllByActivityId(long activityId) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.activityPoint.id.eq(activityId))
                .fetch();
    }

    public List<Rating> getAllByActivityIds(List<Long> activityIds) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.activityPoint.id.in(activityIds))
                .fetch();
    }

    public List<Rating> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, int year) {
        QRating rating = QRating.rating1;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .where(rating.activityPoint.student.id.eq(studentId)
                        .and(rating.activityPoint.id.eq(activityId))
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByStudentIdByYear(long studentId, int year) {
        QRating rating = QRating.rating1;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .where(rating.activityPoint.student.id.eq(studentId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByActivityIdByYear(long activityId, int year) {
        QRating rating = QRating.rating1;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .where(rating.activityPoint.extraCurricularYear.extraCurricular.id.eq(activityId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByTeacherIdByYear(long teacherId, int year) {
        QRating rating = QRating.rating1;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .where(rating.activityPoint.extraCurricularYear.facilitators.any().id.eq(teacherId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }
}
