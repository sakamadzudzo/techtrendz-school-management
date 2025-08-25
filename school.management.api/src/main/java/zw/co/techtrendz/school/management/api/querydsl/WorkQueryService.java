package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.QWork;
import zw.co.techtrendz.school.management.api.entity.Work;
import zw.co.techtrendz.school.management.api.enums.WorkType;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class WorkQueryService {

    private final JPAQueryFactory queryFactory;

    public WorkQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Work getById(long id) {
        QWork work = QWork.work;

        return queryFactory
                .select(
                        ViewProjectionUtils.getWorkProjection()
                )
                .from(work)
                .where(work.id.eq(id))
                .fetchOne();
    }

    public List<Work> getAll() {
        QWork work = QWork.work;

        return queryFactory
                .select(
                        ViewProjectionUtils.getWorkProjection()
                )
                .from(work)
                .fetch();
    }

    public List<Work> getAllByType(WorkType type) {
        QWork work = QWork.work;

        return queryFactory
                .select(
                        ViewProjectionUtils.getWorkProjection()
                )
                .from(work)
                .where(work.type.eq(type))
                .fetch();
    }

    public List<Work> getAllBySubjectId(long subjectId) {
        QWork work = QWork.work;

        return queryFactory
                .select(
                        ViewProjectionUtils.getWorkProjection()
                )
                .from(work)
                .where(work.subject.id.eq(subjectId))
                .fetch();
    }

    public List<Work> getAllByTypeBySubjectId(WorkType type, long subjectId) {
        QWork work = QWork.work;

        return queryFactory
                .select(
                        ViewProjectionUtils.getWorkProjection()
                )
                .from(work)
                .where(work.type.eq(type).and(work.subject.id.eq(subjectId)))
                .fetch();
    }
}
