package zw.co.techtrendz.school.management.api.user.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;
import zw.co.techtrendz.school.management.api.user.entity.Parent;
import zw.co.techtrendz.school.management.api.user.entity.QParent;
import zw.co.techtrendz.school.management.api.user.entity.QStudent;
import zw.co.techtrendz.school.management.api.user.view.ParentDto;
import zw.co.techtrendz.school.management.api.user.view.StudentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ParentQueryService {

    private final JPAQueryFactory queryFactory;

    public ParentQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public ParentDto getParentDetails(Long parentId) {
        QParent parent = QParent.parent;

        return queryFactory.select(
                        Projections.constructor(ParentDto.class,
                                parent.id,
                                parent.person.salutation,
                                parent.person.firstName,
                                parent.person.otherNames,
                                parent.person.lastName,
                                parent.person.dateOfBirth,
                                parent.person.email,
                                parent.person.mobileNumber,
                                parent.person.mobileNumber2,
                                parent.person.telNumber,
                                parent.person.whatsapp,
                                parent.person.facebook,
                                parent.person.linkedin,
                                parent.person.identification,
                                parent.status,
                                parent.datestamp,
                                new NullExpression<>(List.class)
                        )
                ).from(parent)
                .where(parent.id.eq(parentId))
                .fetchOne();
    }

    public ParentDto getParentDetailsByPersonId(Long personId) {
        QParent parent = QParent.parent;

        return queryFactory.select(
                        Projections.constructor(ParentDto.class,
                                parent.id,
                                parent.person.salutation,
                                parent.person.firstName,
                                parent.person.otherNames,
                                parent.person.lastName,
                                parent.person.dateOfBirth,
                                parent.person.email,
                                parent.person.mobileNumber,
                                parent.person.mobileNumber2,
                                parent.person.telNumber,
                                parent.person.whatsapp,
                                parent.person.facebook,
                                parent.person.linkedin,
                                parent.person.identification,
                                parent.status,
                                parent.datestamp,
                                new NullExpression<>(List.class)
                        )
                ).from(parent)
                .where(parent.person.id.eq(personId))
                .fetchOne();
    }

    public Page<ParentDto> getAllParentDetails(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        QParent parent = QParent.parent;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);
        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ParentDto> parentDtoList = queryFactory.select(
                        Projections.constructor(ParentDto.class,
                                parent.id,
                                parent.person.salutation,
                                parent.person.firstName,
                                parent.person.otherNames,
                                parent.person.lastName,
                                parent.person.dateOfBirth,
                                parent.person.email,
                                parent.person.mobileNumber,
                                parent.person.mobileNumber2,
                                parent.person.telNumber,
                                parent.person.whatsapp,
                                parent.person.facebook,
                                parent.person.linkedin,
                                parent.person.identification,
                                parent.status,
                                parent.datestamp,
                                new NullExpression<>(List.class)
                        )
                )
                .from(parent)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(parent).fetchCount();

        return new PageImpl<>(parentDtoList, pageable, total);
    }

    public Page<ParentDto> getAllParentDetailsByPredicate(int page, int size, List<String> sortBy, Sort.Direction sortDir, BooleanExpression predicate) {
        QParent parent = QParent.parent;

        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);
        Pageable pageable = this.getPageRequest(page, size, sortBy, sortDir);

        List<ParentDto> parentDtoList = queryFactory.select(
                        Projections.constructor(ParentDto.class,
                                parent.id,
                                parent.person.salutation,
                                parent.person.firstName,
                                parent.person.otherNames,
                                parent.person.lastName,
                                parent.person.dateOfBirth,
                                parent.person.email,
                                parent.person.mobileNumber,
                                parent.person.mobileNumber2,
                                parent.person.telNumber,
                                parent.person.whatsapp,
                                parent.person.facebook,
                                parent.person.linkedin,
                                parent.person.identification,
                                parent.status,
                                parent.datestamp,
                                new NullExpression<>(List.class)
                        )
                ).from(parent)
                .where(predicate)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(parent).where(predicate).fetchCount();

        return new PageImpl<>(parentDtoList, pageable, total);
    }

    public List<StudentDto> getStudentsForParents(List<Long> parentIds) {
        QParent parent = QParent.parent;
        QStudent student = QStudent.student;

        List<Tuple> results = queryFactory.select(
                        parent.id,
                        student.id,
                        student.person.salutation,
                        student.person.firstName,
                        student.person.otherNames,
                        student.person.lastName,
                        student.startYear,
                        student.endYear
                ).from(parent)
                .join(parent.students, student)
                .where(parent.id.in(parentIds))
                .fetch();

        return results.parallelStream()
                .map(tuple -> new StudentDto(
                        tuple.get(student.id),
                        tuple.get(student.person.salutation),
                        tuple.get(student.person.firstName),
                        tuple.get(student.person.otherNames),
                        tuple.get(student.person.lastName),
                        null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null,
                        tuple.get(student.startYear),
                        tuple.get(student.endYear),
                        null, null, null,
                        tuple.get(parent.id)))
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));
    }

    public List<YearDropdown> getAllParentsForDropdown() {
        QParent parent = QParent.parent;

        List<String> sortBy = List.of("person.lastName");
        Sort.Direction sortDir = Sort.Direction.ASC;
        List<OrderSpecifier<?>> orderSpecifiers = this.getOrderSpecifiers(sortBy, sortDir);

        List<Tuple> results = queryFactory
                .select(
                        parent.id,
                        parent.person.salutation,
                        parent.person.firstName,
                        parent.person.otherNames,
                        parent.person.lastName
                )
                .from(parent)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .fetch();

        return results.parallelStream()
                .map(tuple -> {
                    String name = AppUtils.getFullname(tuple.get(parent.person.salutation), tuple.get(parent.person.firstName), tuple.get(parent.person.otherNames), tuple.get(parent.person.lastName));
                    YearDropdown dto = new YearDropdown();
                    dto.setId(tuple.get(parent.id));
                    dto.setName(name);
                    return dto;
                })
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<String> sortBy, Sort.Direction sortDir) {
        Order order = sortDir == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (String sortField : sortBy) {
            PathBuilder<?> entityPath = new PathBuilder<>(Parent.class, "parent");
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