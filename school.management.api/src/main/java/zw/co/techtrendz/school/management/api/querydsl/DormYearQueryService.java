package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.entity.QDormYear;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.DormYearDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormYearQueryService {

    private final JPAQueryFactory queryFactory;

    public DormYearQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public DormYearDto getDormYearDetails(Long dormYearId) {
        QDormYear dormYear = QDormYear.dormYear;

        return queryFactory.select(
                        ViewProjectionUtils.getDormYearProjection()
                ).from(dormYear)
                .where(dormYear.id.eq(dormYearId))
                .fetchOne();
    }

    public Page<DormYearDto> getAllDormYearDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QDormYear dormYear = QDormYear.dormYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormYearDto> dormYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormYearProjection()
                )
                .from(dormYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dormYear).fetchCount();

        return new PageImpl<>(dormYearDtoList, pageable, total);
    }

    public Page<DormYearDto> getAllDormYearDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QDormYear dormYear = QDormYear.dormYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormYearDto> dormYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormYearProjection()
                ).from(dormYear)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dormYear).where(predicate).fetchCount();

        return new PageImpl<>(dormYearDtoList, pageable, total);
    }

    public Page<DormYearDto> getAllDormYearDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<DormYear> example) {
        QDormYear dormYear = QDormYear.dormYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormYearDto> dormYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormYearProjection()
                ).from(dormYear)
                .where(QuerydslUtils.toPredicate(example, DormYear.class))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dormYear).where(QuerydslUtils.toPredicate(example, DormYear.class)).fetchCount();

        return new PageImpl<>(dormYearDtoList, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(DormYear.class, "dormYear");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("dorm.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
