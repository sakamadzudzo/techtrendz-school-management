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
  type: string;
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
};

export type DormYear = {
  datestamp: Date | string;
  status: string;
  id: number;
  dorm: Dorm;
  calendarYear: number;
  school: School;
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

export type YearDropdown = {
  id: number;
  name: string;
  calendarYear?: number;
};

export type Errors<T, V> = {
  [K in keyof T]: T[K] extends object ? Errors<T[K], V> : V;
} & {
  general?: string;
};
