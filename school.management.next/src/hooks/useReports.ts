import { useSelector } from "react-redux";
import { RootState } from "../store/store";
import { ReportFilter } from "@/utils/types";

export function useGenerateMarksReport() {
  const token = useSelector((state: RootState) => state.auth.token);

  const generateMarksByStudentClassroomYearReport = async (
    year: number,
    classroomId?: number,
    studentId?: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({ year: String(year) });
      if (classroomId) params.append("classroomId", String(classroomId));
      if (studentId) params.append("studentId", String(studentId));

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
    year: number,
    subjectId: number,
    studentId?: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        subjectId: String(subjectId),
      });
      if (studentId) params.append("studentId", String(studentId));

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
    year: number,
    studentId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        studentId: String(studentId),
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
    year: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({ year: String(year) });

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
    year: number,
    subjectId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        subjectId: String(subjectId),
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
    year: number,
    activityId: number,
    studentId?: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        activityId: String(activityId),
      });
      if (studentId) params.append("studentId", String(studentId));

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
    year: number,
    studentId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        studentId: String(studentId),
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
    year: number,
    activityId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        activityId: String(activityId),
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
    activityId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({ activityId: String(activityId) });

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
    year: number,
    teacherId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        teacherId: String(teacherId),
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
    year: number,
    teacherId: number,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        year: String(year),
        teacherId: String(teacherId),
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
    studentId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        studentId: String(studentId),
        entityType,
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
    teacherId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        teacherId: String(teacherId),
        entityType,
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
    subjectId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        subjectId: String(subjectId),
        entityType,
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
    classroomId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        classroomId: String(classroomId),
        entityType,
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
    activityId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        activityId: String(activityId),
        entityType,
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
    facilitatorId: number,
    entityType: string,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: (data: ArrayBuffer) => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading && incrementLoading();
    try {
      const params = new URLSearchParams({
        facilitatorId: String(facilitatorId),
        entityType,
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
