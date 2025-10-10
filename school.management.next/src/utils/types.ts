export type Person = {
  datestamp: Date | string;
  status: string;
  id: number;
  salutation: string;
  firstName: string;
  otherNames: string;
  lastName: string;
  email: string;
  mobileNumber: string;
  mobileNumber2: string;
  telNumber: string;
  whatsapp: string;
  facebook: string;
  linkedin: string;
  dateOfBirth: Date | string;
  name: string;
  fullname: string;
  avatarUrl: string;
  imagesUrls: string[];
};

export type User = {
  datestamp: Date | string;
  status: string;
  id: number;
  username: string;
  password: string;
  verified: boolean;
  enabled: boolean;
  changePassword: boolean;
  person: Person;
  roles: string[];
};

export type Teacher = {
  datestamp: Date | string;
  status: string;
  id: number;
  person: Person;
};

export type Subject = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  description: string;
  code: string;
  syllabus: string;
  curriculum: string;
  level: string;
  type: SubjectType;
  category: string;
};

export type SubjectYear = {
  datestamp: Date | string;
  status: string;
  id: number;
  subject: Subject;
  calendarYear: number;
  teacher: Teacher;
};

export type Classroom = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  description: string;
};

export type ClassroomYear = {
  datestamp: Date | string;
  status: string;
  id: number;
  classroom: Classroom;
  calendarYear: number;
  subjects: SubjectYear[];
  homeroomTeacher: Teacher;
};

export type Dorm = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  description: string;
  address: string;
  school: School;
};

export type DormView = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  description: string;
  address: string;
  school: School;
};

export type School = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  email: string;
  telNumber: string;
  mobileNumber: string;
  mobileNumber2: string;
  address: string;
  website: string;
};

export type Student = {
  datestamp: Date | string;
  status: string;
  id: number;
  person: Person;
  startYear: string;
  endYear: string;
  classroom: ClassroomYear;
  dorm: DormYear;
  school: School;
};

export type ExtraCurricular = {
  datestamp: Date | string;
  status: string;
  id: number;
  name: string;
  description: string;
};

export type ExtraCurricularYear = {
  datestamp: Date | string;
  status: string;
  id: number;
  calendarYear: number;
  extraCurricular: ExtraCurricular;
  facilitators: Teacher[];
  students: Student[];
  leadFacilitator: Teacher;
  leadFacilitatorId: number;
};

export type DormYear = {
  datestamp: Date | string;
  status: string;
  id: number;
  dorm: Dorm;
  calendarYear: number;
};

export type TeacherView = {
  id: number;
  salutation: string;
  firstName: string;
  otherNames: string;
  lastName: string;
  dateOfBirth: Date | string;
  email: string;
  mobileNumber: string;
  mobileNumber2: string;
  telNumber: string;
  whatsapp: string;
  facebook: string;
  linkedin: string;
  status: string;
  datestamp: Date | string;
  classrooms: ClassroomView[];
  subjects: SubjectView[];
  name: string;
  fullname: string;
  avatarUrl: string;
  imagesUrls: string[];
};

export type ClassroomView = {
  id: number;
  name: string;
  description: string;
  calendarYear: number;
  homeroomTeacher: number;
  teacher: TeacherView;
  subjects: SubjectView[];
  datestamp: Date | string;
  status: string;
  classroomId: number;
};

export type SubjectView = {
  id: number;
  calendarYear: number;
  name: string;
  description: string;
  code: string;
  type: string;
  teacherId: number;
  syllabus: string;
  curriculum: string;
  category: string;
  level: number;
  teacher: TeacherView;
  classroomId: number;
  datestamp: Date | string;
  status: string;
  subjectId: number;
  courseworks: Coursework[];
};

export type StudentView = {
  id: number;
  salutation: string;
  firstName: string;
  otherNames: string;
  lastName: string;
  dateOfBirth: string;
  email: string;
  mobileNumber: string;
  mobileNumber2: string;
  telNumber: string;
  whatsapp: string;
  facebook: string;
  linkedin: string;
  status: string;
  datestamp: Date | string;
  classroom: ClassroomView;
  dorm: DormYearView;
  school: SchoolView;
  startYear: string;
  endYear: string;
  name: string;
  fullname: string;
  avatarUrl: string;
  imagesUrls: string[];
};

