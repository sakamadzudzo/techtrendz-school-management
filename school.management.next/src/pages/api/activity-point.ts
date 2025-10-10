import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/activity-point";

function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveActivityPoint(activityPointDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(activityPointDto),
  });
  if (!res.ok) {
    let backendError = "ActivityPoint creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function saveAllActivityPoints(
  activityPointsDto: any[],
  authHeader: any
) {
  const res = await fetch(`${API_BASE}/save/all`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(activityPointsDto),
  });
  if (!res.ok) {
    let backendError = "Bulk activity point creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getActivityPointById(activityPointId: string, authHeader: any) {
  const params = new URLSearchParams({ id: activityPointId }).toString();
  const res = await fetch(`${API_BASE}/get/by/id?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching activity point failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllActivityPoints(authHeader: any) {
  const res = await fetch(`${API_BASE}/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching all activity points failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllActivityPointsByActivityId(
  activityId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ activityId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/activity?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching activity points by activity failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllActivityPointsByStudentId(
  studentId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/student?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching activity points by student failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getActivityPointByStudentIdByActivityId(
  studentId: string,
  activityId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ studentId, activityId }).toString();
  const res = await fetch(`${API_BASE}/get/by/student/by/activity?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching activity point by student and activity failed";
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
    const { action, activityPointId, activityId, studentId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save-activity-point") {
        const data = await saveActivityPoint(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "save-all-activity-points") {
        const data = await saveAllActivityPoints(req.body, authHeader);
        return res.status(201).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "get-by-id" && activityPointId) {
        const data = await getActivityPointById(
          activityPointId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all") {
        const data = await getAllActivityPoints(authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all-by-activity" && activityId) {
        const data = await getAllActivityPointsByActivityId(
          activityId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-student" && studentId) {
        const data = await getAllActivityPointsByStudentId(
          studentId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-by-student-by-activity" && studentId && activityId) {
        const data = await getActivityPointByStudentIdByActivityId(
          studentId as string,
          activityId as string,
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
