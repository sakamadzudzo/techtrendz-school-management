package zw.co.techtrendz.school.management.api.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.enums.SubjectType;
import zw.co.techtrendz.school.management.api.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class DummyDataRunner {

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
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy data...");
            this.dummyDataInsertion();
            Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Dummy data successfully inserted.");
        };
    }

    private void dummyDataInsertion() {
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
    }

    private void dummyPersons() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy persons...");
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
        p1.setDateOfBirth(LocalDate.of(1972, 7, 8));

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
        p3.setDateOfBirth(LocalDate.of(1970, 8, 1));

        List<Person> persons = Arrays.asList(
                p1,
                p2,
                p3
        );
        this.personService.saveAll(persons);
    }

    private void dummyTeachers() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy teachers...");
        Teacher t1 = new Teacher();
//        t1.setId(1L);
        t1.setPerson(new Person(1L));

        List<Teacher> teachers1 = Arrays.asList(
                t1
        );
        this.teacherService.saveAll(teachers1);

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
        List<Teacher> teachers = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Person person = new Person();
            person.setFirstName(firstNames[this.getRandomNumber(0, 70)]);
            person.setLastName(lastNames[this.getRandomNumber(0, 30)]);
            int otherNameLotto = this.getRandomNumber(0, 93);
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
        List<Teacher> teachers = this.teacherService.getAll(0, 100, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<SubjectYear> subjectYears = new ArrayList<>();

        for (Subject subject : subjects) {
            for (int year = 2023; year <= 2025; year++) {
                SubjectYear sy = new SubjectYear();
                sy.setSubject(subject);
                sy.setCalendarYear(year);
                sy.setTeacher(teachers.get(this.getRandomNumber(0, teachers.size() - 1)));
                if (subject.getCode().equals("BES101") && year == 2023) {
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
        List<Classroom> classrooms = this.classroomService.getAll(0, 100, Arrays.asList("name"), Sort.Direction.DESC).getContent();
        List<SubjectYear> subjectYears = this.subjectYearService.getAll(0, 100, Arrays.asList("subject.name"), Sort.Direction.DESC).getContent();
        List<Teacher> teachers = this.teacherService.getAll(0, 100, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<ClassroomYear> classroomYears = new ArrayList<>();

        for (Classroom classroom : classrooms) {
            for (int year = 2023; year <= 2025; year++) {
                final Integer calendarYear = year;
                ClassroomYear cy = new ClassroomYear();
                cy.setClassroom(classroom);
                cy.setCalendarYear(year);
                cy.setSubjects(
                        subjectYears
                                .stream()
                                .filter(
                                        subjectYear ->
                                                subjectYear
                                                        .getCalendarYear()
                                                        .equals(calendarYear)
                                )
                                .collect(
                                        Collectors
                                                .toCollection(
                                                        CopyOnWriteArrayList::new
                                                )
                                )
                );
                cy.setHomeroomTeacher(teachers.get(this.getRandomNumber(0, teachers.size() - 1)));
                classroomYears.add(cy);
            }
        }

        this.classroomYearService.saveAll(classroomYears);
    }

    private void dummyStudent() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy students...");
//        Student s1 = new Student();
//        s1.setPerson(new Person(1L));
//        s1.setStartYear(2023);
//        s1.setEndYear(2027);
//        s1.setClassroom(new ClassroomYear(1L));
//
//        List<Student> students1 = Arrays.asList(
//                s1
//        );
//        this.studentService.saveAll(students1);

        List<ClassroomYear> classroomYears = classroomYearService.getAll(0, 100, Arrays.asList("classroom.name", "calendarYear"), Sort.Direction.DESC).getContent();
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

        int studentCount = 30;
        int currentPersonId = 1;

        for (int i = 0; i < studentCount; i++) {
            String firstName = firstNames[this.getRandomNumber(0, 90)];
            String lastName = lastNames[this.getRandomNumber(0, 70)];
            int otherNameLotto = this.getRandomNumber(0, 103);
            String otherName = otherNameLotto < 90 ? firstNames[otherNameLotto] : "";

            Person person = new Person();
            person.setSalutation(Salutation.MR); // Or vary this if needed
            person.setFirstName(firstName);
            person.setOtherNames(otherName);
            person.setLastName(lastName);
            person.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@schoolmail.com");
            person.setMobileNumber("077" + String.format("%07d", i + 100));
            person.setDateOfBirth(LocalDate.of(2006 + (i % 5), 1 + (i % 12), 1 + (i % 28)));

//            personService.save(person); // save person first to get ID

            Student student = new Student();
            student.setPerson(person);

            int startYear = 2023 + (i % 3); // cycle through 2023–2025
            student.setStartYear(startYear);
            student.setEndYear(startYear + 4); // assume 4-year duration

            ClassroomYear classroomYear = classroomYears.get(i % classroomYears.size());
            student.setClassroom(classroomYear);
            student.setDorm(new DormYear(1L));
            student.setSchool(new School(1L));

            students.add(student);
        }

        this.studentService.saveAll(students);
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void dummyUser() {
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy users...");
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
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy schools...");
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
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy dorms...");
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
        Logger.getLogger(DummyDataRunner.class.getName()).log(Level.INFO, "Inserting dummy dorm years...");
        DormYear dy1 = new DormYear();
        dy1.setDorm(new Dorm(1L));
        dy1.setCalendarYear(2023);

        List<DormYear> dormYears = Arrays.asList(
                dy1
        );

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
        List<Teacher> teachers = this.teacherService.getAll(0, 100, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<Student> students = this.studentService.getAll(0, 100, Arrays.asList("person.lastName"), Sort.Direction.DESC).getContent();
        List<ExtraCurricularYear> extraCurricularYears = new ArrayList<>();

        List<Teacher> defaultTeachers = Arrays.asList(new Teacher(1L)); // Replace or expand as needed
        List<Student> defaultStudents = Arrays.asList(new Student(1L)); // Replace or expand as needed

        for (ExtraCurricular extraCurricular : extraCurriculars) {
            for (int year = 2023; year <= 2025; year++) {
                int numberOfTeachers = this.getRandomNumber(1, 5);
                int numberOfStudents = this.getRandomNumber(5, 30);
                List<Teacher> teacherList = new ArrayList<>();
                List<Student> studentList = new ArrayList<>();
                for (int teacherCount = 0; teacherCount < numberOfTeachers; teacherCount++) {
                    teacherList.add(teachers.get(teacherCount));
                }
                for (int studentCount = 0; studentCount < numberOfStudents; studentCount++) {
                    studentList.add(students.get(studentCount));
                }
                ExtraCurricularYear ecy = new ExtraCurricularYear();
                ecy.setExtraCurricular(extraCurricular);
                ecy.setCalendarYear(year);
                ecy.setFacilitators(teacherList);
                ecy.setStudents(studentList);
                extraCurricularYears.add(ecy);
            }
        }

        this.extraCurricularYearService.saveAll(extraCurricularYears);
    }
}
