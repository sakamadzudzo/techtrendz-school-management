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
import zw.co.techtrendz.school.management.api.entity.QClassroomYear;
import zw.co.techtrendz.school.management.api.entity.QSubjectYear;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class SubjectYearQueryService {

    private final JPAQueryFactory queryFactory;

    public SubjectYearQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<SubjectYearDto> getSubjectYears(List<Long> classroomYearIds) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherProjection()
                )
                .from(subjectYear)
                .join(subjectYear.classrooms, classroomYear) // Join through the relationship
                .where(classroomYear.id.in(classroomYearIds))
                .fetch();
    }

    public SubjectYearDto getSubjectYearDetails(Long subjectYearId) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        return queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherNoClassroomProjection()
                ).from(subjectYear)
                .where(subjectYear.id.eq(subjectYearId))
                .fetchOne();
    }

    public Page<SubjectYearDto> getAllSubjectYearDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<SubjectYearDto> subjectYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherNoClassroomProjection()
                )
                .from(subjectYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(subjectYear).fetchCount();

        return new PageImpl<>(subjectYearDtoList, pageable, total);
    }

    public Page<SubjectYearDto> getAllSubjectYearDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<SubjectYearDto> subjectYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherNoClassroomProjection()
                ).from(subjectYear)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(subjectYear).where(predicate).fetchCount();

        return new PageImpl<>(subjectYearDtoList, pageable, total);
    }

    public Page<SubjectYearDto> getAllSubjectYearDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<SubjectYear> example) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<SubjectYearDto> subjectYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherNoClassroomProjection()
                ).from(subjectYear)
                .where(QuerydslUtils.toPredicate(example, SubjectYear.class, "subjectYear"))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(subjectYear).where(QuerydslUtils.toPredicate(example, SubjectYear.class, "subjectYear")).fetchCount();

        return new PageImpl<>(subjectYearDtoList, pageable, total);
    }

    public List<YearDropdown> getAllSubjectYearForDropdown() {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;

        List<String> sortBy = List.of("subject.name");
        Sort.Direction sortDir = Sort.Direction.ASC;
        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        List<Tuple> results = queryFactory
                .select(
                        subjectYear.id,
                        subjectYear.calendarYear,
                        subjectYear.subject.name
                )
                .from(subjectYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .fetch();

        return results.stream()
                .map(tuple -> {
                    YearDropdown dto = new YearDropdown();
                    dto.setId(tuple.get(subjectYear.id));
                    dto.setCalendarYear(tuple.get(subjectYear.calendarYear));
                    dto.setName(tuple.get(subjectYear.subject.name));
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
            PathBuilder<?> entityPath = new PathBuilder<>(SubjectYear.class, "subjectYear");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("subject.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