export type DormYearView = {
  id: number;
  name: string;
  description: string;
  address: string;
  calendarYear: number;
  linkedSchool: string;
  students: StudentView[];
  dormId: number;
  status: string;
  datestamp: Date | string;
};

export type SchoolView = {
  id: number;
  name: string;
  email: string;
  telNumber: string;
  mobileNumber: string;
  mobileNumber2: string;
  address: string;
  website: string;
  status: string;
  datestamp: Date | string;
};

export type ExtraCurricularYearView = {
  datestamp: Date | string;
  status: string;
  id: number;
  calendarYear: number;
  name: string;
  description: string;
  facilitators: TeacherView[];
  students: StudentView[];
  extraCurricularId: number;
  leadFacilitator: TeacherView;
};

export type UserView = {
  id: number;
  personId: number;
  salutation: string;
  firstName: string;
  otherNames: string;
  lastName: string;
  dateOfBirth: Date | string;
  email: string;
  mobileNumber: string;
  mobileNumber2: string;
  telNumber: string;
  whatsapp: string;
  facebook: string;
  linkedin: string;
  username: string;
  password: string;
  verified: boolean;
  enabled: boolean;
  changePassword: boolean;
  roles: string[];
  status: string;
  datestamp: Date | string;
  name: string;
  fullname: string;
  avatarUrl: string;
  imagesUrls: string[];
};

export type Coursework = {
  id: number;
  title: string;
  type: CourseworkType;
  subject: SubjectYear;
  status: string;
  datestamp: Date | string;
  subjectName: string;
  total: number;
  passMark: number;
  marks?: Mark[];
};

export type Mark = {
  id: number;
  coursework: Coursework;
  student: Student;
  mark: number;
  subjectName: string;
  status: string;
  datestamp: Date | string;
};

export type ActivityPoint = {
  id: number;
  extraCurricularYear: ExtraCurricularYear;
  student: Student;
  remarks: string;
  ratings: Rating[];
  status: string;
  datestamp: Date | string;
};

export type Rating = {
  id: number;
  activityPoint: ActivityPoint;
  rating: number;
};

export enum CourseworkType {
  ASSIGNMENT = "ASSIGNMENT",
  QUIZ = "QUIZ",
  TEST = "TEST",
  PRACTICAL = "PRACTICAL",
  EXTRA = "EXTRA",
}

export enum Status {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE",
  SUSPENDED = "SUSPENDED",
  DELETED = "DELETED",
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  REJECTED = "REJECTED",
  BLOCKED = "BLOCKED",
  UNBLOCKED = "UNBLOCKED",
  ENABLED = "ENABLED",
  DISABLED = "DISABLED",
}

export enum Salutation {
  MR = "MR",
  MRS = "MRS",
  MS = "MS",
  DR = "DR",
  PROF = "PROF",
  ENG = "ENG",
  REV = "REV",
  SNR = "SNR",
  JNR = "JNR",
}

export enum Role {
  ADMIN = "ADMIN",
  TEACHER = "TEACHER",
  STUDENT = "STUDENT",
  PARENT = "PARENT",
  STAFF = "STAFF",
  GUEST = "GUEST",
}

export enum SubjectType {
  CORE = "Core",
  ELECTIVE = "Elective",
  AP = "Advanced Placement",
  PHYSICAL = "Physical",
  OTHER = "Other",
}

export type YearDropdown = {
  id: number;
  name: string;
  calendarYear?: number;
};

export type CourseworkDropdown = {
  id: number;
  type: string;
  calendarYear: number;
  subject: Subject;
};

export type ReportFilter = {
  students: YearDropdown[];
  teachers: YearDropdown[];
  courseworks: CourseworkDropdown[];
  classrooms: YearDropdown[];
  subjects: YearDropdown[];
  extraCurriculars: YearDropdown[];
  calendarYears: number[];
}

export type AvailableFilters = {
  students: boolean;
  teachers: boolean;
  courseworks: boolean;
  classrooms: boolean;
  subjects: boolean;
  extraCurriculars: boolean;
  calendarYears: boolean;
}

export type ReportType = {
  title: string;
  endpoint: string;
  availableFilters: AvailableFilters;
  index: number;
}

export type Errors<T, V> = {
  [K in keyof T]: T[K] extends object ? Errors<T[K], V> : V;
} & {
  general?: string;
};
