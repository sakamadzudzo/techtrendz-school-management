import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/reports";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

// Marks Reports
async function generateMarksByStudentClassroomYearReport(
  authHeader: any,
  year: number,
  classroomId?: number,
  studentId?: number
) {
  const params = new URLSearchParams({ year: String(year) });
  if (classroomId) params.append("classroomId", String(classroomId));
  if (studentId) params.append("studentId", String(studentId));

  const res = await fetch(
    `${API_BASE}/marks/student-classroom-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError =
      "Generating marks by student classroom year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksByStudentSubjectYearReport(
  authHeader: any,
  year: number,
  subjectId: number,
  studentId?: number
) {
  const params = new URLSearchParams({
    year: String(year),
    subjectId: String(subjectId),
  });
  if (studentId) params.append("studentId", String(studentId));

  const res = await fetch(
    `${API_BASE}/marks/student-subject-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating marks by student subject year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksByStudentYearReport(
  year: number,
  studentId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    studentId: String(studentId),
  });

  const res = await fetch(
    `${API_BASE}/marks/student-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating marks by student year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksByStudentReport(
  studentId: number,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId: String(studentId) });

  const res = await fetch(`${API_BASE}/marks/student?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating marks by student report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksByYearReport(year: number, authHeader: any) {
  const params = new URLSearchParams({ year: String(year) });

  const res = await fetch(`${API_BASE}/marks/year?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating marks by year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksByClassroomYearReport(
  year: number,
  classroomId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    classroomId: String(classroomId),
  });

  const res = await fetch(
    `${API_BASE}/marks/classroom-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating marks by classroom year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateMarksBySubjectYearReport(
  year: number,
  subjectId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    subjectId: String(subjectId),
  });

  const res = await fetch(
    `${API_BASE}/marks/subject-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating marks by subject year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

// Ratings Reports
async function generateRatingsByStudentActivityYearReport(
  year: number,
  activityId: number,
  authHeader: any,
  studentId?: number
) {
  const params = new URLSearchParams({
    year: String(year),
    activityId: String(activityId),
  });
  if (studentId) params.append("studentId", String(studentId));

  const res = await fetch(
    `${API_BASE}/ratings/student-activity-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError =
      "Generating ratings by student activity year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateRatingsByStudentYearReport(
  year: number,
  studentId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    studentId: String(studentId),
  });

  const res = await fetch(
    `${API_BASE}/ratings/student-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating ratings by student year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateRatingsByActivityYearReport(
  year: number,
  activityId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    activityId: String(activityId),
  });

  const res = await fetch(
    `${API_BASE}/ratings/activity-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating ratings by activity year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateRatingsByActivityReport(
  activityId: number,
  authHeader: any
) {
  const params = new URLSearchParams({ activityId: String(activityId) });

  const res = await fetch(`${API_BASE}/ratings/activity?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating ratings by activity report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

// Teacher Reports
async function generateMarksByTeacherYearReport(
  year: number,
  teacherId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    teacherId: String(teacherId),
  });

  const res = await fetch(
    `${API_BASE}/marks/teacher-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating marks by teacher year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateRatingsByTeacherYearReport(
  year: number,
  teacherId: number,
  authHeader: any
) {
  const params = new URLSearchParams({
    year: String(year),
    teacherId: String(teacherId),
  });

  const res = await fetch(
    `${API_BASE}/ratings/teacher-year?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating ratings by teacher year report failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

// Progress Reports
async function generateProgressReportByStudent(
  studentId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    studentId: String(studentId),
    entityType,
  });

  const res = await fetch(`${API_BASE}/progress/student?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating progress report by student failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateProgressReportByTeacher(
  teacherId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    teacherId: String(teacherId),
    entityType,
  });

  const res = await fetch(`${API_BASE}/progress/teacher?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating progress report by teacher failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateProgressReportBySubject(
  subjectId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    subjectId: String(subjectId),
    entityType,
  });

  const res = await fetch(`${API_BASE}/progress/subject?${params.toString()}`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Generating progress report by subject failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateProgressReportByClassroom(
  classroomId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    classroomId: String(classroomId),
    entityType,
  });

  const res = await fetch(
    `${API_BASE}/progress/classroom?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating progress report by classroom failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateProgressReportByActivity(
  activityId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    activityId: String(activityId),
    entityType,
  });

  const res = await fetch(
    `${API_BASE}/progress/activity?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating progress report by activity failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

async function generateProgressReportByFacilitator(
  facilitatorId: number,
  entityType: string,
  authHeader: any
) {
  const params = new URLSearchParams({
    facilitatorId: String(facilitatorId),
    entityType,
  });

  const res = await fetch(
    `${API_BASE}/progress/facilitator?${params.toString()}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Generating progress report by facilitator failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.arrayBuffer();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      const {
        action,
        year,
        classroomId,
        studentId,
        subjectId,
        activityId,
        teacherId,
        facilitatorId,
        entityType,
      } = req.query;

      // Marks Reports
      if (action === "marks-student-classroom-year") {
        const reportData = await generateMarksByStudentClassroomYearReport(
          authHeader,
          Number(year),
          classroomId ? Number(classroomId) : undefined,
          studentId ? Number(studentId) : undefined
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_student_classroom_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-student-subject-year") {
        const reportData = await generateMarksByStudentSubjectYearReport(
          authHeader,
          Number(year),
          Number(subjectId),
          studentId ? Number(studentId) : undefined
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_student_subject_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-student-year") {
        const reportData = await generateMarksByStudentYearReport(
          Number(year),
          Number(studentId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_student_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-student") {
        const reportData = await generateMarksByStudentReport(
          Number(studentId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_student.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-year") {
        const reportData = await generateMarksByYearReport(
          Number(year),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-classroom-year") {
        const reportData = await generateMarksByClassroomYearReport(
          Number(year),
          Number(classroomId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_classroom_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "marks-subject-year") {
        const reportData = await generateMarksBySubjectYearReport(
          Number(year),
          Number(subjectId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_subject_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      // Ratings Reports
      if (action === "ratings-student-activity-year") {
        const reportData = await generateRatingsByStudentActivityYearReport(
          Number(year),
          Number(activityId),
          authHeader,
          studentId ? Number(studentId) : undefined
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=ratings_by_student_activity_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "ratings-student-year") {
        const reportData = await generateRatingsByStudentYearReport(
          Number(year),
          Number(studentId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=ratings_by_student_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "ratings-activity-year") {
        const reportData = await generateRatingsByActivityYearReport(
          Number(year),
          Number(activityId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=ratings_by_activity_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "ratings-activity") {
        const reportData = await generateRatingsByActivityReport(
          Number(activityId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=ratings_by_activity.pdf"
          )
          .send(Buffer.from(reportData));
      }

      // Teacher Reports
      if (action === "marks-teacher-year") {
        const reportData = await generateMarksByTeacherYearReport(
          Number(year),
          Number(teacherId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=marks_by_teacher_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "ratings-teacher-year") {
        const reportData = await generateRatingsByTeacherYearReport(
          Number(year),
          Number(teacherId),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=ratings_by_teacher_year.pdf"
          )
          .send(Buffer.from(reportData));
      }

      // Progress Reports
      if (action === "progress-student") {
        const reportData = await generateProgressReportByStudent(
          Number(studentId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_student.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "progress-teacher") {
        const reportData = await generateProgressReportByTeacher(
          Number(teacherId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_teacher.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "progress-subject") {
        const reportData = await generateProgressReportBySubject(
          Number(subjectId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_subject.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "progress-classroom") {
        const reportData = await generateProgressReportByClassroom(
          Number(classroomId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_classroom.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "progress-activity") {
        const reportData = await generateProgressReportByActivity(
          Number(activityId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_activity.pdf"
          )
          .send(Buffer.from(reportData));
      }

      if (action === "progress-facilitator") {
        const reportData = await generateProgressReportByFacilitator(
          Number(facilitatorId),
          String(entityType),
          authHeader
        );
        return res
          .status(200)
          .setHeader("Content-Type", "application/pdf")
          .setHeader(
            "Content-Disposition",
            "attachment; filename=progress_report_by_facilitator.pdf"
          )
          .send(Buffer.from(reportData));
      }
    }

    return res.status(405).json({ error: "Method not allowed" });
  } catch (err: any) {
    if (err.statusCode) {
      return res
        .status(err.statusCode)
        .json({ error: err.message || "Internal server error" });
    }
    return res
      .status(500)
      .json({ error: err.message || "Internal server error" });
  }
}
