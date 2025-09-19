import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/mark";

function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveMark(markDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(markDto),
  });
  if (!res.ok) {
    let backendError = "Mark creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function saveAllMarks(marksDto: any[], authHeader: any) {
  const res = await fetch(`${API_BASE}/save/all`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(marksDto),
  });
  if (!res.ok) {
    let backendError = "Bulk mark creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getMarkById(markId: string, authHeader: any) {
  const params = new URLSearchParams({ id: markId }).toString();
  const res = await fetch(`${API_BASE}/get/by/id?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching mark failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarks(authHeader: any) {
  const res = await fetch(`${API_BASE}/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching all marks failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksByCourseworkId(courseworkId: string, authHeader: any) {
  const params = new URLSearchParams({ courseworkId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/coursework?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching marks by coursework failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksByStudentId(studentId: string, authHeader: any) {
  const params = new URLSearchParams({ studentId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/student?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching marks by student failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksBySubjectId(subjectId: string, authHeader: any) {
  const params = new URLSearchParams({ subjectId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/subject?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching marks by subject failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksBySubjectIdByCourseworkId(
  subjectId: string,
  courseworkId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ subjectId, courseworkId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/subject/by/coursework?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching marks by subject and coursework failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksByStudentIdBySubjectId(
  studentId: string,
  subjectId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId, subjectId }).toString();
  const res = await fetch(
    `${API_BASE}/get/all/by/student/by/subject?${params}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Fetching marks by student and subject failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getMarkByStudentIdByCourseworkId(
  studentId: string,
  courseworkId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId, courseworkId }).toString();
  const res = await fetch(`${API_BASE}/get/by/student/by/coursework?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching mark by student and coursework failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksBySubjectIdByCourseworkType(
  subjectId: string,
  type: string,
  authHeader: any
) {
  const params = new URLSearchParams({ subjectId, type }).toString();
  const res = await fetch(
    `${API_BASE}/get/all/by/subject/by/coursework/type?${params}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Fetching marks by subject and coursework type failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksByStudentIdByCourseworkType(
  studentId: string,
  type: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId, type }).toString();
  const res = await fetch(
    `${API_BASE}/get/all/by/student/coursework/type?${params}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Fetching marks by student and coursework type failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllMarksByStudentIdBySubjectIdByCourseworkType(
  studentId: string,
  subjectId: string,
  type: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId, subjectId, type }).toString();
  const res = await fetch(
    `${API_BASE}/get/all/by/student/by/subject/by/coursework/type?${params}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError =
      "Fetching marks by student, subject, and coursework type failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, markId, courseworkId, studentId, subjectId, type } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save-mark") {
        const data = await saveMark(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "save-all-marks") {
        const data = await saveAllMarks(req.body, authHeader);
        return res.status(201).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "get-by-id" && markId) {
        const data = await getMarkById(markId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all") {
        const data = await getAllMarks(authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all-by-coursework" && courseworkId) {
        const data = await getAllMarksByCourseworkId(courseworkId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all-by-student" && studentId) {
        const data = await getAllMarksByStudentId(
          studentId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-subject" && subjectId) {
        const data = await getAllMarksBySubjectId(
          subjectId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-subject-by-coursework" && subjectId && courseworkId) {
        const data = await getAllMarksBySubjectIdByCourseworkId(
          subjectId as string,
          courseworkId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (
        action === "get-all-by-student-by-subject" &&
        studentId &&
        subjectId
      ) {
        const data = await getAllMarksByStudentIdBySubjectId(
          studentId as string,
          subjectId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-by-student-by-coursework" && studentId && courseworkId) {
        const data = await getMarkByStudentIdByCourseworkId(
          studentId as string,
          courseworkId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-subject-by-coursework-type" && subjectId && type) {
        const data = await getAllMarksBySubjectIdByCourseworkType(
          subjectId as string,
          type as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-student-by-coursework-type" && studentId && type) {
        const data = await getAllMarksByStudentIdByCourseworkType(
          studentId as string,
          type as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (
        action === "get-all-by-student-by-subject-by-coursework-type" &&
        studentId &&
        subjectId &&
        type
      ) {
        const data = await getAllMarksByStudentIdBySubjectIdByCourseworkType(
          studentId as string,
          subjectId as string,
          type as string,
          authHeader
        );
        return res.status(200).json(data);
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
