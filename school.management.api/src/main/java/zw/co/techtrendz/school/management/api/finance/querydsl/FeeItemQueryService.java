package zw.co.techtrendz.school.management.api.finance.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.finance.entity.FeeItem;
import zw.co.techtrendz.school.management.api.finance.entity.QFee;
import zw.co.techtrendz.school.management.api.finance.entity.QFeeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static java.util.Collections.list;

@Service
public class FeeItemQueryService {

    private final JPAQueryFactory queryFactory;

    public FeeItemQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public FeeItem getByPredicate(BooleanExpression predicate) {
        QFeeItem feeItem = QFeeItem.feeItem;

        return this.queryFactory
                .select(ViewProjectionUtils.getFeeItem())
                .from(feeItem)
                .where(predicate)
                .fetchFirst();
    }

    public Page<FeeItem> getByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QFeeItem feeItem = QFeeItem.feeItem;

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<FeeItem> resultSet = this.queryFactory
                .select(ViewProjectionUtils.getFeeItem())
                .from(feeItem)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = this.queryFactory
                .selectFrom(feeItem)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(resultSet, pageable, total);
    }

    public List<FeeItem> getByPredicate(List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QFeeItem feeItem = QFeeItem.feeItem;

        return this.queryFactory
                .select(ViewProjectionUtils.getFeeItem())
                .from(feeItem)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .fetch();
    }

    public Page<FeeItem> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QFeeItem feeItem = QFeeItem.feeItem;

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<FeeItem> resultSet = this.queryFactory
                .select(ViewProjectionUtils.getFeeItem())
                .from(feeItem)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = this.queryFactory
                .selectFrom(feeItem)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(resultSet, pageable, total);
    }

    public List<FeeItem> getAll(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QFeeItem feeItem = QFeeItem.feeItem;

        return this.queryFactory
                .select(ViewProjectionUtils.getFeeItem())
                .from(feeItem)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .fetch();
    }

    public Map<Long, List<FeeItem>> getAllByFee(long feeId) {
        QFeeItem feeItem = QFeeItem.feeItem;
        QFee fee = QFee.fee;

        return this.queryFactory
                .select(
                        feeItem.id,
                        feeItem.name,
                        feeItem.description,
                        feeItem.price,
                        feeItem.quantity,
                        feeItem.status,
                        feeItem.description,
                        fee.id
                )
                .from(fee)
                .leftJoin(fee.items.any(), feeItem)
                .where(fee.id.eq(feeId))
                .transform(
                        groupBy(fee.id).as(fee.items)
                );
    }

    public Map<Long, List<FeeItem>> getAllByFee(List<Long> feeIds) {
        QFeeItem feeItem = QFeeItem.feeItem;
        QFee fee = QFee.fee;

        return this.queryFactory
                .select(
                        feeItem.id,
                        feeItem.name,
                        feeItem.description,
                        feeItem.price,
                        feeItem.quantity,
                        feeItem.status,
                        feeItem.description,
                        fee.id
                )
                .from(fee)
                .leftJoin(fee.items.any(), feeItem)
                .where(fee.id.in(feeIds))
                .transform(
                        groupBy(fee.id).as(fee.items)
                );
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;

        return sortBy
                .stream()
                .map(sortField -> {
                    PathBuilder<?> entityPath = new PathBuilder<>(FeeItem.class, "feeItem");
                    return new OrderSpecifier(order, entityPath.get(sortField));
                })
                .toArray(OrderSpecifier[]::new);
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        String[] sortFields = sortBy.toArray(new String[0]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
