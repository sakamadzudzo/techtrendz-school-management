package zw.co.techtrendz.school.management.api.finance.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.finance.entity.Bank;
import zw.co.techtrendz.school.management.api.finance.entity.QBank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class BankQueryService {

    private final JPAQueryFactory queryFactory;

    public BankQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<YearDropdown> getAllBankDropdown(List<String> sortBy, Sort.Direction sortDir) {

        QBank bank = QBank.bank;

        List<Tuple> results = this.queryFactory
                .select(
                        bank.id,
                        bank.name
                )
                .from(bank)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .fetch();

        return results
                .stream()
                .map(tuple -> new YearDropdown(tuple.get(bank.id), tuple.get(bank.name), null))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        // Build sorting order
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;

        return sortBy
                .stream()
                .map(sortField -> {
                    PathBuilder<?> entityPath = new PathBuilder<>(Bank.class, "bank");
                    return new OrderSpecifier(order, entityPath.get(sortField));
                })
                .toArray(OrderSpecifier[]::new);
    }
}
