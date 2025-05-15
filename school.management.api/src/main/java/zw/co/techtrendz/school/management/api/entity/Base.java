package zw.co.techtrendz.school.management.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Base {

    private LocalDateTime datestamp = LocalDateTime.now();

    private Status status = Status.ACTIVE;
}
