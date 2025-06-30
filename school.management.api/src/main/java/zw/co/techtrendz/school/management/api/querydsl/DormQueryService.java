package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.entity.QDorm;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.DormDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormQueryService {

    private final JPAQueryFactory queryFactory;

    public DormQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public DormDto getDormDetails(Long dormId) {
        QDorm dorm = QDorm.dorm;

        return queryFactory.select(
                        ViewProjectionUtils.getDormProjection()
                ).from(dorm)
                .where(dorm.id.eq(dormId))
                .fetchOne();
    }

    public Page<DormDto> getAllDormDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QDorm dorm = QDorm.dorm;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormDto> dormDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormProjection()
                )
                .from(dorm)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dorm).fetchCount();

        return new PageImpl<>(dormDtoList, pageable, total);
    }

    public Page<DormDto> getAllDormDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QDorm dorm = QDorm.dorm;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormDto> dormDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormProjection()
                ).from(dorm)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dorm).where(predicate).fetchCount();

        return new PageImpl<>(dormDtoList, pageable, total);
    }

    public Page<DormDto> getAllDormDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<Dorm> example) {
        QDorm dorm = QDorm.dorm;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<DormDto> dormDtoList = queryFactory.select(
                        ViewProjectionUtils.getDormProjection()
                ).from(dorm)
                .where(QuerydslUtils.toPredicate(example, Dorm.class))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(dorm).where(QuerydslUtils.toPredicate(example, Dorm.class)).fetchCount();

        return new PageImpl<>(dormDtoList, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(Dorm.class, "dorm");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
