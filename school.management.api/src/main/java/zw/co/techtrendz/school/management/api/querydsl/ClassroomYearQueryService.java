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
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.entity.QClassroomYear;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ClassroomYearQueryService {

    private final JPAQueryFactory queryFactory;

    public ClassroomYearQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public ClassroomYearDto getClassroomYearDetails(Long classroomYearId) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                ).from(classroomYear)
                .where(classroomYear.id.eq(classroomYearId))
                .fetchOne();
    }

    public Page<ClassroomYearDto> getAllClassroomYearDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ClassroomYearDto> classroomYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                )
                .from(classroomYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(classroomYear).fetchCount();

        return new PageImpl<>(classroomYearDtoList, pageable, total);
    }

    public Page<ClassroomYearDto> getAllClassroomYearDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ClassroomYearDto> classroomYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                ).from(classroomYear)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(classroomYear).where(predicate).fetchCount();

        return new PageImpl<>(classroomYearDtoList, pageable, total);
    }

    public Page<ClassroomYearDto> getAllClassroomYearDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<ClassroomYear> example) {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ClassroomYearDto> classroomYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getClassroomYearProjection()
                ).from(classroomYear)
                .where(QuerydslUtils.toPredicate(example, ClassroomYear.class, "classroomYear"))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(classroomYear).where(QuerydslUtils.toPredicate(example, ClassroomYear.class, "classroomYear")).fetchCount();

        return new PageImpl<>(classroomYearDtoList, pageable, total);
    }

    public List<YearDropdown> getAllClassroomYearForDropdown() {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        List<String> sortBy = List.of("classroom.name");
        Sort.Direction sortDir = Sort.Direction.ASC;
        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        List<Tuple> results = queryFactory
                .select(
                        classroomYear.id,
                        classroomYear.calendarYear,
                        classroomYear.classroom.name
                )
                .from(classroomYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .fetch();

        return results.parallelStream()
                .map(tuple -> {
                    YearDropdown dto = new YearDropdown();
                    dto.setId(tuple.get(classroomYear.id));
                    dto.setCalendarYear(tuple.get(classroomYear.calendarYear));
                    dto.setName(tuple.get(classroomYear.classroom.name));
                    return dto;
                })
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));
    }

    public List<Integer> getCalendarYears() {
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return this.queryFactory
                .select(ViewProjectionUtils.getCalendarYears())
                .from(classroomYear)
                .distinct()
                .fetch();
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(ClassroomYear.class, "classroomYear");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("classroom.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
