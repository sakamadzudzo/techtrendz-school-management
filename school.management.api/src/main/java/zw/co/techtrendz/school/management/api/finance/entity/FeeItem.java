package zw.co.techtrendz.school.management.api.finance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a component of student's fee")
public class FeeItem extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the item line", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Name of the item line", example = "Swimming club", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the item line", example = "Required for providing support for swimming extra-curriculars")
    private String description;

    @NotEmpty(message = "Price cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Price of the item line", example = "20.4", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @NotEmpty(message = "Quantity cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Quantity of the item line", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal quantity;
}
