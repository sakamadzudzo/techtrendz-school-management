package zw.co.techtrendz.school.management.api.academic.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.entity.Mark;
import zw.co.techtrendz.school.management.api.academic.entity.*;
import zw.co.techtrendz.school.management.api.academic.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.common.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class MarkQueryService {

    private final JPAQueryFactory queryFactory;

    public MarkQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Mark getById(long id) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.id.eq(id))
                .fetchOne();
    }

    public List<Mark> getAll() {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .fetch();
    }

    public List<Mark> getAllByCourseworkId(long courseworkId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.id.eq(courseworkId))
                .fetch();
    }

    public List<Mark> getAllByStudentId(long studentId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId))
                .fetch();
    }

    public List<Mark> getAllBySubjectId(long subjectId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.subject.id.eq(subjectId))
                .fetch();
    }

    public List<Mark> getAllBySubjectIdByCourseworkId(long subjectId, long courseworkId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.subject.id.eq(subjectId).and(mark.coursework.id.eq(courseworkId)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectId(long studentId, long subjectId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId).and(mark.coursework.subject.id.eq(subjectId)))
                .fetch();
    }

    public Mark getByStudentIdByCourseworkId(long studentId, long courseworkId) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId).and(mark.coursework.id.eq(courseworkId)))
                .fetchOne();
    }

    public List<Mark> getAllBySubjectIdByCourseworkType(long subjectId, CourseworkType type) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.subject.id.eq(subjectId).and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByCourseworkType(long studentId, CourseworkType type) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId).and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(long studentId, long subjectId, CourseworkType type) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId)
                        .and(mark.coursework.subject.id.eq(subjectId))
                        .and(mark.coursework.type.eq(type)))
                .fetch();
    }

    public List<Mark> getAllByCourseworkIds(List<Long> courseworkIds) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.id.in(courseworkIds))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByClassroomIdByYear(long studentId, long classroomId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId)
                        .and(mark.student.classroom.isNotNull())
                        .and(classroom.id.eq(classroomId))
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdBySubjectIdByYear(long studentId, long subjectId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId)
                        .and(subjectYear.subject.id.eq(subjectId))
                        .and(subjectYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByStudentIdByYear(long studentId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.student.id.eq(studentId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByClassroomIdByYear(long classroomId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(classroom.id.eq(classroomId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllBySubjectIdByYear(long subjectId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.subject.subject.id.eq(subjectId)
                        .and(mark.coursework.subject.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByTeacherIdByYear(long teacherId, int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(subjectYear.teacher.id.eq(teacherId)
                        .and(subjectYear.calendarYear.eq(year)))
                .fetch();
    }

    public List<Mark> getAllByYear(int year) {
        QMark mark = QMark.mark1;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory
                .select(ViewProjectionUtils.getMarkProjection())
                .from(mark)
                .leftJoin(classroomYear)
                .on(mark.student.classroom.id.eq(classroomYear.id))
                .leftJoin(classroom)
                .on(classroomYear.classroom.id.eq(classroom.id))
                .leftJoin(subjectYear)
                .on(mark.coursework.subject.id.eq(subjectYear.id))
                .where(mark.coursework.subject.calendarYear.eq(year))
                .fetch();
    }
}
