package zw.co.techtrendz.school.management.api.common.utility;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpecificationUtils {

    public static <T> Specification<T> notMatchingExample(Example<T> example) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Get the probe object (example entity) and its fields
            T probe = example.getProbe();
            Class<?> probeClass = probe.getClass();

            for (java.lang.reflect.Field field : probeClass.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(probe);
                    if (value != null) {
                        predicates.add(criteriaBuilder.equal(root.get(field.getName()), value));
                    }
//                    else {
//                        predicates.add(criteriaBuilder.isNotNull(root.get(field.getName())));
//                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field: " + field.getName(), e);
                }
            }

            // Combine predicates with AND and negate them
            return criteriaBuilder.not(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        };
    }

    public static <T> Specification<T> dateBetween(String fieldName, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            Path<LocalDateTime> field = root.get(fieldName);
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(field, startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(field, startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(field, endDate);
            }
            return criteriaBuilder.conjunction(); // No filtering if both are null
        };
    }

    public static <T> Specification<T> bigDecimalBetween(String fieldName, BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) -> {
            Path<BigDecimal> field = root.get(fieldName);
            if (min != null && max != null) {
                return criteriaBuilder.between(field, min, max);
            } else if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(field, min);
            } else if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(field, max);
            }
            return criteriaBuilder.conjunction(); // No filtering if both are null
        };
    }

    public static <T> Specification<T> integerBetween(String fieldName, Integer min, Integer max) {
        return (root, query, criteriaBuilder) -> {
            Path<Integer> field = root.get(fieldName);
            if (min != null && max != null) {
                return criteriaBuilder.between(field, min, max);
            } else if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(field, min);
            } else if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(field, max);
            }
            return criteriaBuilder.conjunction(); // No filtering if both are null
        };
    }

    public static <T> Specification<T> doubleBetween(String fieldName, Double min, Double max) {
        return (root, query, criteriaBuilder) -> {
            Path<Double> field = root.get(fieldName);
            if (min != null && max != null) {
                return criteriaBuilder.between(field, min, max);
            } else if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(field, min);
            } else if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(field, max);
            }
            return criteriaBuilder.conjunction(); // No filtering if both are null
        };
    }

    public static <T> Specification<T> exactMatch(String fieldName, Object value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction(); // No filtering if value is null
            }

            String[] pathParts = fieldName.split("\\.");
            From<?, ?> from = root;  // Start from the root

            for (int i = 0; i < pathParts.length - 1; i++) {
                from = from.join(pathParts[i]);  // Join each part except the last one
            }

            Path<?> path = from.get(pathParts[pathParts.length - 1]);  // Get the final field
            return criteriaBuilder.equal(path, value);
        };
    }

    public static <T> Specification<T> likeMatch(String fieldName, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering if value is null/empty
            }

            // Handle nested properties (e.g., "client.name")
            if (fieldName.contains(".")) {
                String[] path = fieldName.split("\\.");
                return criteriaBuilder.like(criteriaBuilder.lower(root.join(path[0]).get(path[1])), "%" + value.toLowerCase() + "%");
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        };
    }

    public static <T> Specification<T> isNotNull(String fieldName) {
        return (root, query, criteriaBuilder) -> {
            // Handle nested properties (e.g., "allocation.id")
            if (fieldName.contains(".")) {
                String[] path = fieldName.split("\\.");
                return criteriaBuilder.isNotNull(root.join(path[0]).get(path[1]));
            }

            return criteriaBuilder.isNotNull(root.get(fieldName));
        };
    }

    public static <T> Specification<T> isNull(String fieldName) {
        return (root, query, criteriaBuilder) -> {
            // Handle nested properties (e.g., "allocation.id")
            if (fieldName.contains(".")) {
                String[] path = fieldName.split("\\.");
                return criteriaBuilder.isNull(root.join(path[0]).get(path[1]));
            }

            return criteriaBuilder.isNull(root.get(fieldName));
        };
    }

}
