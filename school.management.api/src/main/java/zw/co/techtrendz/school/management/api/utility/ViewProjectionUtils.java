package zw.co.techtrendz.school.management.api.utility;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.view.*;

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
                teacher.status,
                teacher.datestamp
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
                teacher.status,
                teacher.datestamp,
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
                classroomYear.datestamp,
                classroomYear.status
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearProjection() {
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
                subjectYear.teacher.id
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearWithClassroomIdProjection() {
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
                classroomYear.id
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
                        subjectYear.teacher.datestamp
                ),
                classroomYear.id,
                subjectYear.datestamp,
                subjectYear.status
        );
    }

    public static ConstructorExpression<SubjectYearDto> getSubjectYearWithTeacherNoClassroomProjection() {
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
                        subjectYear.teacher.datestamp
                ),
                subjectYear.datestamp,
                subjectYear.status
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
                student.status,
                student.datestamp
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
                student.status,
                student.datestamp,
                student.startYear,
                student.endYear,
                extraCurricularYear.id
        );
    }

    public static ConstructorExpression<StudentDto> getStudentWithClassroomProjection() {
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
                student.status,
                student.datestamp,
                Projections.constructor(StudentClassDto.class,
                        student.classroom.id,
                        student.classroom.classroom.name,
                        student.classroom.classroom.description,
                        student.classroom.calendarYear
                ),
                Projections.constructor(DormDto.class,
                        student.dorm.id,
                        student.dorm.dorm.name,
                        student.dorm.dorm.description,
                        student.dorm.dorm.address,
                        student.dorm.calendarYear,
                        student.dorm.dorm.school.name
                ),
                Projections.constructor(SchoolDto.class,
                        student.school.id,
                        student.school.name,
                        student.school.email,
                        student.school.telNumber,
                        student.school.mobileNumber,
                        student.school.mobileNumber2,
                        student.school.address,
                        student.school.website
                ),
                student.startYear,
                student.endYear
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
                extraCurricularYear.status
        );
    }

    public static ConstructorExpression<DormDto> getDormYearProjection() {
        QDormYear dormYear = QDormYear.dormYear;

        return Projections.constructor(DormDto.class,
                dormYear.id,
                dormYear.dorm.name,
                dormYear.dorm.description,
                dormYear.dorm.address,
                dormYear.calendarYear,
                dormYear.dorm.school.name
        );
    }
}
