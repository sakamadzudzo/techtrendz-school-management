package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.SubjectType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectYearDto {

    private Long id;

    private Integer calendarYear;

    private String name;

    private String description;

    private String code;

    private SubjectType type;

    private String syllabus;

    private String curriculum;

    private Integer level;

    private String category;

    private Long teacherId;

    private TeacherDto teacher;

    private Long classroomId;

    public SubjectYearDto(
            Long id,
            Integer calendarYear,
            String name,
            String description,
            String code,
            SubjectType type,
            String syllabus,
            String curriculum,
            Integer level,
            String category,
            Long teacherId
    ) {
        this.id = id;
        this.calendarYear = calendarYear;
        this.name = name;
        this.description = description;
        this.code = code;
        this.type = type;
        this.syllabus = syllabus;
        this.curriculum = curriculum;
        this.level = level;
        this.category = category;
        this.teacherId = teacherId;
    }

    public SubjectYearDto(
            Long id,
            Integer calendarYear,
            String name,
            String description,
            String code,
            SubjectType type,
            String syllabus,
            String curriculum,
            Integer level,
            String category,
            Long teacherId,
            Long classroomId
    ) {
        this.id = id;
        this.calendarYear = calendarYear;
        this.name = name;
        this.description = description;
        this.code = code;
        this.type = type;
        this.syllabus = syllabus;
        this.curriculum = curriculum;
        this.level = level;
        this.category = category;
        this.teacherId = teacherId;
        this.classroomId = classroomId;
    }
}