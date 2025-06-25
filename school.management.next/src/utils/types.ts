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
  id: string;
  person: Person;
};

export type Subject = {
  datestamp: Date | string;
  status: string;
  id: string;
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
  id: string;
  subject: Subject;
  calendarYear: number;
  teacher: Teacher;
};

export type Classroom = {
  datestamp: Date | string;
  status: string;
  id: string;
  name: string;
  description: string;
};

export type ClassroomYear = {
  datestamp: Date | string;
  status: string;
  id: string;
  classroom: Classroom;
  calendarYear: number;
  subjects: SubjectYear[];
};

export type Dorm = {
  datestamp: Date | string;
  status: string;
  id: string;
  name: string;
  description: string;
  address: string;
  school: School;
};

export type School = {
  datestamp: Date | string;
  status: string;
  id: string;
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
  id: string;
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
  id: string;
  name: string;
  description: string;
};

export type ExtraCurricularYear = {
  datestamp: Date | string;
  status: string;
  id: string;
  calendarYear: number;
  extraCurricular: ExtraCurricular;
  facilitators: string;
  students: string;
};

export type DormYear = {
  datestamp: Date | string;
  status: string;
  id: string;
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
  subjects: SubjectView[];
};

export type SubjectView = {
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
  dorm: DormView;
  school: SchoolView;
  startYear: string;
  endYear: string;
  name: string;
  fullname: string;
  avatarUrl: string;
  imagesUrls: string[];
};

export type DormView = {
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
