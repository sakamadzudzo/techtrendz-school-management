package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.entity.QExtraCurricularYear;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.ExtraCurricularYearDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtraCurricularYearQueryService {

    private final JPAQueryFactory queryFactory;

    public ExtraCurricularYearQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public ExtraCurricularYearDto getExtraCurricularYearDetails(Long extraCurricularYearId) {
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        return queryFactory.select(
                        ViewProjectionUtils.getExtraCurricularYearProjection()
                ).from(extraCurricularYear)
                .where(extraCurricularYear.id.eq(extraCurricularYearId))
                .fetchOne();
    }

    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ExtraCurricularYearDto> extraCurricularYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getExtraCurricularYearProjection()
                )
                .from(extraCurricularYear)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(extraCurricularYear).fetchCount();

        return new PageImpl<>(extraCurricularYearDtoList, pageable, total);
    }

    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ExtraCurricularYearDto> extraCurricularYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getExtraCurricularYearProjection()
                ).from(extraCurricularYear)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(extraCurricularYear).where(predicate).fetchCount();

        return new PageImpl<>(extraCurricularYearDtoList, pageable, total);
    }

    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<ExtraCurricularYear> example) {
        QExtraCurricularYear extraCurricularYear = QExtraCurricularYear.extraCurricularYear;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ExtraCurricularYearDto> extraCurricularYearDtoList = queryFactory.select(
                        ViewProjectionUtils.getExtraCurricularYearProjection()
                ).from(extraCurricularYear)
                .where(QuerydslUtils.toPredicate(example, ExtraCurricularYear.class))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(extraCurricularYear).where(QuerydslUtils.toPredicate(example, ExtraCurricularYear.class)).fetchCount();

        return new PageImpl<>(extraCurricularYearDtoList, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(ExtraCurricularYear.class, "extraCurricularYear");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("extraCurricular.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
