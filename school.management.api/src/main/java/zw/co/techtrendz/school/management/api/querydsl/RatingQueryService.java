package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.*;
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
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(rating.id.eq(id))
                .fetchOne();
    }

    public List<Rating> getAll() {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .fetch();
    }

    public List<Rating> getAllByActivityId(long activityId) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(rating.activityPoint.id.eq(activityId))
                .fetch();
    }

    public List<Rating> getAllByActivityIds(List<Long> activityIds) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(rating.activityPoint.id.in(activityIds))
                .fetch();
    }

    public List<Rating> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, int year) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(rating.activityPoint.student.id.eq(studentId)
                        .and(rating.activityPoint.id.eq(activityId))
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByStudentIdByYear(long studentId, int year) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(rating.activityPoint.student.id.eq(studentId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByActivityIdByYear(long activityId, int year) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(extraCurricular.id.eq(activityId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Rating> getAllByTeacherIdByYear(long teacherId, int year) {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QExtraCurricular extraCurricular = QExtraCurricular.extraCurricular;
        QStudent student = QStudent.student;

        return queryFactory
                .select(ViewProjectionUtils.getRatingWithNestedFields())
                .from(rating)
                .leftJoin(extraCurricularYear)
                .on(rating.activityPoint.extraCurricularYear.id.eq(extraCurricularYear.id))
                .leftJoin(extraCurricular)
                .on(extraCurricularYear.extraCurricular.id.eq(extraCurricular.id))
                .leftJoin(student)
                .on(rating.activityPoint.student.id.eq(student.id))
                .where(extraCurricularYear.facilitators.any().id.eq(teacherId)
                        .and(rating.activityPoint.extraCurricularYear.calendarYear.eq(year)))
                .fetch();
    }
}
