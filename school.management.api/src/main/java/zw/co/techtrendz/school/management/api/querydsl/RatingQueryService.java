package zw.co.techtrendz.school.management.api.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.QRating;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.utility.ViewProjectionUtils;

import java.util.List;

@Service
public class RatingQueryService {

    private final JPAQueryFactory queryFactory;

    public RatingQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Rating getById(long id) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.id.eq(id))
                .fetchOne();
    }

    public List<Rating> getAll() {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .fetch();
    }

    public List<Rating> getAllByActivityId(long activityId) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.activityPoint.id.eq(activityId))
                .fetch();
    }

    public List<Rating> getAllByActivityIds(List<Long> activityIds) {
        QRating rating = QRating.rating1;

        return this.queryFactory
                .select(
                        ViewProjectionUtils.getRating()
                )
                .from(rating)
                .where(rating.activityPoint.id.in(activityIds))
                .fetch();
    }
}
