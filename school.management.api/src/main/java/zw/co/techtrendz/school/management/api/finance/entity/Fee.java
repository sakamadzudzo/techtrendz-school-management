package zw.co.techtrendz.school.management.api.finance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing student's fee")
public class Fee extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the fee", example = "1")
    private Long id;

    @NotEmpty(message = "Student cannot be empty or null")
//    @Column(nullable = false)
    @Schema(description = "Student being billed", example = "Student{ \"id\": 1 }", requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne
    private Student student;

    @NotEmpty(message = "Calendar year cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Calendar year being billed for", example = "2025", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer calendarYear;

    @NotEmpty(message = "Fee items cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Components of the fee", example = "List<FeeItem>", requiredMode = Schema.RequiredMode.REQUIRED)
    @OneToMany
    private List<FeeItem> items = new ArrayList<>();

    public BigDecimal getTotal() {
        if (items.isEmpty()) return BigDecimal.ZERO;

        return items
                .parallelStream()
                .map(FeeItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
