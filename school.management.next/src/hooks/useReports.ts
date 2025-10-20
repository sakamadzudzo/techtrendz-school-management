import { useSelector } from "react-redux";
import { RootState } from "../store/store";
import { ReportFilter } from "@/utils/types";

export function useGenerateMarksReport() {
  const token = useSelector((state: RootState) => state.auth.token);

  const generateMarksByStudentClassroomYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        type: reportFilter.type
      });
      if (reportFilter.classroomId) params.append("classroomId", String(reportFilter.classroomId));
      if (reportFilter.studentId) params.append("studentId", String(reportFilter.studentId));

      const res = await fetch(
        `/api/reports?action=marks-student-classroom-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (res.status === 204) {
        return { success: false, error: "No Content", statusCode: 204 };
      }
      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksByStudentSubjectYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        subjectId: String(reportFilter.subjectId!),
        type: reportFilter.type,
      });
      if (reportFilter.studentId) params.append("studentId", String(reportFilter.studentId));

      const res = await fetch(
        `/api/reports?action=marks-student-subject-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (res.status === 204) {
        return { success: false, error: "No Content", statusCode: 204 };
      }
      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksByStudentYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        studentId: String(reportFilter.studentId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=marks-student-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksByStudentReport = async (
    reportFiler: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        studentId: String(reportFiler.studentId),
        type: reportFiler.type,
      });

      const res = await fetch(
        `/api/reports?action=marks-student&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksByYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        type: reportFilter.type
      });

      const res = await fetch(
        `/api/reports?action=marks-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksByClassroomYearReport = async (
    reportFiler: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFiler.year),
        classroomId: String(reportFiler.classroomId),
        type: reportFiler.type,
      });

      const res = await fetch(
        `/api/reports?action=marks-classroom-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateMarksBySubjectYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        subjectId: String(reportFilter.subjectId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=marks-subject-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  return {
    generateMarksByStudentClassroomYearReport,
    generateMarksByStudentSubjectYearReport,
    generateMarksByStudentYearReport,
    generateMarksByStudentReport,
    generateMarksByYearReport,
    generateMarksByClassroomYearReport,
    generateMarksBySubjectYearReport,
  };
}

export function useGenerateRatingsReport() {
  const token = useSelector((state: RootState) => state.auth.token);

  const generateRatingsByStudentActivityYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        activityId: String(reportFilter.extraCurricularId!),
        type: reportFilter.type,
      });
      if (reportFilter.studentId) params.append("studentId", String(reportFilter.studentId));

      const res = await fetch(
        `/api/reports?action=ratings-student-activity-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate ratings report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateRatingsByStudentYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        studentId: String(reportFilter.studentId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=ratings-student-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate ratings report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateRatingsByActivityYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        activityId: String(reportFilter.extraCurricularId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=ratings-activity-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate ratings report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateRatingsByActivityReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        activityId: String(reportFilter.extraCurricularId!),
        type: reportFilter.type
      });

      const res = await fetch(
        `/api/reports?action=ratings-activity&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate ratings report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  return {
    generateRatingsByStudentActivityYearReport,
    generateRatingsByStudentYearReport,
    generateRatingsByActivityYearReport,
    generateRatingsByActivityReport,
  };
}

export function useGenerateTeacherReport() {
  const token = useSelector((state: RootState) => state.auth.token);

  const generateMarksByTeacherYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        teacherId: String(reportFilter.teacherId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=marks-teacher-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err =
          errorData.error || "Failed to generate teacher marks report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateRatingsByTeacherYearReport = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(reportFilter.year!),
        teacherId: String(reportFilter.teacherId!),
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=ratings-teacher-year&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err =
          errorData.error || "Failed to generate teacher ratings report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  return {
    generateMarksByTeacherYearReport,
    generateRatingsByTeacherYearReport,
  };
}

export function useGenerateProgressReport() {
  const token = useSelector((state: RootState) => state.auth.token);

  const generateProgressReportByStudent = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        studentId: String(reportFilter.studentId!),
        entityType: "student",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-student&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateProgressReportByTeacher = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        teacherId: String(reportFilter.teacherId!),
        entityType: "teacher",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-teacher&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateProgressReportBySubject = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        subjectId: String(reportFilter.subjectId!),
        entityType: "subject",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-subject&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateProgressReportByClassroom = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        classroomId: String(reportFilter.classroomId!),
        entityType: "classroom",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-classroom&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateProgressReportByActivity = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        activityId: String(reportFilter.extraCurricularId!),
        entityType: "activity",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-activity&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  const generateProgressReportByFacilitator = async (
    reportFilter: ReportFilter,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        facilitatorId: String(reportFilter.teacherId!),
        entityType: "facilitator",
        type: reportFilter.type,
      });

      const res = await fetch(
        `/api/reports?action=progress-facilitator&${params.toString()}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) {
        const errorData = await res.json();
        const err = errorData.error || "Failed to generate progress report";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        const reportData = await res.arrayBuffer();
        onSuccess?.(reportData);
        return { success: true, data: reportData };
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading && decrementLoading();
    }
  };

  return {
    generateProgressReportByStudent,
    generateProgressReportByTeacher,
    generateProgressReportBySubject,
    generateProgressReportByClassroom,
    generateProgressReportByActivity,
    generateProgressReportByFacilitator,
  };
}
