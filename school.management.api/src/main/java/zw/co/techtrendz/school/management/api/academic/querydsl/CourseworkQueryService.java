package zw.co.techtrendz.school.management.api.academic.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.academic.entity.Coursework;
import zw.co.techtrendz.school.management.api.academic.entity.QCoursework;
import zw.co.techtrendz.school.management.api.academic.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.common.utility.ViewProjectionUtils;

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

    public List<CourseworkDropdown> getAllCourseworkForDropdown() {
        QCoursework coursework = QCoursework.coursework;

        return this.queryFactory
                .select(ViewProjectionUtils.getCourseworkDropdown())
                .from(coursework)
                .fetch();
    }
}
