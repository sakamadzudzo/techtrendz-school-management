package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class Base {

    private LocalDateTime datestamp = LocalDateTime.now();

    private Status status = Status.ACTIVE;
}
