package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.entity.QClassroom;
import zw.co.techtrendz.school.management.api.entity.QClassroomYear;
import zw.co.techtrendz.school.management.api.entity.QMark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class MarkQueryService {

    private final JPAQueryFactory queryFactory;

    public MarkQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Mark getById(long id) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.id.eq(id))
                .fetchOne();
    }

    public List<Mark> getAll() {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .fetch();
    }

    public List<Mark> getAllByCourseworkId(long courseworkId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.id.eq(courseworkId))
                .fetch();
    }

    public List<Mark> getAllByStudentId(long studentId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId))
                .fetch();
    }

    public List<Mark> getAllBySubjectId(long subjectId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.id.eq(subjectId))
                .fetch();
    }

    public List<Mark> getAllBySubjectIdByCourseworkId(long subjectId, long courseworkId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.id.eq(subjectId).and(mark.coursework.id.eq(courseworkId)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectId(long studentId, long subjectId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId).and(mark.coursework.subject.id.eq(subjectId)))
                .fetch();
    }

    public Mark getByStudentIdByCourseworkId(long studentId, long courseworkId) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId).and(mark.coursework.id.eq(courseworkId)))
                .fetchOne();
    }

    public List<Mark> getAllBySubjectIdByCourseworkType(long subjectId, CourseworkType type) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.id.eq(subjectId).and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByCourseworkType(long studentId, CourseworkType type) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId).and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(long studentId, long subjectId, CourseworkType type) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId)
                        .and(mark.coursework.subject.id.eq(subjectId))
                        .and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByCourseworkIds(List<Long> courseworkIds) {
        QMark mark = QMark.mark1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getMarkProjection()
                )
                .from(mark)
                .where(mark.coursework.id.in(courseworkIds))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByClassroomIdByYear(long studentId, long classroomId, int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId)
                        .and(mark.student.classroom.isNotNull())
                        .and(mark.student.classroom.classroom.id.eq(classroomId))
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectIdByYear(long studentId, long subjectId, int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId)
                        .and(mark.coursework.subject.subject.id.eq(subjectId))
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByYear(long studentId, int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.student.id.eq(studentId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByClassroomIdByYear(long classroomId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .where(classroom.id.eq(classroomId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllBySubjectIdByYear(long subjectId, int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.subject.id.eq(subjectId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByTeacherIdByYear(long teacherId, int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.teacher.id.eq(teacherId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByYear(int year) {
        QMark mark = QMark.mark1;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .where(mark.coursework.subject.calendarYear.eq(year))
                .fetch();
    }
}
