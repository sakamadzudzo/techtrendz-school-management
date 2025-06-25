package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.QClassroomYear;
import zw.co.techtrendz.school.management.api.entity.QSubjectYear;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.List;

@Service
public class SubjectYearQueryService {

    private final JPAQueryFactory queryFactory;

    public SubjectYearQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<SubjectYearDto> getSubjectYears(List<Long> classroomYearIds) {
        QSubjectYear subjectYear = QSubjectYear.subjectYear;
        QClassroomYear classroomYear = QClassroomYear.classroomYear;

        return queryFactory.select(
                        ViewProjectionUtils.getSubjectYearWithTeacherProjection()
                )
                .from(subjectYear)
                .join(subjectYear.classrooms, classroomYear) // Join through the relationship
                .where(classroomYear.id.in(classroomYearIds))
                .fetch();
    }
}
