package zw.co.techtrendz.school.management.api.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.academic.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;

import java.time.Year;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for data to use in reports filtering dropdowns")
public class ReportsFilters {

    private List<YearDropdown> students;

    private List<YearDropdown> teachers;

    private List<CourseworkDropdown> courseworks;

    private List<YearDropdown> classrooms;

    private List<YearDropdown> subjects;

    private List<YearDropdown> extraCurriculars;

    @Schema(description = "Calendar years derived from all years used in subject years, or from current going back 10 years")
    private List<Integer> calendarYears = new ArrayList<>();

    public List<Integer> getCalendarYears() {
        if (this.calendarYears != null && !this.calendarYears.isEmpty()) {
            return this.calendarYears;
        }

        Set<Integer> calendarYears = new HashSet<>();
        if (this.students != null && !this.students.isEmpty()) {
            Set<Integer> temp = this.students
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(YearDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (this.teachers != null && !this.teachers.isEmpty()) {
            Set<Integer> temp = this.teachers
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(YearDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (this.courseworks != null && !this.courseworks.isEmpty()) {
            Set<Integer> temp = this.courseworks
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(CourseworkDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (this.classrooms != null && !this.classrooms.isEmpty()) {
            Set<Integer> temp = this.classrooms
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(YearDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (this.subjects != null && !this.subjects.isEmpty()) {
            Set<Integer> temp = this.subjects
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(YearDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (this.extraCurriculars != null && !this.extraCurriculars.isEmpty()) {
            Set<Integer> temp = this.extraCurriculars
                    .parallelStream()
                    .filter(item -> item.getCalendarYear() != null)
                    .mapToInt(YearDropdown::getCalendarYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        if (calendarYears.isEmpty()) {
            int currentYear = Year.now().getValue();
            Set<Integer> temp = IntStream
                    .rangeClosed(currentYear - 10, currentYear)
                    .boxed()
                    .collect(Collectors.toCollection(
                            CopyOnWriteArraySet::new
                    ));
            calendarYears.addAll(temp);
        }

        this.calendarYears = new ArrayList<>(calendarYears);
        Collections.sort(this.calendarYears);
        return this.calendarYears;
    }
}