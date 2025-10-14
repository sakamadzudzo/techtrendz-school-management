import { useState, useMemo, useCallback } from "react";
import { ReportType, ReportFilter, Errors, AvailableFilters } from "@/utils/types";
import { createReportTypes } from "@/utils/reportConfig";

const defaultAvailableFilters: AvailableFilters = {
  students: false,
  teachers: false,
  courseworks: false,
  classrooms: false,
  subjects: false,
  extraCurriculars: false,
  calendarYears: false,
};

export const useReportsState = (
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
) => {
  const [reportData, setReportDataState] = useState<ArrayBuffer | null>(null);
  const [errorMessage, setErrorMessageState] = useState<string>("");
  const [statusCode, setStatusCodeState] = useState<number | undefined>();
  const [reportType, setReportType] = useState<ReportType | null>(null);
  const [reportFilter, setReportFilter] = useState<ReportFilter>({} as ReportFilter);
  const [errors, setErrors] = useState<Errors<ReportFilter, string>>({} as Errors<ReportFilter, string>);

  const reportTypes = useMemo(() =>
    createReportTypes(
      generateMarksByClassroomYearReport,
      generateMarksByStudentReport,
      generateMarksByStudentClassroomYearReport,
      generateMarksByStudentSubjectYearReport,
      generateMarksByStudentYearReport,
      generateMarksByYearReport,
      generateMarksBySubjectYearReport,
      generateRatingsByStudentActivityYearReport,
      generateRatingsByStudentYearReport,
      generateRatingsByActivityYearReport,
      generateRatingsByActivityReport,
      generateMarksByTeacherYearReport,
      generateRatingsByTeacherYearReport,
      generateProgressReportByStudent,
      generateProgressReportByTeacher,
      generateProgressReportBySubject,
      generateProgressReportByClassroom,
      generateProgressReportByActivity,
      generateProgressReportByFacilitator,
      incrementLoading,
      decrementLoading,
      setReportDataState,
      setErrorMessageState,
      setStatusCodeState
    ),
    [
      generateMarksByClassroomYearReport,
      generateMarksByStudentReport,
      generateMarksByStudentClassroomYearReport,
      generateMarksByStudentSubjectYearReport,
      generateMarksByStudentYearReport,
      generateMarksByYearReport,
      generateMarksBySubjectYearReport,
      generateRatingsByStudentActivityYearReport,
      generateRatingsByStudentYearReport,
      generateRatingsByActivityYearReport,
      generateRatingsByActivityReport,
      generateMarksByTeacherYearReport,
      generateRatingsByTeacherYearReport,
      generateProgressReportByStudent,
      generateProgressReportByTeacher,
      generateProgressReportBySubject,
      generateProgressReportByClassroom,
      generateProgressReportByActivity,
      generateProgressReportByFacilitator,
      incrementLoading,
      decrementLoading,
    ]
  );

  const resetStates = useCallback(() => {
    setReportDataState(null);
    setErrorMessageState("");
    setStatusCodeState(undefined);
    setReportType(null);
    setReportFilter({} as ReportFilter);
    setErrors({} as Errors<ReportFilter, string>);
  }, []);

  const isValid = useMemo(() => {
    if (!reportType) return false;

    const tempErrors: Errors<ReportFilter, string> = {} as Errors<ReportFilter, string>;

    if (reportType?.availableFilters?.students && !reportFilter?.studentId) {
      tempErrors.studentId = "Student is required for this report";
    }
    if (reportType?.availableFilters?.teachers && !reportFilter?.teacherId) {
      tempErrors.teacherId = "Teacher is required for this report";
    }
    if (reportType?.availableFilters?.courseworks && !reportFilter?.courseworkId) {
      tempErrors.courseworkId = "Coursework is required for this report";
    }
    if (reportType?.availableFilters?.classrooms && !reportFilter?.classroomId) {
      tempErrors.classroomId = "Classroom is required for this report";
    }
    if (reportType?.availableFilters?.subjects && !reportFilter?.subjectId) {
      tempErrors.subjectId = "Subject is required for this report";
    }
    if (reportType?.availableFilters?.extraCurriculars && !reportFilter?.extraCurricularId) {
      tempErrors.extraCurricularId = "Extra curricular activity is required for this report";
    }
    if (reportType?.availableFilters?.calendarYears && !reportFilter?.year) {
      tempErrors.year = "Year is required for this report";
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  }, [reportType, reportFilter]);

  const validate = useCallback(() => {
    return isValid;
  }, [isValid]);

  const hasEnoughFilters = useMemo(() => {
    if (!reportType) {
      return false;
    }

    const { availableFilters } = reportType;

    const requirements = [
      { needs: availableFilters.students, has: !!reportFilter.studentId },
      { needs: availableFilters.teachers, has: !!reportFilter.teacherId },
      { needs: availableFilters.courseworks, has: !!reportFilter.courseworkId },
      { needs: availableFilters.classrooms, has: !!reportFilter.classroomId },
      { needs: availableFilters.subjects, has: !!reportFilter.subjectId },
      { needs: availableFilters.extraCurriculars, has: !!reportFilter.extraCurricularId },
      { needs: availableFilters.calendarYears, has: !!reportFilter.year },
    ];

    return requirements.every(({ needs, has }) => !needs || has);
  }, [reportFilter, reportType]);

  const handleReportTypeChange = useCallback((newReportType: ReportType) => {
    setReportType(newReportType);
    setReportFilter({} as ReportFilter);
    setErrors({} as Errors<ReportFilter, string>);
  }, []);

  const handleFilterChange = useCallback((key: keyof ReportFilter, value: number) => {
    setReportFilter(prev => ({
      ...prev,
      [key]: value,
    }));
  }, []);

  return {
    // State
    reportData: reportData,
    errorMessage: errorMessage,
    statusCode: statusCode,
    reportType,
    reportFilter,
    errors,
    reportTypes,

    // Computed values
    hasEnoughFilters,
    isValid,

    // Actions
    resetStates,
    validate,
    handleReportTypeChange,
    handleFilterChange,
    setReportData,
    setErrorMessage,
    setStatusCode,
  };
};