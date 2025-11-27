package zw.co.techtrendz.school.management.api.finance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.finance.enums.KindType;
import zw.co.techtrendz.school.management.api.finance.enums.PaymentType;
import zw.co.techtrendz.school.management.api.finance.enums.ReferenceType;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing fee payment")
public class FeePayment extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the payment", example = "1")
    private Long id;

    @Column(nullable = false)
    @ManyToOne
    @Schema(description = "The student for whom payment is being done", example = "Student{ \"id\": 12 }", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Student student;

    @Column(nullable = false)
    @Schema(description = "The type of the payment", example = "PaymentType.SWIPE", requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentType paymentType;

    // Used only if payment type is electronic
    @ManyToOne
    @Schema(description = "Account making the electronic payment", example = "BankAccount{ \"id\": 23 }")
    private BankAccount payingAccount;

    @ManyToOne
    @Schema(description = "Account receiving the amount of the electronic payment", example = "BankAccount{ \"id\": 43 }")
    private BankAccount receivingAccount;

    @Schema(description = "Type of reference of the electronic payment", example = "ReferenceType.TRANSACTION_ID")
    private ReferenceType referenceType;

    @Column(unique = true)
    @Schema(description = "Reference of the electronic payment", example = "TRANS1xN:00012")
    private String reference;

    // Used only if payment type is kind
    @Schema(description = "Type of kind if paid in kind", example = "KindType.CATTLE")
    private KindType kindType;

    @ManyToOne
    @Column(nullable = false)
    @Schema(description = "Actual currency used in the payment", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
    private Currency currency;

    @Column(nullable = false)
    @Schema(description = "Amount of the payment", example = "520.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Column(nullable = false)
    @Schema(description = "Date of the payment", example = "2025-11-24", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate paymentDate;

    @Schema(description = "Time of the payment", example = "10:30")
    private LocalTime paymentTime;

    private Boolean verified;

    public FeePayment(
            Long id,
            Student student,
            PaymentType paymentType,
            BankAccount payingAccount,
            BankAccount receivingAccount,
            ReferenceType referenceType,
            String reference,
            KindType kindType,
            Currency currency,
            BigDecimal amount,
            LocalDate paymentDate,
            LocalTime paymentTime
    ) {
        this.id = id;
        this.student = student;
        this.paymentType = paymentType;
        this.payingAccount = payingAccount;
        this.receivingAccount = receivingAccount;
        this.referenceType = referenceType;
        this.reference = reference;
        this.kindType = kindType;
        this.currency = currency;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
    }
}
