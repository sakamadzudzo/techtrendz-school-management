package zw.co.techtrendz.school.management.api.common.utility;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Projections;
import zw.co.techtrendz.school.management.api.academic.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.academic.entity.*;
import zw.co.techtrendz.school.management.api.academic.view.*;
import zw.co.techtrendz.school.management.api.finance.entity.*;
import zw.co.techtrendz.school.management.api.user.entity.*;
import zw.co.techtrendz.school.management.api.user.view.StudentDto;
import zw.co.techtrendz.school.management.api.user.view.TeacherDto;
import zw.co.techtrendz.school.management.api.user.view.UserDto;

import java.time.LocalDate;
import java.util.List;

public class ViewProjectionUtils {

    public static ConstructorExpression<TeacherDto> getTeacherProjection() {
        QTeacher teacher = QTeacher.teacher;

        return Projections.constructor(TeacherDto.class,
                teacher.id,
                teacher.person.salutation,
                teacher.person.firstName,
                teacher.person.otherNames,
                teacher.person.lastName,
                teacher.person.dateOfBirth,
                teacher.person.email,
                teacher.person.mobileNumber,
                teacher.person.mobileNumber2,
                teacher.person.telNumber,
                teacher.person.whatsapp,
                teacher.person.facebook,
                teacher.person.linkedin,
                teacher.person.identification,
                teacher.status,
                teacher.datestamp,
                new NullExpression<>(List.class),
                new NullExpression<>(List.class),
                new NullExpression<>(Long.class)
        );
    }

    public static ConstructorExpression<TeacherDto> getTeacherWithECYProjection() {
        QTeacher teacher = QTeacher.teacher;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return Projections.constructor(TeacherDto.class,
                teacher.id,
                teacher.person.salutation,
                teacher.person.firstName,
                teacher.person.otherNames,
                teacher.person.lastName,
                teacher.person.dateOfBirth,
                teacher.person.email,
                teacher.person.mobileNumber,
                teacher.person.mobileNumber2,
                teacher.person.telNumber,
                teacher.person.whatsapp,
                teacher.person.facebook,
                teacher.person.linkedin,
                teacher.person.identification,
                teacher.status,
                teacher.datestamp,
                new NullExpression<>(List.class),
                new NullExpression<>(List.class),
                extraCurricularYear.id
        );
    }

