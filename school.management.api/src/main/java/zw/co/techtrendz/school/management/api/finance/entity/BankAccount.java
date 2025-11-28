package zw.co.techtrendz.school.management.api.finance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.entity.Person;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing fee payment")
public class BankAccount extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the fee", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Schema(description = "Name of the account", example = "Mrs. Brown's EcoCash")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the account", example = "EcoCash account used by Mrs. Brown to settle school fees")
    private String description;

    @ManyToOne
//    @Column(nullable = false)
    @Schema(description = "Owner of the account", example = "Person{ \"id\": 71 }", requiredMode = Schema.RequiredMode.REQUIRED)
    private Person holder;

    @ManyToOne
//    @Column(nullable = false)
    @Schema(description = "Bank under which the account is registered", example = "Bank{ \"id\": 1 }", requiredMode = Schema.RequiredMode.REQUIRED)
    private Bank bank;

    @Schema(description = "Name of the bank's branch for the account", example = "Samora Machel branch")
    private String branch;

    @Schema(description = "Code used to represent the branch in the bank's internal systems", example = "KW-0001")
    private String branchCode;

    @Column(nullable = false)
    @Schema(description = "Complete account number", example = "6600024243", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountNumber;

    @ManyToOne
//    @Column(nullable = false)
    @Schema(description = "Base currency of the account", example = "Currency{ \"id\": 1 }", requiredMode = Schema.RequiredMode.REQUIRED)
    private Currency currency;
}
