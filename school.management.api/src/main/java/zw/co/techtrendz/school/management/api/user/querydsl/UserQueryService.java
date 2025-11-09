package zw.co.techtrendz.school.management.api.user.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.common.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.user.entity.QUser;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.user.view.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserQueryService {

    private final JPAQueryFactory queryFactory;

    public UserQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public UserDto getUserDetails(Long userId) {
        QUser user = QUser.user;

        return queryFactory.select(
                        ViewProjectionUtils.getUserProjection()
                ).from(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }

    public UserDto getUserDetails(BooleanExpression predicate) {
        QUser user = QUser.user;

        return queryFactory.select(
                        ViewProjectionUtils.getUserProjection()
                ).from(user)
                .where(predicate)
                .fetchOne();
    }

    public Page<UserDto> getAllUserDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QUser user = QUser.user;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<UserDto> userDtoList = queryFactory.select(
                        ViewProjectionUtils.getUserProjection()
                )
                .from(user)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(user).fetchCount();

        return new PageImpl<>(userDtoList, pageable, total);
    }

    public Page<UserDto> getAllUserDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QUser user = QUser.user;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<UserDto> userDtoList = queryFactory.select(
                        ViewProjectionUtils.getUserProjection()
                ).from(user)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(user).where(predicate).fetchCount();

        return new PageImpl<>(userDtoList, pageable, total);
    }

    public Page<UserDto> getAllUserDetailsByExample(int page, int size, List<String> sortBy, Sort.Direction sortDir, Example<User> example) {
        QUser user = QUser.user;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<UserDto> userDtoList = queryFactory.select(
                        ViewProjectionUtils.getUserProjection()
                ).from(user)
                .where(QuerydslUtils.toPredicate(example, User.class, "user"))
                .orderBy(orderSpecifiers.toArray(new com.querydsl.core.types.OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(user).where(QuerydslUtils.toPredicate(example, User.class, "user")).fetchCount();

        return new PageImpl<>(userDtoList, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(User.class, "user");
            orderSpecifiers.add(new OrderSpecifier(order, entityPath.get(sortField)));
        }

        return orderSpecifiers;
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
