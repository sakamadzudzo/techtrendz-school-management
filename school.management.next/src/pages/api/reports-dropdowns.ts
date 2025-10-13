import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/reports/dropdowns";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function getAllDropdowns(authHeader: any) {
  const res = await fetch(`${API_BASE}/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching all dropdowns failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getSelectiveDropdowns(
  queryParams: any,
  authHeader: any
) {
  const params = new URLSearchParams();
  if (queryParams.students !== undefined) params.append("students", queryParams.students);
  if (queryParams.teachers !== undefined) params.append("teachers", queryParams.teachers);
  if (queryParams.courseworks !== undefined) params.append("courseworks", queryParams.courseworks);
  if (queryParams.classrooms !== undefined) params.append("classrooms", queryParams.classrooms);
  if (queryParams.subjects !== undefined) params.append("subjects", queryParams.subjects);
  if (queryParams.extraCurriculars !== undefined) params.append("extraCurriculars", queryParams.extraCurriculars);
  if (queryParams.calendarYears !== undefined) params.append("calendarYears", queryParams.calendarYears);

  const res = await fetch(`${API_BASE}/selective?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching selective dropdowns failed";
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
    const authHeader = getAuthHeader(req);

    if (req.method === "GET") {
      const { action, students, teachers, courseworks, classrooms, subjects, extraCurriculars, calendarYears } = req.query;

      if (action === "all") {
        const data = await getAllDropdowns(authHeader);
        return res.status(200).json(data);
      }

      if (action === "selective") {
        const queryParams = {
          students: students === "true",
          teachers: teachers === "true",
          courseworks: courseworks === "true",
          classrooms: classrooms === "true",
          subjects: subjects === "true",
          extraCurriculars: extraCurriculars === "true",
          calendarYears: calendarYears !== "false", // default to true
        };
        const data = await getSelectiveDropdowns(queryParams, authHeader);
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