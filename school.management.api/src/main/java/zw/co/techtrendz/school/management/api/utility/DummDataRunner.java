package zw.co.techtrendz.school.management.api.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.SubjectType;
import zw.co.techtrendz.school.management.api.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DummDataRunner {

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassroomYearService classroomYearService;
    @Autowired
    private DormService dormService;
    @Autowired
    private DormYearService dormYearService;
    @Autowired
    private ExtraCurricularService extraCurricularService;
    @Autowired
    private ExtraCurricularYearService extraCurricularYearService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectYearService subjectYearService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    @Bean
    public ApplicationRunner insertDummyData() {
        return args -> {
            System.out.println("\n\nInserting dummy data...\n");
            this.dummyDataInsertion();
            System.out.println("\n\nDummy data successfully inserted.\n");
        };
    }

    private void dummyDataInsertion() {
        this.dummyPersons();
        this.dummTeachers();
        this.dummySubject();
        this.dummySubjectYear();
        this.dummyClassroom();
        this.dummyClassroomYear();
        this.dummyStudent();
        this.dummyUser();
        this.dummySchool();
        this.dummyDorm();
        this.dummyDormYear();
    }

    private void dummyPersons() {
        Person p1 = new Person();
//        p1.setId(1L);
        p1.setSalutation(Salutation.MR);
        p1.setFirstName("Arlington");
        p1.setOtherNames("Chikozho");
        p1.setLastName("Maridimuka");
        p1.setEmail("amaridimuka@mail.com");
        p1.setMobileNumber("0771234567");
        p1.setMobileNumber2("0771234568");
        p1.setTelNumber("08600230056");
        p1.setWhatsapp("0771234567");
        p1.setFacebook("https://www.facebook.com/arlington.maridimuka");
        p1.setLinkedin("https://www.linkedin.com/in/arlington-maridimuka-0b1a4a1b/");
        p1.setDateOfBirth(LocalDate.of(1972,7,8));

        Person p2 = new Person();
        p2.setSalutation(Salutation.MS);
        p2.setFirstName("Leah");
        p2.setOtherNames("Ane");
        p2.setLastName("Kandemwa");
        p2.setDateOfBirth(LocalDate.of(2010, 5, 15));

        Person p3 = new Person();
        p3.setSalutation(Salutation.MR);
        p3.setFirstName("Saka");
        p3.setOtherNames("Shingirai");
        p3.setLastName("Madzudzo");
        p3.setEmail("sakamadzudzo@mail.com");
        p3.setMobileNumber("0773702467");
        p3.setMobileNumber2("0719702467");
        p3.setWhatsapp("0773702467");
        p3.setLinkedin("https://www.linkedin.com/in/sakamadzudzo/");
        p3.setDateOfBirth(LocalDate.of(1970,8,1));

        List<Person> persons = Arrays.asList(
                p1,
                p2,
                p3
        );
        this.personService.saveAll(persons);
    }

    private void dummTeachers() {
        Teacher t1 = new Teacher();
//        t1.setId(1L);
        t1.setPerson(new Person(1L));

        List<Teacher> teachers = Arrays.asList(
                t1
        );
        this.teacherService.saveAll(teachers);
    }

    private void dummySubject() {
        Subject s1 = new Subject();
        s1.setName("Mathematics");
        s1.setDescription("Mathematics is the study of numbers, shapes, and patterns.");
        s1.setCode("MATH101");
        s1.setSyllabus("Mathematics Form 1 Syllabus");
        s1.setCurriculum("Mathematics Form 1 Curriculum");
        s1.setLevel(1);
        s1.setType(SubjectType.CORE);

        List<Subject> subjects = Arrays.asList(
                s1
        );
        this.subjectService.saveAll(subjects);
    }

    private void dummySubjectYear() {
        SubjectYear sy1 = new SubjectYear();
        sy1.setSubject(new Subject(1L));
        sy1.setCalendarYear(2023);
        sy1.setTeacher(new Teacher(1L));

        List<SubjectYear> subjectYears = Arrays.asList(
                sy1
        );
        this.subjectYearService.saveAll(subjectYears);
    }

    private void dummyClassroom() {
        Classroom c1 = new Classroom();
        c1.setName("Form 1A");
        c1.setDescription("Form 1A Classroom");

        List<Classroom> classrooms = Arrays.asList(
                c1
        );
        this.classroomService.saveAll(classrooms);
    }

    private void dummyClassroomYear() {
        ClassroomYear cy1 = new ClassroomYear();
        cy1.setClassroom(new Classroom(1L));
        cy1.setCalendarYear(2023);
        cy1.setSubjects(Arrays.asList(new SubjectYear(1L)));

        List<ClassroomYear> classroomYears = Arrays.asList(
                cy1
        );
        this.classroomYearService.saveAll(classroomYears);
    }

    private void dummyStudent() {
        Student s1 = new Student();
        s1.setPerson(new Person(1L));
        s1.setStartYear(2023);
        s1.setEndYear(2027);
        s1.setClassroom(new ClassroomYear(1L));

        List<Student> students = Arrays.asList(
                s1
        );
        this.studentService.saveAll(students);
    }

    private void dummyUser() {
        User u1 = new User();
        u1.setUsername("amaridimuka");
        u1.setPassword("password");
        u1.setPerson(new Person(1L));
        u1.setRoles(Arrays.asList(Role.TEACHER));
        u1.setEnabled(true);

        User u2 = new User();
        u2.setUsername("sakamadzudzo");
        u2.setPassword("password");
        u2.setPerson(new Person(3L));
        u2.setRoles(Arrays.asList(Role.ADMIN));
        u2.setEnabled(true);

        List<User> users = Arrays.asList(
                u1,
                u2
        );
        this.userService.saveAll(users);
        users.stream().map(User::getId).forEach(this.userService::enableUser);
    }

    private void dummySchool() {
        School s1 = new School();
        s1.setName("TechTrendz Academy");
        s1.setEmail("techtrendzacademy@mail.com");
        s1.setMobileNumber("0771534567");
        s1.setMobileNumber2("0771534568");
        s1.setTelNumber("08600530056");
        s1.setWebsite("https://www.techtrendzacademy.co.zw");
        s1.setAddress("123 Altruz Street, Harare, Zimbabwe");

        List<School> schools = Arrays.asList(
                s1
        );
        this.schoolService.saveAll(schools);
    }

    private void dummyDorm() {
        Dorm d1 = new Dorm();
        d1.setName("Dorm A");
        d1.setDescription("Dorm A Description");
        d1.setAddress("123 Altruz Street, Harare, Zimbabwe");
        d1.setSchool(new School(1L));

        List<Dorm> dorms = Arrays.asList(
                d1
        );
        this.dormService.saveAll(dorms);
    }

    private void dummyDormYear() {
        DormYear dy1 = new DormYear();
        dy1.setDorm(new Dorm(1L));
        dy1.setCalendarYear(2023);

        List<DormYear> dormYears = Arrays.asList(
                dy1
        );

        this.dormYearService.saveAll(dormYears);
    }
}
