package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Coursework;
import zw.co.techtrendz.school.management.api.entity.QCoursework;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class CourseworkQueryService {

    private final JPAQueryFactory queryFactory;

    public CourseworkQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Coursework getById(long id) {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .where(coursework.id.eq(id))
                .fetchOne();
    }

    public List<Coursework> getAll() {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .fetch();
    }

    public List<Coursework> getAllByType(CourseworkType type) {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .where(coursework.type.eq(type))
                .fetch();
    }

    public List<Coursework> getAllBySubjectId(long subjectId) {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .where(coursework.subject.id.eq(subjectId))
                .fetch();
    }

    public List<Coursework> getAllByTypeBySubjectId(CourseworkType type, long subjectId) {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .where(coursework.type.eq(type).and(coursework.subject.id.eq(subjectId)))
                .fetch();
    }

    public List<Coursework> getAllBySubjectIds(List<Long> ids) {
        QCoursework coursework = QCoursework.coursework;

        return queryFactory
                .select(
                        ViewProjectionUtils.getCourseworkProjection()
                )
                .from(coursework)
                .where(coursework.subject.id.in(ids))
                .fetch();
    }
}
