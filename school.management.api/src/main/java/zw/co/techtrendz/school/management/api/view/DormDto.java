package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormDto {

    private Long id;

    private String name;

    private String description;

    private String address;

    private Status status;

    private LocalDateTime datestamp;

    private SchoolDto school;
}
