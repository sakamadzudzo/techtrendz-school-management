import { ReportType, AvailableFilters } from "./types";

export interface ReportConfig {
  title: string;
  endpoint: string;
  index: number;
  function: () => Promise<any>;
  availableFilters: AvailableFilters;
}

export const createReportTypes = (
  generateMarksByClassroomYearReport: any,
  generateMarksByStudentReport: any,
  generateMarksByStudentClassroomYearReport: any,
  generateMarksByStudentSubjectYearReport: any,
  generateMarksByStudentYearReport: any,
  generateMarksByYearReport: any,
  generateMarksBySubjectYearReport: any,
  generateRatingsByStudentActivityYearReport: any,
  generateRatingsByStudentYearReport: any,
  generateRatingsByActivityYearReport: any,
  generateRatingsByActivityReport: any,
  generateMarksByTeacherYearReport: any,
  generateRatingsByTeacherYearReport: any,
  generateProgressReportByStudent: any,
  generateProgressReportByTeacher: any,
  generateProgressReportBySubject: any,
  generateProgressReportByClassroom: any,
  generateProgressReportByActivity: any,
  generateProgressReportByFacilitator: any,
  incrementLoading: () => void,
  decrementLoading: () => void,
  setReportData: (data: ArrayBuffer | null) => void,
  setErrorMessage: (error: string) => void,
  setStatusCode: (status: number | undefined) => void
): ReportType[] => {
  return [
    {
      title: "Marks By Classroom Year",
      endpoint: "marks_by_classroom_year",
      index: 1,
      function: (reportFilter: any) => {
        return generateMarksByClassroomYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student",
      endpoint: "marks_by_student",
      index: 2,
      function: (reportFilter: any) => {
        return generateMarksByStudentReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Marks By Student Classroom Year",
      endpoint: "marks_student_classroom_year",
      index: 3,
      function: (reportFilter: any) => {
        return generateMarksByStudentClassroomYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student Subject Year",
      endpoint: "marks_student_subject_year",
      index: 4,
      function: (reportFilter: any) => {
        return generateMarksByStudentSubjectYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student Year",
      endpoint: "marks_student_year",
      index: 5,
      function: (reportFilter: any) => {
        return generateMarksByStudentYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Year",
      endpoint: "marks_year",
      index: 6,
      function: (reportFilter: any) => {
        return generateMarksByYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Subject Year",
      endpoint: "marks_subject_year",
      index: 7,
      function: (reportFilter: any) => {
        return generateMarksBySubjectYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Student Activity Year",
      endpoint: "ratings_student_activity_year",
      index: 8,
      function: (reportFilter: any) => {
        return generateRatingsByStudentActivityYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Student Year",
      endpoint: "ratings_student_year",
      index: 9,
      function: (reportFilter: any) => {
        return generateRatingsByStudentYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Activity Year",
      endpoint: "ratings_activity_year",
      index: 10,
      function: (reportFilter: any) => {
        return generateRatingsByActivityYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Activity",
      endpoint: "ratings_activity",
      index: 11,
      function: (reportFilter: any) => {
        return generateRatingsByActivityReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      },
    },
    {
      title: "Marks By Teacher Year",
      endpoint: "marks_teacher_year",
      index: 12,
      function: (reportFilter: any) => {
        return generateMarksByTeacherYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Teacher Year",
      endpoint: "ratings_teacher_year",
      index: 13,
      function: (reportFilter: any) => {
        return generateRatingsByTeacherYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Progress By Student",
      endpoint: "progress_student",
      index: 14,
      function: (reportFilter: any) => {
        return generateProgressReportByStudent(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Teacher",
      endpoint: "progress_teacher",
      index: 15,
      function: (reportFilter: any) => {
        return generateProgressReportByTeacher(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Subject",
      endpoint: "progress_subject",
      index: 16,
      function: (reportFilter: any) => {
        return generateProgressReportBySubject(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Classroom",
      endpoint: "progress_classroom",
      index: 17,
      function: (reportFilter: any) => {
        return generateProgressReportByClassroom(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Activity",
      endpoint: "progress_activity",
      index: 18,
      function: (reportFilter: any) => {
        return generateProgressReportByActivity(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Facilitator",
      endpoint: "progress_facilitator",
      index: 19,
      function: (reportFilter: any) => {
        return generateProgressReportByFacilitator(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error: string, statusCode: number | undefined) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        );
      },
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
  ];
};