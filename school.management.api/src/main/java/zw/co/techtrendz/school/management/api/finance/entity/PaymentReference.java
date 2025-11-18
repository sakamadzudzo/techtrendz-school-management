package zw.co.techtrendz.school.management.api.finance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.finance.enums.ReferenceType;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity recording references of all received electronic payments")
public class PaymentReference extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the fee", example = "1")
    private Long id;

    @Schema(description = "Type of reference of an electronic payment", example = "ReferenceType.TRANSACTION_ID")
    private ReferenceType referenceType;

    @Column(nullable = false)
    @Schema(description = "Reference of an electronic payment", example = "TRANS1xN:00012")
    private String reference;
}
