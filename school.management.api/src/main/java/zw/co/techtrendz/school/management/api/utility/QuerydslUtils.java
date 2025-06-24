package zw.co.techtrendz.school.management.api.utility;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuerydslUtils {

    public static <T> BooleanExpression exactMatch(Class<T> entityClass, String fieldName, Object value) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (value == null) {
            return null; // No filtering if value is null
        }
        return entityPath.get(fieldName).eq(value);
    }

    public static <T> BooleanExpression likeMatch(Class<T> entityClass, String fieldName, String value) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (value == null || value.isEmpty()) {
            return null; // No filtering if value is null/empty
        }
        return entityPath.getString(fieldName).containsIgnoreCase(value);
    }

    public static <T> BooleanExpression isNotNull(Class<T> entityClass, String fieldName) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        return entityPath.get(fieldName).isNotNull();
    }

    public static <T> BooleanExpression isNull(Class<T> entityClass, String fieldName) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        return entityPath.get(fieldName).isNull();
    }

    public static <T> BooleanExpression dateBetween(Class<T> entityClass, String fieldName, LocalDateTime startDate, LocalDateTime endDate) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (startDate != null && endDate != null) {
            return entityPath.getDateTime(fieldName, LocalDateTime.class).between(startDate, endDate);
        } else if (startDate != null) {
            return entityPath.getDateTime(fieldName, LocalDateTime.class).goe(startDate);
        } else if (endDate != null) {
            return entityPath.getDateTime(fieldName, LocalDateTime.class).loe(endDate);
        }
        return null; // No filtering if both are null
    }

    public static <T> BooleanExpression bigDecimalBetween(Class<T> entityClass, String fieldName, BigDecimal min, BigDecimal max) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (min != null && max != null) {
            return entityPath.getNumber(fieldName, BigDecimal.class).between(min, max);
        } else if (min != null) {
            return entityPath.getNumber(fieldName, BigDecimal.class).goe(min);
        } else if (max != null) {
            return entityPath.getNumber(fieldName, BigDecimal.class).loe(max);
        }
        return null; // No filtering if both are null
    }

    public static <T> BooleanExpression integerBetween(Class<T> entityClass, String fieldName, Integer min, Integer max) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (min != null && max != null) {
            return entityPath.getNumber(fieldName, Integer.class).between(min, max);
        } else if (min != null) {
            return entityPath.getNumber(fieldName, Integer.class).goe(min);
        } else if (max != null) {
            return entityPath.getNumber(fieldName, Integer.class).loe(max);
        }
        return null; // No filtering if both are null
    }

    public static <T> BooleanExpression doubleBetween(Class<T> entityClass, String fieldName, Double min, Double max) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        if (min != null && max != null) {
            return entityPath.getNumber(fieldName, Double.class).between(min, max);
        } else if (min != null) {
            return entityPath.getNumber(fieldName, Double.class).goe(min);
        } else if (max != null) {
            return entityPath.getNumber(fieldName, Double.class).loe(max);
        }
        return null; // No filtering if both are null
    }

    public static <T> BooleanExpression toPredicate(Example<T> example, Class<T> entityClass) {
        PathBuilder<T> entityPath = new PathBuilder<>(entityClass, "entity");
        BooleanExpression predicate = null;

        for (var field : example.getProbe().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(example.getProbe());
                if (value != null) {
                    BooleanExpression fieldPredicate = entityPath.get(field.getName()).eq(value);
                    predicate = predicate == null ? fieldPredicate : predicate.and(fieldPredicate);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field value", e);
            }
        }

        return predicate;
    }

    public static BooleanExpression chainPredicate(BooleanExpression existingPredicate, BooleanExpression newPredicate, boolean useAnd) {
        if (existingPredicate == null) {
            return newPredicate;
        }
        if (newPredicate == null) {
            return existingPredicate;
        }
        return useAnd ? existingPredicate.and(newPredicate) : existingPredicate.or(newPredicate);
    }
}
