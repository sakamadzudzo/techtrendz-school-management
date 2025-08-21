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
import zw.co.techtrendz.school.management.api.view.StudentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class StudentQueryService {

    private final JPAQueryFactory queryFactory;

    public StudentQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public StudentDto getStudentDetails(Long studentId) {
        QStudent student = QStudent.student;

        return queryFactory.select(
                        ViewProjectionUtils.getStudentWithClassroomProjection()
                ).from(student)
                .where(student.id.eq(studentId))
                .fetchOne();
    }

    public Page<StudentDto> getAllStudentDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QStudent student = QStudent.student;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<StudentDto> studentDtoList = queryFactory.select(
                        ViewProjectionUtils.getStudentWithClassroomProjection()
                )
                .from(student)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(student).fetchCount();

        return new PageImpl<>(studentDtoList, pageable, total);
    }

    public Page<StudentDto> getAllStudentDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QStudent student = QStudent.student;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<StudentDto> studentDtoList = queryFactory.select(
                        ViewProjectionUtils.getStudentWithClassroomProjection()
                ).from(student)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(student).where(predicate).fetchCount();

        return new PageImpl<>(studentDtoList, pageable, total);
    }

    public Page<StudentDto> getAllStudentDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<Student> example) {
        QStudent student = QStudent.student;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<StudentDto> studentDtoList = queryFactory.select(
                        ViewProjectionUtils.getStudentWithClassroomProjection()
                ).from(student)
                .where(QuerydslUtils.toPredicate(example, Student.class, "student"))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(student).where(QuerydslUtils.toPredicate(example, Student.class, "student")).fetchCount();

        return new PageImpl<>(studentDtoList, pageable, total);
    }

    public List<StudentDto> getStudentsByExtraCurricular(List<Long> extraCurricularIds) {
        QStudent student = QStudent.student;
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return queryFactory.select(
                        ViewProjectionUtils.getStudentWithECYProjection()
                ).from(student)
                .join(student.extraCurricularYears, extraCurricularYear)
                .where(extraCurricularYear.id.in(extraCurricularIds))
                .fetch();
    }

    public List<StudentDto> getStudentsByDormId(List<Long> dormIds) {
        QStudent student = QStudent.student;
        QDormYear dormYear = QDormYear.dormYear;

        return queryFactory.select(
                        ViewProjectionUtils.getStudentWithDormProjection()
                ).from(student)
                .where(student.dorm.id.in(dormIds))
                .fetch();
    }

    public List<YearDropdown> getAllStudentsForDropdown() {
        QStudent student = QStudent.student;

        List<String> sortBy = List.of("person.lastName");
        Sort.Direction sortDir = Sort.Direction.ASC;
        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        List<Tuple> results = queryFactory
                .select(
                        student.id,
                        student.person.salutation,
                        student.person.firstName,
                        student.person.otherNames,
                        student.person.lastName
                )
                .from(student)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .fetch();

        return results.stream()
                .map(tuple -> {
                    String name = AppUtils.getFullname(tuple.get(student.person.salutation), tuple.get(student.person.firstName), tuple.get(student.person.otherNames), tuple.get(student.person.lastName));
                    YearDropdown dto = new YearDropdown();
                    dto.setId(tuple.get(student.id));
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
            PathBuilder<?> entityPath = new PathBuilder<>(Student.class, "student");
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