    public static ConstructorExpression<ClassroomYearDto> getClassroomYearProjection() {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return Projections.constructor(ClassroomYearDto.class,
                classroomYear.id,
                classroomYear.classroom.name,
                classroomYear.classroom.description,
                classroomYear.calendarYear,
                classroomYear.homeroomTeacher.id,
                new NullExpression<>(List.class),
                Projections.constructor(TeacherDto.class,
                        classroomYear.homeroomTeacher.id,
                        classroomYear.homeroomTeacher.person.salutation,
                        classroomYear.homeroomTeacher.person.firstName,
                        classroomYear.homeroomTeacher.person.otherNames,
                        classroomYear.homeroomTeacher.person.lastName,
                        classroomYear.homeroomTeacher.person.dateOfBirth,
                        classroomYear.homeroomTeacher.person.email,
                        classroomYear.homeroomTeacher.person.mobileNumber,
                        classroomYear.homeroomTeacher.person.mobileNumber2,
                        classroomYear.homeroomTeacher.person.telNumber,
                        classroomYear.homeroomTeacher.person.whatsapp,
                        classroomYear.homeroomTeacher.person.facebook,
                        classroomYear.homeroomTeacher.person.linkedin,
                        classroomYear.homeroomTeacher.status,
                        classroomYear.homeroomTeacher.datestamp,
                        new NullExpression<>(List.class),
                        new NullExpression<>(List.class),
                        new NullExpression<>(Long.class)
                ),
                classroomYear.datestamp,
                classroomYear.status,
                classroomYear.classroom.id
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearProjection() {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return Projections.constructor(SubjectYearDto.class,
                subjectYear.id,
                subjectYear.calendarYear,
                subjectYear.subject.name,
                subjectYear.subject.description,
                subjectYear.subject.code,
                subjectYear.subject.type,
                subjectYear.subject.syllabus,
                subjectYear.subject.curriculum,
                subjectYear.subject.level,
                subjectYear.subject.category,
                subjectYear.teacher.id,
                new NullExpression<>(TeacherDto.class),
                new NullExpression<>(Long.class),
                subjectYear.datestamp,
                subjectYear.status,
                subjectYear.subject.id,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearWithTeacherProjection() {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return Projections.constructor(SubjectYearDto.class,
                subjectYear.id,
                subjectYear.calendarYear,
                subjectYear.subject.name,
                subjectYear.subject.description,
                subjectYear.subject.code,
                subjectYear.subject.type,
                subjectYear.subject.syllabus,
                subjectYear.subject.curriculum,
                subjectYear.subject.level,
                subjectYear.subject.category,
                subjectYear.teacher.id,
                Projections.constructor(TeacherDto.class,
                        subjectYear.teacher.id,
                        subjectYear.teacher.person.salutation,
                        subjectYear.teacher.person.firstName,
                        subjectYear.teacher.person.otherNames,
                        subjectYear.teacher.person.lastName,
                        subjectYear.teacher.person.dateOfBirth,
                        subjectYear.teacher.person.email,
                        subjectYear.teacher.person.mobileNumber,
                        subjectYear.teacher.person.mobileNumber2,
                        subjectYear.teacher.person.telNumber,
                        subjectYear.teacher.person.whatsapp,
                        subjectYear.teacher.person.facebook,
                        subjectYear.teacher.person.linkedin,
                        subjectYear.teacher.status,
                        subjectYear.teacher.datestamp,
                        new NullExpression<>(List.class),
                        new NullExpression<>(List.class),
                        new NullExpression<>(Long.class)
                ),
                classroomYear.id,
                subjectYear.datestamp,
                subjectYear.status,
                subjectYear.subject.id,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearWithTeacherNoClassroomProjection() {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return Projections.constructor(SubjectYearDto.class,
                subjectYear.id,
                subjectYear.calendarYear,
                subjectYear.subject.name,
                subjectYear.subject.description,
                subjectYear.subject.code,
                subjectYear.subject.type,
                subjectYear.subject.syllabus,
                subjectYear.subject.curriculum,
                subjectYear.subject.level,
                subjectYear.subject.category,
                subjectYear.teacher.id,
                Projections.constructor(TeacherDto.class,
                        subjectYear.teacher.id,
                        subjectYear.teacher.person.salutation,
                        subjectYear.teacher.person.firstName,
                        subjectYear.teacher.person.otherNames,
                        subjectYear.teacher.person.lastName,
                        subjectYear.teacher.person.dateOfBirth,
                        subjectYear.teacher.person.email,
                        subjectYear.teacher.person.mobileNumber,
                        subjectYear.teacher.person.mobileNumber2,
                        subjectYear.teacher.person.telNumber,
                        subjectYear.teacher.person.whatsapp,
                        subjectYear.teacher.person.facebook,
                        subjectYear.teacher.person.linkedin,
                        subjectYear.teacher.status,
                        subjectYear.teacher.datestamp,
                        new NullExpression<>(List.class),
                        new NullExpression<>(List.class),
                        new NullExpression<>(Long.class)
                ),
                new NullExpression<>(Long.class),
                subjectYear.datestamp,
                subjectYear.status,
                subjectYear.subject.id,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<StudentDto> getStudentProjection() {
        QStudent student = QStudent.student;

        return Projections.constructor(StudentDto.class,
                student.id,
                student.person.salutation,
                student.person.firstName,
                student.person.otherNames,
                student.person.lastName,
                student.person.dateOfBirth,
                student.person.email,
                student.person.mobileNumber,
                student.person.mobileNumber2,
                student.person.telNumber,
                student.person.whatsapp,
                student.person.facebook,
                student.person.linkedin,
                student.person.identification,
                student.status,
                student.datestamp,
                new NullExpression<>(StudentClassDto.class),
                new NullExpression<>(DormYearDto.class),
                new NullExpression<>(SchoolDto.class),
                student.startYear,
                student.endYear,
                new NullExpression<>(Long.class),
                new NullExpression<>(Long.class),
                new NullExpression<>(ActivityPoint.class),
                new NullExpression<>(Long.class)
        );
    }

    public static ConstructorExpression<StudentDto> getStudentWithDormProjection() {
        QStudent student = QStudent.student;

        return Projections.constructor(StudentDto.class,
                student.id,
                student.person.salutation,
                student.person.firstName,
                student.person.otherNames,
                student.person.lastName,
                student.person.dateOfBirth,
                student.person.email,
                student.person.mobileNumber,
                student.person.mobileNumber2,
                student.person.telNumber,
                student.person.whatsapp,
                student.person.facebook,
                student.person.linkedin,
                student.person.identification,
                student.status,
                student.datestamp,
                new NullExpression<>(StudentClassDto.class),
                new NullExpression<>(DormYearDto.class),
                new NullExpression<>(SchoolDto.class),
                student.startYear,
                student.endYear,
                new NullExpression<>(Long.class),
                student.dorm.id,
                new NullExpression<>(ActivityPoint.class),
                new NullExpression<>(Long.class)
        );
    }

    public static ConstructorExpression<StudentDto> getStudentWithECYProjection() {
        QStudent student = QStudent.student;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return Projections.constructor(StudentDto.class,
                student.id,
                student.person.salutation,
                student.person.firstName,
                student.person.otherNames,
                student.person.lastName,
                student.person.dateOfBirth,
                student.person.email,
                student.person.mobileNumber,
                student.person.mobileNumber2,
                student.person.telNumber,
                student.person.whatsapp,
                student.person.facebook,
                student.person.linkedin,
                student.person.identification,
                student.status,
                student.datestamp,
                new NullExpression<>(StudentClassDto.class),
                new NullExpression<>(DormYearDto.class),
                new NullExpression<>(SchoolDto.class),
                student.startYear,
                student.endYear,
                extraCurricularYear.id,
                new NullExpression<>(Long.class),
                new NullExpression<>(ActivityPoint.class),
                new NullExpression<>(Long.class)
        );
    }

    public static ConstructorExpression<StudentDto> getStudentWithClassroomProjection() {
        QStudent student = QStudent.student;
        QDorm dorm = QDorm.dorm;

        return Projections.constructor(StudentDto.class,
                student.id,
                student.person.salutation,
                student.person.firstName,
                student.person.otherNames,
                student.person.lastName,
                student.person.dateOfBirth,
                student.person.email,
                student.person.mobileNumber,
                student.person.mobileNumber2,
                student.person.telNumber,
                student.person.whatsapp,
                student.person.facebook,
                student.person.linkedin,
                student.person.identification,
                student.status,
                student.datestamp,
                Projections.constructor(StudentClassDto.class,
                        student.classroom.id,
                        student.classroom.classroom.name,
                        student.classroom.classroom.description,
                        student.classroom.calendarYear,
                        new NullExpression<>(List.class),
                        student.classroom.status,
                        student.classroom.datestamp
                ),
                Projections.constructor(DormYearDto.class,
                        student.dorm.id,
                        student.dorm.dorm.name,
                        student.dorm.dorm.description,
                        student.dorm.dorm.address,
                        student.dorm.calendarYear,
                        dorm.school.name,
                        new NullExpression<>(List.class),
                        student.dorm.id,
                        student.dorm.status,
                        student.dorm.datestamp
                ),
                Projections.constructor(SchoolDto.class,
                        student.school.id,
                        student.school.name,
                        student.school.email,
                        student.school.telNumber,
                        student.school.mobileNumber,
                        student.school.mobileNumber2,
                        student.school.address,
                        student.school.website,
                        student.school.status,
                        student.school.datestamp
                ),
                student.startYear,
                student.endYear,
                new NullExpression<>(Long.class),
                new NullExpression<>(Long.class),
                new NullExpression<>(ActivityPoint.class),
                new NullExpression<>(Long.class)
        );
    }

    public static ConstructorExpression<ExtraCurricularYearDto> getExtraCurricularYearProjection() {
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return Projections.constructor(ExtraCurricularYearDto.class,
                extraCurricularYear.id,
                extraCurricularYear.extraCurricular.name,
                extraCurricularYear.extraCurricular.description,
                extraCurricularYear.calendarYear,
                extraCurricularYear.datestamp,
                extraCurricularYear.status,
                new NullExpression<>(List.class),
                new NullExpression<>(List.class),
                extraCurricularYear.extraCurricular.id,
                extraCurricularYear.leadFacilitatorId,
                new NullExpression<>(TeacherDto.class)
        );
    }

    public static ConstructorExpression<DormYearDto> getDormYearProjection() {
        QDormYear dormYear = QDormYear.dormYear;

        return Projections.constructor(DormYearDto.class,
                dormYear.id,
                dormYear.dorm.name,
                dormYear.dorm.description,
                dormYear.dorm.address,
                dormYear.calendarYear,
                dormYear.dorm.school.name,
                new NullExpression<>(List.class),
                dormYear.dorm.id,
                dormYear.dorm.status,
                dormYear.dorm.datestamp,
                dormYear.dorm.school.id
        );
    }

    public static ConstructorExpression<DormDto> getDormProjection() {
        QDorm dorm = QDorm.dorm;

        return Projections.constructor(DormDto.class,
                dorm.id,
                dorm.name,
                dorm.description,
                dorm.address,
                dorm.status,
                dorm.datestamp,
                Projections.constructor(SchoolDto.class,
                        dorm.school.id,
                        dorm.school.name,
                        dorm.school.email,
                        dorm.school.telNumber,
                        dorm.school.mobileNumber,
                        dorm.school.mobileNumber2,
                        dorm.school.address,
                        dorm.school.website,
                        dorm.school.status,
                        dorm.school.datestamp
                )
        );
    }

    public static ConstructorExpression<UserDto> getUserProjection() {
        QUser user = QUser.user;

        return Projections.constructor(UserDto.class,
                user.id,
                user.person.id,
                user.person.salutation,
                user.person.firstName,
                user.person.otherNames,
                user.person.lastName,
                user.person.dateOfBirth,
                user.person.email,
                user.person.mobileNumber,
                user.person.mobileNumber2,
                user.person.telNumber,
                user.person.whatsapp,
                user.person.facebook,
                user.person.linkedin,
                user.person.identification,
                user.username,
                user.password,
                user.verified,
                user.enabled,
                user.changePassword,
                user.roles,
                user.status,
                user.datestamp
        );
    }

    public static ConstructorExpression<Coursework> getCourseworkProjection() {
        QCoursework coursework = QCoursework.coursework;

        return Projections.constructor(Coursework.class,
                coursework.id,
                coursework.title,
                coursework.type,
                Projections.constructor(SubjectYear.class,
                        coursework.subject.id,
                        Projections.constructor(Subject.class,
                                coursework.subject.subject.id,
                                coursework.subject.subject.name),
                        coursework.subject.calendarYear,
                        new NullExpression<>(Teacher.class),
                        new NullExpression<>(List.class)
                ),
                coursework.total,
                coursework.passMark,
                coursework.status,
                coursework.datestamp
        );
    }

    public static ConstructorExpression<Mark> getMarkProjection() {
        QMark mark = QMark.mark1;
        QClassroom classroom = QClassroom.classroom;
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return Projections.constructor((Mark.class),
                mark.id,
                Projections.constructor(Coursework.class,
                        mark.coursework.id,
                        mark.coursework.title,
                        mark.coursework.type,
                        Projections.constructor(SubjectYear.class,
                                mark.coursework.subject.id,
                                Projections.constructor(Subject.class,
                                        subjectYear.subject.id,
                                        subjectYear.subject.name
                                ),
                                mark.coursework.subject.calendarYear,
                                Projections.constructor(Teacher.class,
                                        subjectYear.teacher.id,
                                        Projections.constructor(Person.class,
                                                subjectYear.teacher.person.id,
                                                subjectYear.teacher.person.salutation,
                                                subjectYear.teacher.person.firstName,
                                                subjectYear.teacher.person.otherNames,
                                                subjectYear.teacher.person.lastName,
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(String.class),
                                                new NullExpression<>(LocalDate.class),
                                                subjectYear.teacher.person.identification
                                        )
                                ),
                                new NullExpression<>(List.class)
                        ),
                        mark.coursework.total,
                        mark.coursework.passMark,
                        mark.coursework.status,
                        mark.coursework.datestamp
                ),
                Projections.constructor(Student.class,
                        mark.student.id,
                        Projections.constructor(Person.class,
                                mark.student.person.id,
                                mark.student.person.salutation,
                                mark.student.person.firstName,
                                mark.student.person.otherNames,
                                mark.student.person.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                mark.student.person.identification
                        ),
                        Projections.constructor(ClassroomYear.class,
                                mark.student.classroom.id,
                                Projections.constructor(Classroom.class,
                                        classroom.id,
                                        classroom.name,
                                        classroom.description)
                        )
                ),
                mark.mark,
                mark.status,
                mark.datestamp
        );
    }

    public static ConstructorExpression<ActivityPoint> getActivityPoints() {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;

        return Projections.constructor(ActivityPoint.class,
                activityPoint.id,
                Projections.constructor(ExtraCurricularYear.class,
                        activityPoint.extraCurricularYear.id,
                        new NullExpression<>(Integer.class),
                        Projections.constructor(ExtraCurricular.class,
                                activityPoint.extraCurricularYear.extraCurricular.id,
                                activityPoint.extraCurricularYear.extraCurricular.name,
                                activityPoint.extraCurricularYear.extraCurricular.description
                        ),
                        new NullExpression<>(List.class),
                        new NullExpression<>(List.class),
                        activityPoint.extraCurricularYear.leadFacilitatorId
                ),
                Projections.constructor(Student.class,
                        activityPoint.student.id,
                        Projections.constructor(Person.class,
                                activityPoint.student.person.id,
                                activityPoint.student.person.salutation,
                                activityPoint.student.person.firstName,
                                activityPoint.student.person.otherNames,
                                activityPoint.student.person.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                activityPoint.student.person.identification
                        ),
                        new NullExpression<>(Integer.class),
                        new NullExpression<>(Integer.class),
                        new NullExpression<>(ClassroomYear.class),
                        new NullExpression<>(List.class),
                        new NullExpression<>(DormYear.class),
                        new NullExpression<>(School.class)
                ),
                activityPoint.remarks,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<ActivityPoint> getActivityPointsWithFullECA() {
        QActivityPoint activityPoint = QActivityPoint.activityPoint;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return Projections.constructor(ActivityPoint.class,
                activityPoint.id,
                Projections.constructor(ExtraCurricularYear.class,
                        activityPoint.extraCurricularYear.id,
                        activityPoint.extraCurricularYear.calendarYear,
                        Projections.constructor(ExtraCurricular.class,
                                extraCurricularYear.extraCurricular.id,
                                extraCurricularYear.extraCurricular.name,
                                extraCurricularYear.extraCurricular.description
                        ),
                        new NullExpression<>(List.class), // facilitators - will be populated later
                        new NullExpression<>(List.class), // students - will be populated later
                        activityPoint.extraCurricularYear.leadFacilitatorId
                ),
                Projections.constructor(Student.class,
                        activityPoint.student.id,
                        Projections.constructor(Person.class,
                                activityPoint.student.person.id,
                                activityPoint.student.person.salutation,
                                activityPoint.student.person.firstName,
                                activityPoint.student.person.otherNames,
                                activityPoint.student.person.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                activityPoint.student.person.identification
                        ),
                        new NullExpression<>(Integer.class),
                        new NullExpression<>(Integer.class),
                        new NullExpression<>(ClassroomYear.class),
                        new NullExpression<>(List.class),
                        new NullExpression<>(DormYear.class),
                        new NullExpression<>(School.class)
                ),
                activityPoint.remarks,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<Rating> getRating() {
        QRating rating = QRating.rating1;
        QStudent student = QStudent.student;

        return Projections.constructor(Rating.class,
                rating.id,
                Projections.constructor(ActivityPoint.class,
                        rating.activityPoint.id,
                        Projections.constructor(ExtraCurricularYear.class,
                                rating.activityPoint.extraCurricularYear.id,
                                new NullExpression<>(Integer.class),
                                new NullExpression<>(ExtraCurricular.class),
                                new NullExpression<>(List.class),
                                new NullExpression<>(List.class),
                                rating.activityPoint.extraCurricularYear.leadFacilitatorId
                        ),
                        Projections.constructor(Student.class,
                                rating.activityPoint.student.id,
                                Projections.constructor(Person.class,
                                        student.person.id,
                                        student.person.salutation,
                                        student.person.firstName,
                                        student.person.otherNames,
                                        student.person.lastName,
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(LocalDate.class),
                                        student.person.identification
                                ),
                                student.startYear,
                                student.endYear,
                                new NullExpression<>(ClassroomYear.class),
                                new NullExpression<>(List.class),
                                new NullExpression<>(DormYear.class),
                                new NullExpression<>(School.class)
                        ),
                        new NullExpression<>(String.class),
                        new NullExpression<>(List.class)
                ),
                rating.rating
        );
    }

    public static ConstructorExpression<Rating> getRatingWithNestedFields() {
        QRating rating = QRating.rating1;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;
        QStudent student = QStudent.student;

        return Projections.constructor(Rating.class,
                rating.id,
                Projections.constructor(ActivityPoint.class,
                        rating.activityPoint.id,
                        Projections.constructor(ExtraCurricularYear.class,
                                rating.activityPoint.extraCurricularYear.id,
                                rating.activityPoint.extraCurricularYear.calendarYear,
                                Projections.constructor(ExtraCurricular.class,
                                        extraCurricularYear.extraCurricular.id,
                                        extraCurricularYear.extraCurricular.name,
                                        extraCurricularYear.extraCurricular.description
                                ),
                                new NullExpression<>(List.class),
                                new NullExpression<>(List.class),
                                rating.activityPoint.extraCurricularYear.leadFacilitatorId
                        ),
                        Projections.constructor(Student.class,
                                rating.activityPoint.student.id,
                                Projections.constructor(Person.class,
                                        student.person.id,
                                        student.person.salutation,
                                        student.person.firstName,
                                        student.person.otherNames,
                                        student.person.lastName,
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(String.class),
                                        new NullExpression<>(LocalDate.class),
                                        student.person.identification
                                ),
                                new NullExpression<>(Integer.class),
                                new NullExpression<>(Integer.class),
                                new NullExpression<>(ClassroomYear.class),
                                new NullExpression<>(List.class),
                                new NullExpression<>(DormYear.class),
                                new NullExpression<>(School.class)
                        ),
                        new NullExpression<>(String.class),
                        new NullExpression<>(List.class)
                ),
                rating.rating
        );
    }

    public static ConstructorExpression<Integer> getCalendarYears() {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return Projections.constructor(Integer.class,
                classroomYear.calendarYear
        );
    }

    public static ConstructorExpression<Integer> getCalendarYears2() {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return Projections.constructor(Integer.class,
                subjectYear.calendarYear
        );
    }

    public static ConstructorExpression<CourseworkDropdown> getCourseworkDropdown() {
        QCoursework coursework = QCoursework.coursework;

        return Projections.constructor(CourseworkDropdown.class,
                coursework.id,
                coursework.type,
                coursework.subject.calendarYear,
                Projections.constructor(Subject.class,
                        coursework.subject.subject.id,
                        coursework.subject.subject.name
                )
        );
    }

    public static ConstructorExpression<BankAccount> getBankAccount() {
        QBankAccount bankAccount = QBankAccount.bankAccount;

        return Projections.constructor(BankAccount.class,
                bankAccount.id,
                bankAccount.name,
                bankAccount.description,
                Projections.constructor(Person.class,
                        bankAccount.holder.id,
                        bankAccount.holder.salutation,
                        bankAccount.holder.firstName,
                        bankAccount.holder.otherNames,
                        bankAccount.holder.lastName,
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(String.class),
                        new NullExpression<>(LocalDate.class),
                        bankAccount.holder.identification
                ),
                Projections.constructor(Bank.class,
                        bankAccount.bank.id,
                        bankAccount.bank.name,
                        bankAccount.bank.description
                ),
                bankAccount.branch,
                bankAccount.branchCode,
                bankAccount.accountNumber,
                Projections.constructor(Currency.class,
                        bankAccount.currency.id,
                        bankAccount.currency.name,
                        bankAccount.currency.description
                ),
                bankAccount.status,
                bankAccount.datestamp
        );
    }

    public static ConstructorExpression<Fee> getFee() {
        QFee fee = QFee.fee;

        return Projections.constructor(Fee.class,
                fee.id,
                Projections.constructor(Student.class,
                        fee.student.id,
                        Projections.constructor(Person.class,
                                fee.student.person.id,
                                fee.student.person.salutation,
                                fee.student.person.firstName,
                                fee.student.person.otherNames,
                                fee.student.person.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                fee.student.person.identification
                        ),
                        new NullExpression<>(ClassroomYear.class)
                ),
                fee.calendarYear,
                new NullExpression<>(List.class)
        );
    }

    public static ConstructorExpression<FeePayment> getFeePayment() {
        QFeePayment feePayment = QFeePayment.feePayment;

        return Projections.constructor(FeePayment.class,
                feePayment.id,
                Projections.constructor(Student.class,
                        feePayment.student.id,
                        Projections.constructor(Person.class,
                                feePayment.student.person.id,
                                feePayment.student.person.salutation,
                                feePayment.student.person.firstName,
                                feePayment.student.person.otherNames,
                                feePayment.student.person.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                feePayment.student.person.identification
                        ),
                        new NullExpression<>(ClassroomYear.class)
                ),
                feePayment.paymentType,
                Projections.constructor(BankAccount.class,
                        feePayment.payingAccount.id,
                        feePayment.payingAccount.name,
                        feePayment.payingAccount.description,
                        Projections.constructor(Person.class,
                                feePayment.payingAccount.holder.id,
                                feePayment.payingAccount.holder.salutation,
                                feePayment.payingAccount.holder.firstName,
                                feePayment.payingAccount.holder.otherNames,
                                feePayment.payingAccount.holder.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                feePayment.payingAccount.holder.identification
                        ),
                        Projections.constructor(Bank.class,
                                feePayment.payingAccount.bank.id,
                                feePayment.payingAccount.bank.name,
                                feePayment.payingAccount.bank.description
                        ),
                        feePayment.payingAccount.branch,
                        feePayment.payingAccount.branchCode,
                        feePayment.payingAccount.accountNumber,
                        Projections.constructor(Currency.class,
                                feePayment.payingAccount.currency.id,
                                feePayment.payingAccount.currency.name,
                                feePayment.payingAccount.currency.description
                        ),
                        feePayment.payingAccount.status,
                        feePayment.payingAccount.datestamp
                ),
                Projections.constructor(BankAccount.class,
                        feePayment.receivingAccount.id,
                        feePayment.receivingAccount.name,
                        feePayment.receivingAccount.description,
                        Projections.constructor(Person.class,
                                feePayment.receivingAccount.holder.id,
                                feePayment.receivingAccount.holder.salutation,
                                feePayment.receivingAccount.holder.firstName,
                                feePayment.receivingAccount.holder.otherNames,
                                feePayment.receivingAccount.holder.lastName,
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(String.class),
                                new NullExpression<>(LocalDate.class),
                                feePayment.receivingAccount.holder.identification
                        ),
                        Projections.constructor(Bank.class,
                                feePayment.receivingAccount.bank.id,
                                feePayment.receivingAccount.bank.name,
                                feePayment.receivingAccount.bank.description
                        ),
                        feePayment.receivingAccount.branch,
                        feePayment.receivingAccount.branchCode,
                        feePayment.receivingAccount.accountNumber,
                        Projections.constructor(Currency.class,
                                feePayment.receivingAccount.currency.id,
                                feePayment.receivingAccount.currency.name,
                                feePayment.receivingAccount.currency.description
                        ),
                        feePayment.receivingAccount.status,
                        feePayment.receivingAccount.datestamp
                ),
                feePayment.referenceType,
                feePayment.reference,
                feePayment.kindType,
                Projections.constructor(Currency.class,
                        feePayment.currency.id,
                        feePayment.currency.name,
                        feePayment.currency.description
                ),
                feePayment.amount,
                feePayment.paymentDate,
                feePayment.paymentTime,
                feePayment.verified,
                feePayment.status,
                feePayment.datestamp
        );
    }

    public static ConstructorExpression<FeeItem> getFeeItem() {
        QFeeItem feeItem = QFeeItem.feeItem;

        return Projections.constructor(FeeItem.class,
                feeItem.id,
                feeItem.name,
                feeItem.description,
                feeItem.price,
                feeItem.quantity,
                feeItem.status,
                feeItem.datestamp
        );
    }
}
