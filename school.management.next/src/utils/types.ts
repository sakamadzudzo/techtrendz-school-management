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
  datestamp: string;
  status: string;
  id: string;
  person: Person;
};

export type Subject = {
  datestamp: string;
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
  datestamp: string;
  status: string;
  id: string;
  subject: Subject;
  calendarYear: string;
  teacher: Teacher;
};

export type Classroom = {
  datestamp: string;
  status: string;
  id: string;
  name: string;
  description: string;
};

export type ClassroomYear = {
  datestamp: string;
  status: string;
  id: string;
  classroom: Classroom;
  calendarYear: string;
  subjects: SubjectYear[];
};

export type Dorm = {
  datestamp: string;
  status: string;
  id: string;
  name: string;
  description: string;
  address: string;
  school: School;
};

export type School = {
  datestamp: string;
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
  datestamp: string;
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
  datestamp: string;
  status: string;
  id: string;
  name: string;
  description: string;
};

export type ExtraCurricularYear = {
  datestamp: string;
  status: string;
  id: string;
  calendarYear: string;
  extraCurricular: ExtraCurricular;
  facilitators: string;
  students: string;
};

export type DormYear = {
  datestamp: string;
  status: string;
  id: string;
  dorm: Dorm;
  calendarYear: string;
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
