package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;
import zw.co.techtrendz.school.management.api.view.TeacherDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TeacherQueryService {

    private final JPAQueryFactory queryFactory;

    public TeacherQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public TeacherDto getTeacherDetails(Long teacherId) {
        QTeacher teacher = QTeacher.teacher;

        return queryFactory.select(
                        ViewProjectionUtils.getTeacherProjection()
                ).from(teacher)
                .where(teacher.id.eq(teacherId))
                .fetchOne();
    }

    public List<ClassroomYearDto> getClassroomYears(Long teacherId) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                ).from(classroomYear)
                .where(classroomYear.homeroomTeacher.id.eq(teacherId))
                .fetch();
    }

    public List<ClassroomYearDto> getClassroomYears(List<Long> teacherIds) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                ).from(classroomYear)
                .where(classroomYear.homeroomTeacher.id.in(teacherIds))
                .fetch();
    }

    public List<SubjectYearDto> getSubjectYears(Long teacherId) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory.select(
                        ViewProjectionUtils.getSubjectYearProjection()
                ).from(subjectYear)
                .where(subjectYear.teacher.id.eq(teacherId))
                .fetch();
    }

    public List<SubjectYearDto> getSubjectYears(List<Long> teacherIds) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory.select(
                        ViewProjectionUtils.getSubjectYearProjection()
                ).from(subjectYear)
                .where(subjectYear.teacher.id.in(teacherIds))
                .fetch();
    }

    public Page<TeacherDto> getAllTeacherDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QTeacher teacher = QTeacher.teacher;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<TeacherDto> teacherDtoList = queryFactory.select(
                        ViewProjectionUtils.getTeacherProjection()
                )
                .from(teacher)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(teacher).fetchCount();

        return new PageImpl<>(teacherDtoList, pageable, total);
    }

    public Page<TeacherDto> getAllTeacherDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QTeacher teacher = QTeacher.teacher;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<TeacherDto> teacherDtoList = queryFactory.select(
                        ViewProjectionUtils.getTeacherProjection()
                ).from(teacher)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(teacher).where(predicate).fetchCount();

        return new PageImpl<>(teacherDtoList, pageable, total);
    }

    public Page<TeacherDto> getAllTeacherDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<Teacher> example) {
        QTeacher teacher = QTeacher.teacher;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<TeacherDto> teacherDtoList = queryFactory.select(
                        ViewProjectionUtils.getTeacherProjection()
                ).from(teacher)
                .where(QuerydslUtils.toPredicate(example, Teacher.class, "teacher"))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(teacher).where(QuerydslUtils.toPredicate(example, Teacher.class, "teacher")).fetchCount();

        return new PageImpl<>(teacherDtoList, pageable, total);
    }

    public List<TeacherDto> getFacilitatorsByExtraCurricular(List<Long> extraCurricularIds) {
        QTeacher teacher = QTeacher.teacher;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return queryFactory.select(
                        ViewProjectionUtils.getTeacherWithECYProjection()
                ).from(teacher)
                .join(teacher.extraCurricularYears, extraCurricularYear)
                .where(extraCurricularYear.id.in(extraCurricularIds))
                .fetch();
    }

    public List<YearDropdown> getAllTeachersForDropdown() {
        QTeacher teacher = QTeacher.teacher;

        List<String> sortBy = List.of("person.lastName");
        Sort.Direction sortDir = Sort.Direction.ASC;
        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        List<Tuple> results = queryFactory
                .select(
                        teacher.id,
                        teacher.person.salutation,
                        teacher.person.firstName,
                        teacher.person.otherNames,
                        teacher.person.lastName
                )
                .from(teacher)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .fetch();

        return results.stream()
                .map(tuple -> {
                    String name = AppUtils.getFullname(tuple.get(teacher.person.salutation), tuple.get(teacher.person.firstName), tuple.get(teacher.person.otherNames), tuple.get(teacher.person.lastName));
                    YearDropdown dto = new YearDropdown();
                    dto.setId(tuple.get(teacher.id));
                    dto.setName(name);
                    return dto;
                })
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(Teacher.class, "teacher");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.lastName");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}