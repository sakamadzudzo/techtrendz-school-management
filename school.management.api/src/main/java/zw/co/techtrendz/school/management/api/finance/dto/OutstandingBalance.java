package zw.co.techtrendz.school.management.api.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingBalance {

    public Student student;

    public BigDecimal balance;
}
