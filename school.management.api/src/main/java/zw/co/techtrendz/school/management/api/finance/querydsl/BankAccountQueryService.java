package zw.co.techtrendz.school.management.api.finance.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.finance.entity.Bank;
import zw.co.techtrendz.school.management.api.finance.entity.BankAccount;
import zw.co.techtrendz.school.management.api.finance.entity.QBankAccount;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountQueryService {

    private final JPAQueryFactory queryFactory;

    public BankAccountQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public BankAccount getByPredicate(BooleanExpression predicate) {
        QBankAccount bankAccount = QBankAccount.bankAccount;

        return this.queryFactory
                .select(ViewProjectionUtils.getBankAccount())
                .from(bankAccount)
                .where(predicate)
                .fetchOne();
    }

    public Page<BankAccount> getByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QBankAccount bankAccount = QBankAccount.bankAccount;

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<BankAccount> resultSet = this.queryFactory
                .select(ViewProjectionUtils.getBankAccount())
                .from(bankAccount)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = this.queryFactory
                .selectFrom(bankAccount)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(resultSet, pageable, total);
    }

    public List<BankAccount> getByPredicate(List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QBankAccount bankAccount = QBankAccount.bankAccount;

        return this.queryFactory
                .select(ViewProjectionUtils.getBankAccount())
                .from(bankAccount)
                .where(predicate)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .fetch();
    }

    public Page<BankAccount> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QBankAccount bankAccount = QBankAccount.bankAccount;

        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<BankAccount> resultSet = this.queryFactory
                .select(ViewProjectionUtils.getBankAccount())
                .from(bankAccount)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = this.queryFactory
                .selectFrom(bankAccount)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(resultSet, pageable, total);
    }

    public List<BankAccount> getAll(List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("id");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        QBankAccount bankAccount = QBankAccount.bankAccount;

        return this.queryFactory
                .select(ViewProjectionUtils.getBankAccount())
                .from(bankAccount)
                .orderBy(this.getOrderSpecifiers(sortBy, sortDir))
                .fetch();
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
                    PathBuilder<?> entityPath = new PathBuilder<>(BankAccount.class, "bankAccount");
                    return new OrderSpecifier(order, entityPath.get(sortField));
                })
                .toArray(OrderSpecifier[]::new);
    }

    private Pageable getPageRequest(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy == null) sortBy = new ArrayList<>();
        if (sortBy.isEmpty()) sortBy.add("accountNumber");
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        String[] sortFields = sortBy.toArray(new String[0]);

        return PageRequest.of(page, size, Sort.by(sortDir, sortFields));
    }
}
