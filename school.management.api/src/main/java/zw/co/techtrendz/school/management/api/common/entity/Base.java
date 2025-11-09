package zw.co.techtrendz.school.management.api.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.common.enums.Status;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@Schema(description = "Base entity containing common fields for all domain entities")
public abstract class Base {

    @Schema(description = "Timestamp when the entity was created", example = "2024-01-15T10:30:00")
    private LocalDateTime datestamp = LocalDateTime.now();

    @Schema(description = "Current status of the entity", example = "ACTIVE")
    private Status status = Status.ACTIVE;
}
