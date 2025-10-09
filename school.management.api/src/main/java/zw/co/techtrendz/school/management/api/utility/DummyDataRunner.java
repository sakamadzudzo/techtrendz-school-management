package zw.co.techtrendz.school.management.api.utility;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.enums.*;
import zw.co.techtrendz.school.management.api.service.*;
import zw.co.techtrendz.school.management.api.view.ExtraCurricularYearDto;
import zw.co.techtrendz.school.management.api.view.StudentDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DummyDataRunner {

    private final ClassroomService classroomService;
    private final ClassroomYearService classroomYearService;
    private final DormService dormService;
    private final DormYearService dormYearService;
    private final ExtraCurricularService extraCurricularService;
    private final ExtraCurricularYearService extraCurricularYearService;
    private final PersonService personService;
    private final SchoolService schoolService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final SubjectYearService subjectYearService;
    private final TeacherService teacherService;
    private final UserService userService;
    private final CourseworkService courseworkService;
    private final ActivityPointService activityPointService;
    private final RatingService ratingService;
    private final MarkService markService;

    // Simulation range: 5 years
    private final List<Integer> CALENDAR_YEARS = IntStream.rangeClosed(2021, 2025).boxed().collect(Collectors.toList());

    // Deterministic persons for user accounts
    private Person adminPerson; // username: sakamadzudzo
    private final List<Person> teacherAccountPersons = new ArrayList<>(); // 5 teachers
    private final List<Person> parentAccountPersons = new ArrayList<>(); // 3 parents
    private final List<Person> staffAccountPersons = new ArrayList<>(); // 2 staff

    // Saved school and dorms (to avoid hardcoded IDs)
    private School savedSchool;
    private final List<Dorm> savedDorms = new ArrayList<>();

    public DummyDataRunner(
            ClassroomService classroomService,
            ClassroomYearService classroomYearService,
            DormService dormService,
            DormYearService dormYearService,
            ExtraCurricularService extraCurricularService,
            ExtraCurricularYearService extraCurricularYearService,
            PersonService personService,
            SchoolService schoolService,
            StudentService studentService,
            SubjectService subjectService,
            SubjectYearService subjectYearService,
            TeacherService teacherService,
            UserService userService,
            CourseworkService courseworkService,
            ActivityPointService activityPointService,
            RatingService ratingService,
            MarkService markService
    ) {
        this.classroomService = classroomService;
        this.classroomYearService = classroomYearService;
        this.dormService = dormService;
        this.dormYearService = dormYearService;
        this.extraCurricularService = extraCurricularService;
        this.extraCurricularYearService = extraCurricularYearService;
        this.personService = personService;
        this.schoolService = schoolService;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.subjectYearService = subjectYearService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.courseworkService = courseworkService;
        this.activityPointService = activityPointService;
        this.ratingService = ratingService;
        this.markService = markService;
    }

    @Bean
    public ApplicationRunner insertDummyData() {
        return args -> {
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy data...");
            this.dummyDataInsertion();
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Dummy data successfully inserted.");
        };
    }

    private void dummyDataInsertion() {
        // Fast devtools restarts: if data already seeded, skip reseeding on restart
        try {
            boolean hasUsers = !this.userService.getAll(0, 1, Arrays.asList("id"), Sort.Direction.ASC).getContent().isEmpty();
            boolean hasSchools = !this.schoolService.getAll(0, 1, Arrays.asList("id"), Sort.Direction.ASC).getContent().isEmpty();
            boolean hasSubjects = !this.subjectService.getAll(0, 1, Arrays.asList("id"), Sort.Direction.ASC).getContent().isEmpty();
            if (hasUsers || hasSchools || hasSubjects) {
                Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Dummy data detected; skipping reseed for devtools restart.");
                return;
            }
        } catch (Exception e) {
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.WARNING, "Seed pre-check failed; proceeding with seeding.", e);
        }

        this.dummyPersons();
        this.dummyTeachers();
        this.dummySubject();
        this.dummySubjectYear();
        this.dummyClassroom();
        this.dummyClassroomYear();
        this.dummyUser();
        this.dummySchool();
        this.dummyDorm();
        this.dummyDormYear();
        this.dummyStudent();
        this.dummyExtraCurricular();
        this.dummyExtraCurricularYear();
        this.dummyCoursework();
        this.dummyActivityPoint();
        this.dummyRating();
    }

    private void dummyPersons() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy persons...");
        // Existing base persons
        Person p1 = new Person();
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
        p1.setDateOfBirth(LocalDate.of(1972, 7, 8));

        Person p2 = new Person();
        p2.setSalutation(Salutation.MS);
        p2.setFirstName("Leah");
        p2.setOtherNames("Ane");
        p2.setLastName("Kandemwa");
        p2.setEmail("leah.a.kandemwa@mail.com");
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
        p3.setDateOfBirth(LocalDate.of(1970, 8, 1));

        // Deterministic teacher persons (5 total including Arlington)
        Person t2 = buildPerson(Salutation.MR, "Tendai", "Blessing", "Nyathi", "tendai.nyathi@schoolmail.ac.zw", LocalDate.of(1985, 3, 14), "0772012345");
        Person t3 = buildPerson(Salutation.MS, "Thandeka", "Sithandekile", "Moyo", "thandeka.moyo@schoolmail.ac.zw", LocalDate.of(1987, 6, 9), "0772987654");
        Person t4 = buildPerson(Salutation.MR, "Brian", "Tawanda", "Dube", "brian.dube@schoolmail.ac.zw", LocalDate.of(1982, 11, 23), "0712123456");
        Person t5 = buildPerson(Salutation.MS, "Vimbai", "Rudo", "Sibanda", "vimbai.sibanda@schoolmail.ac.zw", LocalDate.of(1989, 8, 3), "0783456789");

        // Deterministic parent persons (3)
        Person pr1 = buildPerson(Salutation.MR, "Farai", "Tonderai", "Chigumba", "farai.chigumba@parentmail.co.zw", LocalDate.of(1978, 4, 20), "0774400123");
        Person pr2 = buildPerson(Salutation.MS, "Lindiwe", "Nomusa", "Ncube", "lindiwe.ncube@parentmail.co.zw", LocalDate.of(1980, 12, 1), "0774400456");
        Person pr3 = buildPerson(Salutation.MR, "Tinashe", "Munesu", "Mpofu", "tinashe.mpofu@parentmail.co.zw", LocalDate.of(1976, 2, 5), "0714400789");

        // Deterministic staff persons (2)
        Person st1 = buildPerson(Salutation.MR, "Admire", "Kudakwashe", "Moyo", "admire.moyo@schoolmail.ac.zw", LocalDate.of(1975, 7, 12), "0775500123");
        Person st2 = buildPerson(Salutation.MS, "Patricia", "Nyasha", "Mugabe", "patricia.mugabe@schoolmail.ac.zw", LocalDate.of(1983, 9, 30), "0775500456");

        // Save persons; rely on JPA to set IDs back on entity instances
        this.personService.save(p1);
        this.personService.save(p2);
        this.personService.save(p3);
        this.personService.save(t2);
        this.personService.save(t3);
        this.personService.save(t4);
        this.personService.save(t5);
        this.personService.save(pr1);
        this.personService.save(pr2);
        this.personService.save(pr3);
        this.personService.save(st1);
        this.personService.save(st2);

        // Assign to holder fields for later linkage
        this.adminPerson = p3; // For admin username 'sakamadzudzo'
        this.teacherAccountPersons.add(p1); // Arlington will be a teacher with user 'amaridimuka'
        this.teacherAccountPersons.add(t2);
        this.teacherAccountPersons.add(t3);
        this.teacherAccountPersons.add(t4);
        this.teacherAccountPersons.add(t5);
        this.parentAccountPersons.add(pr1);
        this.parentAccountPersons.add(pr2);
        this.parentAccountPersons.add(pr3);
        this.staffAccountPersons.add(st1);
        this.staffAccountPersons.add(st2);
    }

    private Person buildPerson(Salutation salutation, String firstName, String otherNames, String lastName, String email, LocalDate dob, String mobile) {
        Person p = new Person();
        p.setSalutation(salutation);
        p.setFirstName(firstName);
        p.setOtherNames(otherNames);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setMobileNumber(mobile);
        p.setWhatsapp(mobile);
        p.setDateOfBirth(dob);
        p.setFacebook("https://www.facebook.com/" + firstName.toLowerCase() + lastName.toLowerCase());
        p.setLinkedin("https://www.linkedin.com/in/" + firstName.toLowerCase() + "-" + lastName.toLowerCase());
        return p;
    }

    private void dummyTeachers() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy teachers...");
        List<Teacher> teachers = new ArrayList<>();

        // Create Teacher records for deterministic teacher persons
        for (Person tp : this.teacherAccountPersons) {
            Teacher t = new Teacher();
            t.setPerson(tp);
            teachers.add(t);
        }

        // Additional randomly generated teachers for variety
        String[] firstNames = {
                // Shona
                "Tinotenda", "Tafadzwa", "Rufaro", "Tinashe", "Kudzanai", "Tawana", "Tariro", "Anotida", "Chengeto", "Takunda",
                "Nyasha", "Munyaradzi", "Vimbai", "Runako", "Ruvimbo", "Fadzai", "Tanatswa", "Tanyaradzwa", "Shamiso", "Tadiwanashe",
                "Chiedza", "Nomatter", "Natsai", "Takudzwa", "Tendekai", "Ruramai", "Munashe", "Farirai", "Kushinga", "Nyengeterai",

                // Ndebele
                "Thulisile", "Bonginkosi", "Siphosenkosi", "Lungile", "Sithembiso", "Nomathemba", "Khulekani", "Thembelihle", "Sithandazile", "Mpumelelo",
                "Zanele", "Sibusisiwe", "Nqobile", "Gugulethu", "Nkosinathi", "Thandeka", "Sibusiso", "Mthandazo", "Mlungisi", "Khanyisani",

                // English
                "Jordan", "Melissa", "Trevor", "Chloe", "Dylan", "Nicole", "Liam", "Alyssa", "Ethan", "Grace",
                "Logan", "Vanessa", "Nathan", "Madison", "Megan", "Kyle", "Brenda", "Noah", "Rachel", "Jonathan"
        };
        String[] lastNames = {
                // Shona & Ndebele
                "Makwavarara", "Mutambanengwe", "Chinyere", "Mugabe", "Mapuranga", "Muzenda", "Nyandoro", "Zhou", "Matavire", "Chindori",
                "Mutasa", "Chiromo", "Mpofu", "Dlodlo", "Chigumba", "Mashakada", "Sibanda", "Mahlangu", "Chikore", "Nkomo",

                // English / Anglicized
                "Smith", "Johnson", "Miller", "Anderson", "Richards", "Gray", "Adams", "Marshall", "Perry", "Young"
        };

        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            Person person = new Person();
            person.setFirstName(firstNames[AppUtils.getRandom(0, 70)]);
            person.setLastName(lastNames[AppUtils.getRandom(0, 30)]);
            int otherNameLotto = AppUtils.getRandom(0, 93);
            person.setOtherNames(otherNameLotto < 70 ? firstNames[otherNameLotto] : "");
            person.setSalutation(random.nextBoolean() ? Salutation.MR : Salutation.MS);
            person.setDateOfBirth(LocalDate.of(1980 + random.nextInt(15), 1 + random.nextInt(12), 1 + random.nextInt(28)));
            person.setEmail(person.getFirstName().toLowerCase() + "." + person.getLastName().toLowerCase() + "@schoolmail.ac.zw");
            person.setMobileNumber("07" + (10000000 + random.nextInt(9000000)));
            person.setFacebook("https://www.facebook.com/" + person.getFirstName().toLowerCase() + person.getLastName().toLowerCase());
            person.setLinkedin("https://www.linkedin.com/in/" + person.getFirstName().toLowerCase() + "-" + person.getLastName().toLowerCase());
            if (person.getMobileNumber() != null) {
                person.setWhatsapp(person.getMobileNumber());
            }

            Teacher teacher = new Teacher();
            teacher.setPerson(person);
            teachers.add(teacher);
        }

        this.teacherService.saveAll(teachers);
    }

    private void dummySubject() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy subjects...");
        List<Subject> subjects = new ArrayList<>();

        // Define subjects with level and type
        subjects.add(createSubject("Mathematics", "MATH101", 1, SubjectType.CORE));
        subjects.add(createSubject("English Language", "ENG101", 1, SubjectType.CORE));
        subjects.add(createSubject("Shona", "SHO101", 1, SubjectType.CORE));
        subjects.add(createSubject("Science", "SCI101", 1, SubjectType.CORE));
        subjects.add(createSubject("Heritage Studies", "HST101", 1, SubjectType.CORE));
        subjects.add(createSubject("Physical Education", "PE101", 1, SubjectType.CORE));
        subjects.add(createSubject("Agriculture", "AGR101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Computer Science", "CS101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Commerce", "COM101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Geography", "GEO101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("History", "HIS101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Religious Studies", "RS101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Art and Design", "ART101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Technical Graphics", "TG101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Building Technology", "BT101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Metalwork", "MW101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Woodwork", "WW101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Food and Nutrition", "FN101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Fashion and Fabrics", "FF101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("French", "FR101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Ndebele", "NDE101", 1, SubjectType.ELECTIVE));
        subjects.add(createSubject("Business Enterprise Skills", "BES101", 1, SubjectType.ELECTIVE));

        this.subjectService.saveAll(subjects);
    }

    private Subject createSubject(String name, String code, int level, SubjectType type) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setCode(code);
        subject.setLevel(level);
        subject.setType(type);
        subject.setDescription(name + " is a subject in the school curriculum.");
        subject.setSyllabus(name + " Form " + level + " Syllabus");
        subject.setCurriculum(name + " Form " + level + " Curriculum");
        if (code.equals("BES101")) subject.setStatus(Status.DISABLED);
        return subject;
    }


    private void dummySubjectYear() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy subject years...");
        List<Subject> subjects = this.subjectService.getAll(0, 100, Arrays.asList("name"), Sort.Direction.DESC).getContent();
        List<Teacher> teachers = this.teacherService.getAll(0, 200, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<SubjectYear> subjectYears = new ArrayList<>();

        for (Subject subject : subjects) {
            for (Integer year : CALENDAR_YEARS) {
                SubjectYear sy = new SubjectYear();
                sy.setSubject(subject);
                sy.setCalendarYear(year);
                sy.setTeacher(teachers.get(AppUtils.getRandom(0, teachers.size() - 1)));
                if (subject.getCode().equals("BES101") && year.equals(CALENDAR_YEARS.get(0))) {
                    sy.setStatus(Status.DISABLED);
                }
                subjectYears.add(sy);
            }
        }

        this.subjectYearService.saveAll(subjectYears);
    }


    private void dummyClassroom() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy classrooms...");
        List<Classroom> classrooms = new ArrayList<>();

        // Form 1 to Form 4 with sections A to E
        for (int form = 1; form <= 4; form++) {
            for (char section = 'A'; section <= 'E'; section++) {
                Classroom classroom = new Classroom();
                String name = "Form " + form + section;
                classroom.setName(name);
                classroom.setDescription(name + " Classroom");
                classrooms.add(classroom);
            }
        }

        // Form 5 and Form 6 streams
        String[] upperStreams = {"Science", "Arts", "Commercial", "Technical", "Agriculture"};

        for (int form = 5; form <= 6; form++) {
            for (String stream : upperStreams) {
                Classroom classroom = new Classroom();
                String name = "Form " + form + " " + stream;
                classroom.setName(name);
                classroom.setDescription(name + " Classroom");
                classrooms.add(classroom);
            }
        }

        // Optional special classes
        String[] specialClasses = {"Resource Room", "Remedial Class", "Special Education Unit"};
        for (String special : specialClasses) {
            Classroom classroom = new Classroom();
            classroom.setName(special);
            classroom.setDescription(special + " for support and inclusive education");
            classrooms.add(classroom);
        }

        this.classroomService.saveAll(classrooms);
    }

    private void dummyClassroomYear() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy classroom years...");
        List<Classroom> classrooms = this.classroomService.getAll(0, 200, Arrays.asList("name"), Sort.Direction.DESC).getContent();
        List<SubjectYear> subjectYears = this.subjectYearService.getAll(0, 1000, Arrays.asList("subject.name"), Sort.Direction.DESC).getContent();
        List<Teacher> teachers = this.teacherService.getAll(0, 200, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<ClassroomYear> classroomYears = new ArrayList<>();

        for (Classroom classroom : classrooms) {
            for (Integer year : CALENDAR_YEARS) {
                final Integer calendarYear = year;
                ClassroomYear cy = new ClassroomYear();
                cy.setClassroom(classroom);
                cy.setCalendarYear(year);
                cy.setSubjects(
                        subjectYears
                                .parallelStream()
                                .filter(
                                        subjectYear -> subjectYear.getCalendarYear().equals(calendarYear)
                                )
                                .collect(
                                        Collectors.toCollection(CopyOnWriteArrayList::new)
                                )
                );
                cy.setHomeroomTeacher(teachers.get(AppUtils.getRandom(0, teachers.size() - 1)));
                classroomYears.add(cy);
            }
        }

        this.classroomYearService.saveAll(classroomYears);
    }

    private void dummyStudent() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy students...");

        List<ClassroomYear> classroomYears = classroomYearService.getAll(0, 2000, Arrays.asList("classroom.name", "calendarYear"), Sort.Direction.DESC).getContent();
        List<DormYear> dormYears = dormYearService.getAll(0, 200, Arrays.asList("calendarYear"), Sort.Direction.ASC).getContent();
        List<Student> students = new ArrayList<>();

        String[] firstNames = {
                // Shona
                "Tariro", "Tendai", "Rudo", "Kuda", "Chipo", "Nyasha", "Tatenda", "Rumbidzai", "Simba", "Takudzwa",
                "Farai", "Kudzai", "Tanaka", "Vimbai", "Anesu", "Munyaradzi", "Tafadzwa", "Chenai", "Tanyaradzwa", "Nyengeterai",
                "Shingai", "Ruramai", "Tichawona", "Runako", "Tinotenda", "Fadzai", "Nyengeterai", "Tawananyasha", "Tadiwanashe", "Munashe",
                "Natsai", "Rutendo", "Tadiwa", "Tawana", "Tino", "Tashinga", "Nyarai", "Tafara", "Kudakwashe", "Tanyaradzwa",

                // Ndebele
                "Thandeka", "Bongani", "Nokuthula", "Sibusiso", "Nkosazana", "Mthokozisi", "Thabani", "Sithandekile", "Thembinkosi", "Nomathemba",
                "Lindiwe", "Nqobile", "Sipho", "Gugulethu", "Zanele", "Themba", "Khanyisile", "Mlungisi", "Nomvula", "Sifiso",

                // English / Mixed
                "Ashley", "Brandon", "Courtney", "Daniel", "Emily", "Faith", "George", "Hannah", "Isaac", "Jessica",
                "Kevin", "Lisa", "Michael", "Natalie", "Oliver", "Paige", "Quinton", "Rachel", "Sean", "Tanya",
                "Ursula", "Victor", "Wendy", "Xavier", "Yvonne", "Zachary", "Angela", "Brian", "Cynthia", "Derrick"
        };
        String[] lastNames = {
                // Zimbabwean (Shona/Ndebele dominant)
                "Chirwa", "Moyo", "Nyoni", "Dube", "Chikafu", "Gumbo", "Mutsvangwa", "Mtetwa", "Makoni", "Zhou",
                "Sibanda", "Marufu", "Banda", "Ncube", "Chingono", "Madzima", "Chihwai", "Maphosa", "Marechera", "Tsvangirai",
                "Hove", "Chimombe", "Chinamasa", "Zvobgo", "Mutasa", "Chiwenga", "Chikowore", "Nhema", "Mazvimbakupa", "Kasukuwere",
                "Mahere", "Masiyiwa", "Matemadanda", "Machingauta", "Madzokere", "Wadyajena", "Chiheru", "Kwaramba", "Chigudu", "Gwisai",
                "Dzvairo", "Tandi", "Kasukuwere", "Chikwama", "Shava", "Muchena", "Rugare", "Munyai", "Bvute", "Mujuru",

                // English/Anglicized mix
                "Brown", "Taylor", "White", "Scott", "Grant", "Evans", "Nelson", "Foster", "Morgan", "Chambers",
                "Clark", "Lewis", "Walker", "Roberts", "Reid", "Wright", "Baker", "Mason", "Stewart", "Campbell"
        };

        int studentCount = 250; // Simulate a medium-sized Group A school
        Random random = new Random();

        for (int i = 0; i < studentCount; i++) {
            String firstName = firstNames[AppUtils.getRandom(0, 90)];
            String lastName = lastNames[AppUtils.getRandom(0, 70)];
            int otherNameLotto = AppUtils.getRandom(0, 103);
            String otherName = otherNameLotto < 90 ? firstNames[otherNameLotto] : "";

            Person person = new Person();
            person.setSalutation(random.nextBoolean() ? Salutation.MR : Salutation.MS);
            person.setFirstName(firstName);
            person.setOtherNames(otherName);
            person.setLastName(lastName);
            person.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@studentmail.co.zw");
            person.setMobileNumber("07" + (10000000 + random.nextInt(9000000)));
            person.setDateOfBirth(LocalDate.of(2006 + (i % 5), 1 + (i % 12), 1 + (i % 28)));

            Student student = new Student();
            student.setPerson(person);

            // Choose an enrollment year within the simulation range
            int startYear = CALENDAR_YEARS.get(AppUtils.getRandom(0, CALENDAR_YEARS.size() - 1));
            student.setStartYear(startYear);
            student.setEndYear(startYear + 4);

            // Assign a classroomYear matching the student's startYear
            List<ClassroomYear> possibleCY = classroomYears.stream()
                    .filter(cy -> cy.getCalendarYear() != null && cy.getCalendarYear().equals(startYear))
                    .collect(Collectors.toList());
            if (possibleCY.isEmpty()) possibleCY = classroomYears; // fallback
            ClassroomYear classroomYear = possibleCY.get(AppUtils.getRandom(0, possibleCY.size() - 1));
            student.setClassroom(classroomYear);

            // Assign a dorm for the same year (or any if not found)
            int targetYear = startYear;
            List<DormYear> possibleDY = dormYears.stream()
                    .filter(dy -> dy.getCalendarYear() != null && dy.getCalendarYear().equals(targetYear))
                    .collect(Collectors.toList());
            DormYear dormYear = possibleDY.isEmpty() ? dormYears.get(0) : possibleDY.get(AppUtils.getRandom(0, possibleDY.size() - 1));
            student.setDorm(dormYear);

            // Assign school
            if (this.savedSchool == null) {
                // attempt get any saved school
                List<School> schools = this.schoolService.getAll(0, 1, Arrays.asList("id"), Sort.Direction.DESC).getContent();
                if (!schools.isEmpty()) this.savedSchool = schools.get(0);
            }
            if (this.savedSchool != null) {
                student.setSchool(this.savedSchool);
            }

            students.add(student);
        }

        this.studentService.saveAll(students);
    }

    private void dummyUser() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy users...");
        List<User> users = new ArrayList<>();

        // Admin (leave details as is)
        User admin = new User();
        admin.setUsername("sakamadzudzo");
        admin.setPassword("password");
        admin.setPerson(this.adminPerson);
        admin.setRoles(Arrays.asList(Role.ADMIN));
        admin.setEnabled(true);
        users.add(admin);

        // Five teacher accounts (deterministic)
        // 1) Arlington Maridimuka
        User t1 = new User();
        t1.setUsername("amaridimuka");
        t1.setPassword("password");
        t1.setPerson(this.teacherAccountPersons.get(0));
        t1.setRoles(Arrays.asList(Role.TEACHER));
        t1.setEnabled(true);
        users.add(t1);

        // 2..5) Others
        String[] teacherUsernames = {"tnyathi", "tmoyo", "bdube", "vsibanda"};
        for (int i = 1; i < this.teacherAccountPersons.size(); i++) {
            User ut = new User();
            ut.setUsername(teacherUsernames[i - 1]);
            ut.setPassword("password");
            ut.setPerson(this.teacherAccountPersons.get(i));
            ut.setRoles(Arrays.asList(Role.TEACHER));
            ut.setEnabled(true);
            users.add(ut);
        }

        // Three parent accounts
        String[] parentUsernames = {"fchigumba", "lncube", "tmpofu"};
        for (int i = 0; i < this.parentAccountPersons.size(); i++) {
            User up = new User();
            up.setUsername(parentUsernames[i]);
            up.setPassword("password");
            up.setPerson(this.parentAccountPersons.get(i));
            up.setRoles(Arrays.asList(Role.PARENT));
            up.setEnabled(true);
            users.add(up);
        }

        // Two staff accounts
        String[] staffUsernames = {"admiremoyo", "pmugabe"};
        for (int i = 0; i < this.staffAccountPersons.size(); i++) {
            User us = new User();
            us.setUsername(staffUsernames[i]);
            us.setPassword("password");
            us.setPerson(this.staffAccountPersons.get(i));
            us.setRoles(Arrays.asList(Role.STAFF));
            us.setEnabled(true);
            users.add(us);
        }

        this.userService.saveAll(users);
        users.parallelStream().map(User::getId).forEach(this.userService::enableUser);
    }

    private void dummySchool() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy schools...");
        School s1 = new School();
        s1.setName("Mukuvisi Group A School");
        s1.setEmail("info@mukuvisi.edu.zw");
        s1.setMobileNumber("0771534567");
        s1.setMobileNumber2("0771534568");
        s1.setTelNumber("0242 705321");
        s1.setWebsite("https://www.mukuvisi.edu.zw");
        s1.setAddress("12 Chancellor Avenue, Harare, Zimbabwe");

        List<School> schools = Arrays.asList(s1);
        this.schoolService.saveAll(schools);
        this.savedSchool = s1;
    }

    private void dummyDorm() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy dorms...");
        Dorm d1 = new Dorm();
        d1.setName("Dambudzo House");
        d1.setDescription("Dambudzo House - Boys");
        d1.setAddress("12 Chancellor Avenue, Harare, Zimbabwe");
        d1.setSchool(this.savedSchool != null ? this.savedSchool : new School(1L));

        Dorm d2 = new Dorm();
        d2.setName("Chaminuka House");
        d2.setDescription("Chaminuka House - Girls");
        d2.setAddress("12 Chancellor Avenue, Harare, Zimbabwe");
        d2.setSchool(this.savedSchool != null ? this.savedSchool : new School(1L));

        List<Dorm> dorms = Arrays.asList(d1, d2);
        this.dormService.saveAll(dorms);
        this.savedDorms.clear();
        this.savedDorms.addAll(dorms);
    }

    private void dummyDormYear() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy dorm years...");
        List<DormYear> dormYears = new ArrayList<>();
        if (this.savedDorms.isEmpty()) {
            // fallback: try to fetch existing dorms
            List<Dorm> fetchedDorms = this.dormService.getAll(0, 50, Arrays.asList("name"), Sort.Direction.ASC).getContent();
            this.savedDorms.addAll(fetchedDorms);
        }
        for (Dorm dorm : this.savedDorms) {
            for (Integer year : CALENDAR_YEARS) {
                DormYear dy = new DormYear();
                dy.setDorm(dorm);
                dy.setCalendarYear(year);
                dormYears.add(dy);
            }
        }
        this.dormYearService.saveAll(dormYears);
    }

    private void dummyExtraCurricular() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy extra curriculars...");

        List<ExtraCurricular> extraCurriculars = new ArrayList<>();

        extraCurriculars.add(createExtraCurricular("Physical Education", "Promotes physical fitness, teamwork, and healthy competition."));
        extraCurriculars.add(createExtraCurricular("Soccer", "Team sport focusing on physical coordination, strategy, and endurance."));
        extraCurriculars.add(createExtraCurricular("Netball", "Popular team sport for girls, emphasizing agility and teamwork."));
        extraCurriculars.add(createExtraCurricular("Athletics", "Track and field activities promoting individual competition and fitness."));
        extraCurriculars.add(createExtraCurricular("Debate Club", "Develops critical thinking, public speaking, and argumentation skills."));
        extraCurriculars.add(createExtraCurricular("Drama Club", "Encourages self-expression and performance through theatre and acting."));
        extraCurriculars.add(createExtraCurricular("Choir", "Develops vocal performance skills and appreciation for music."));
        extraCurriculars.add(createExtraCurricular("Chess Club", "Enhances strategic thinking and concentration through competitive play."));
        extraCurriculars.add(createExtraCurricular("Science Club", "Promotes scientific curiosity and experimentation beyond the classroom."));
        extraCurriculars.add(createExtraCurricular("Art Club", "Provides a creative outlet through drawing, painting, and visual design."));
        extraCurriculars.add(createExtraCurricular("Environmental Club", "Fosters environmental awareness and sustainability initiatives."));
        extraCurriculars.add(createExtraCurricular("First Aid Club", "Trains students in basic medical and emergency response skills."));
        extraCurriculars.add(createExtraCurricular("Rotary Interact Club", "Instills leadership and community service values."));
        extraCurriculars.add(createExtraCurricular("Junior Achievement", "Introduces entrepreneurship and business skills to students."));
        extraCurriculars.add(createExtraCurricular("Quiz Club", "Promotes academic excellence and broad knowledge through competition."));
        extraCurriculars.add(createExtraCurricular("Marimba Band", "Teaches traditional Zimbabwean music through marimba instruments."));
        extraCurriculars.add(createExtraCurricular("Culture Club", "Explores and celebrates diverse cultural traditions and practices."));
        extraCurriculars.add(createExtraCurricular("Taekwondo", "Martial art promoting discipline, self-defense, and physical fitness."));
        extraCurriculars.add(createExtraCurricular("Student Council", "Encourages leadership, responsibility, and school governance."));
        extraCurriculars.add(createExtraCurricular("Media Club", "Engages students in photography, videography, and digital storytelling."));

        this.extraCurricularService.saveAll(extraCurriculars);
    }

    private ExtraCurricular createExtraCurricular(String name, String description) {
        ExtraCurricular ec = new ExtraCurricular();
        ec.setName(name);
        ec.setDescription(description);
        return ec;
    }

    private void dummyExtraCurricularYear() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy extra curricular years...");
        List<ExtraCurricular> extraCurriculars = this.extraCurricularService.getAll(0, 100, Arrays.asList("name"), Sort.Direction.DESC).getContent();
        List<Teacher> teachers = this.teacherService.getAll(0, 200, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<Student> students = this.studentService.getAll(0, 1000, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<ExtraCurricularYear> extraCurricularYears = new ArrayList<>();

        for (ExtraCurricular extraCurricular : extraCurriculars) {
            for (Integer year : CALENDAR_YEARS) {
                int numberOfTeachers = AppUtils.getRandom(1, Math.min(5, teachers.size()));
                int numberOfStudents = AppUtils.getRandom(10, Math.min(50, students.size()));
                List<Teacher> teacherList = new ArrayList<>();
                List<Student> studentList = new ArrayList<>();
                for (int teacherCount = 0; teacherCount < numberOfTeachers; teacherCount++) {
                    teacherList.add(teachers.get(AppUtils.getRandom(0, teachers.size() - 1)));
                }
                for (int studentCount = 0; studentCount < numberOfStudents; studentCount++) {
                    studentList.add(students.get(AppUtils.getRandom(0, students.size() - 1)));
                }
                ExtraCurricularYear ecy = new ExtraCurricularYear();
                ecy.setExtraCurricular(extraCurricular);
                ecy.setCalendarYear(year);
                ecy.setFacilitators(teacherList);
                ecy.setStudents(studentList);

                // Set leadFacilitatorIndex to random value within bounds of facilitators list
                if (!teacherList.isEmpty()) {
                    Random random = new Random();
                    ecy.setLeadFacilitatorIndex(random.nextInt(teacherList.size()));
                }

                extraCurricularYears.add(ecy);
            }
        }

        this.extraCurricularYearService.saveAll(extraCurricularYears);
    }

    private void dummyCoursework() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy courseworks and marks for lapsed years...");

        int currentYear = LocalDate.now().getYear();
        int lastLapsedYear = currentYear - 1;

        List<SubjectYear> allSubjectYears = this.subjectYearService.getAll(0, 5000, Arrays.asList("calendarYear"), Sort.Direction.ASC).getContent();
        if (allSubjectYears.isEmpty()) {
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.WARNING, "No SubjectYears found.");
            return;
        }

        // Focus on CORE subjects and a few key electives to keep dataset realistic and manageable
        List<String> electiveFocus = Arrays.asList("AGR101", "CS101", "GEO101", "HIS101");

        List<Mark> allMarks = new ArrayList<>();

        for (SubjectYear sy : allSubjectYears) {
            if (sy.getSubject() == null || sy.getCalendarYear() == null) continue;
            boolean inScope = sy.getSubject().getType() == SubjectType.CORE || electiveFocus.contains(sy.getSubject().getCode());
            if (!inScope) continue;

            boolean isLapsed = sy.getCalendarYear() <= lastLapsedYear;
            boolean isCurrent = sy.getCalendarYear() == currentYear;

            List<Coursework> cws = new ArrayList<>();
            if (isLapsed) {
                cws.add(new Coursework(null, "T1 Test - " + sy.getSubject().getName(), CourseworkType.QUIZ, sy, 100.0F, 50.0F));
                cws.add(new Coursework(null, "T2 Test - " + sy.getSubject().getName(), CourseworkType.QUIZ, sy, 100.0F, 50.0F));
                cws.add(new Coursework(null, "T3 Test - " + sy.getSubject().getName(), CourseworkType.QUIZ, sy, 100.0F, 50.0F));
                cws.add(new Coursework(null, "Assignment 1 - " + sy.getSubject().getName(), CourseworkType.ASSIGNMENT, sy, 50.0F, 25.0F));
                cws.add(new Coursework(null, "Assignment 2 - " + sy.getSubject().getName(), CourseworkType.ASSIGNMENT, sy, 50.0F, 25.0F));
                cws.add(new Coursework(null, "Final Assessment - " + sy.getSubject().getName(), CourseworkType.ASSIGNMENT, sy, 100.0F, 50.0F));
            } else if (isCurrent) {
                cws.add(new Coursework(null, "T1 Test - " + sy.getSubject().getName(), CourseworkType.QUIZ, sy, 100.0F, 50.0F));
                cws.add(new Coursework(null, "T2 Test - " + sy.getSubject().getName(), CourseworkType.QUIZ, sy, 100.0F, 50.0F));
            } else {
                // Future or out-of-scope year for our simulation
                continue;
            }

            if (cws.isEmpty()) continue;

            List<Coursework> saved = this.courseworkService.saveAll(cws);
            if (saved == null || saved.isEmpty()) continue;

            List<YearDropdown> students = this.studentService.getAllStudentsForSubjectYear(sy.getId().intValue());
            if (students == null || students.isEmpty()) continue;

            if (isLapsed) {
                // Complete marks: all students for all courseworks
                for (Coursework cw : saved) {
                    for (YearDropdown yd : students) {
                        allMarks.add(new Mark(null, cw, new Student(yd.getId()), AppUtils.getRandom(45F, 99F, 2)));
                    }
                }
            } else if (isCurrent) {
                // Partial marks: ~60% of students so far
                int take = Math.max(1, (int) Math.round(students.size() * 0.6));
                for (Coursework cw : saved) {
                    for (int i = 0; i < take; i++) {
                        YearDropdown yd = students.get(AppUtils.getRandom(0, students.size() - 1));
                        allMarks.add(new Mark(null, cw, new Student(yd.getId()), AppUtils.getRandom(40F, 98F, 2)));
                    }
                }
            }
        }

        if (!allMarks.isEmpty()) {
            this.markService.saveAll(allMarks);
        }
    }

    private void dummyActivityPoint() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting activity points for extra-curriculars (complete for lapsed years)...");

        int currentYear = LocalDate.now().getYear();
        int lastLapsedYear = currentYear - 1;

        List<ExtraCurricularYear> ecyList = this.extraCurricularYearService.getAll(0, 2000, Arrays.asList("calendarYear"), Sort.Direction.ASC).getContent();
        if (ecyList.isEmpty()) return;

        List<ActivityPoint> activityPoints = new ArrayList<>();

        for (ExtraCurricularYear ecy : ecyList) {
            if (ecy.getId() == null) continue;
            ExtraCurricularYearDto ecyView = this.extraCurricularYearService.getViewById(ecy.getId());
            if (ecyView == null || ecyView.getStudents() == null) continue;

            List<StudentDto> studentDtoList = ecyView.getStudents();
            if (studentDtoList.isEmpty()) continue;

            String remark = "Annual performance evaluation - " + (ecy.getExtraCurricular() != null ? ecy.getExtraCurricular().getName() : "Club");

            if (ecy.getCalendarYear() != null && ecy.getCalendarYear() <= lastLapsedYear) {
                // Complete set: all students
                for (StudentDto s : studentDtoList) {
                    activityPoints.add(new ActivityPoint(null, new ExtraCurricularYear(ecyView.getId()), new Student(s.getId()), remark, null));
                }
            } else if (ecy.getCalendarYear() != null && ecy.getCalendarYear() == currentYear) {
                // Partial set: ~50% of students
                int take = Math.max(1, studentDtoList.size() / 2);
                for (int i = 0; i < take; i++) {
                    StudentDto s = studentDtoList.get(AppUtils.getRandom(0, studentDtoList.size() - 1));
                    activityPoints.add(new ActivityPoint(null, new ExtraCurricularYear(ecyView.getId()), new Student(s.getId()), remark, null));
                }
            }
        }

        if (!activityPoints.isEmpty()) {
            this.activityPointService.saveAll(activityPoints);
        }
    }

    private void dummyRating() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy ratings...");

        List<ActivityPoint> activityPoints = this.activityPointService.getAll();
        List<Rating> ratings = new ArrayList<>();

        activityPoints
                .parallelStream()
                .forEach(ap -> {
                    if (ap != null) {
                        ratings.add(
                                new Rating(
                                        null,
                                        ap,
                                        AppUtils.getRandom(60F, 75F, 2)
                                )
                        );
                    }
                });

        this.ratingService.saveAll(ratings);
    }
}
