import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/coursework";

function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveCoursework(courseworkDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(courseworkDto),
  });
  if (!res.ok) {
    let backendError = "Coursework creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function saveAllCourseworks(courseworksDto: any[], authHeader: any) {
  const res = await fetch(`${API_BASE}/save/all`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(courseworksDto),
  });
  if (!res.ok) {
    let backendError = "Bulk coursework creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getCourseworkById(courseworkId: string, authHeader: any) {
  const params = new URLSearchParams({ id: courseworkId }).toString();
  const res = await fetch(`${API_BASE}/get/by/id?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching coursework failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllCourseworks(authHeader: any) {
  const res = await fetch(`${API_BASE}/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching all courseworks failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllCourseworksByType(type: string, authHeader: any) {
  const params = new URLSearchParams({ type }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/coursework/type?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching courseworks by type failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllCourseworksBySubjectId(subjectId: string, authHeader: any) {
  const params = new URLSearchParams({ subjectId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/subject?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching courseworks by subject failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllCourseworksByTypeBySubject(
  type: string,
  subjectId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ type, subjectId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/type/by/subject?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching courseworks by type and subject failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
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
    const { action, courseworkId, type, subjectId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveCoursework(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "save-all") {
        const data = await saveAllCourseworks(req.body, authHeader);
        return res.status(201).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "get-by-id" && courseworkId) {
        const data = await getCourseworkById(courseworkId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all") {
        const data = await getAllCourseworks(authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all-by-type" && type) {
        const data = await getAllCourseworksByType(type as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "get-all-by-subject" && subjectId) {
        const data = await getAllCourseworksBySubjectId(
          subjectId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "get-all-by-type-by-subject" && type && subjectId) {
        const data = await getAllCourseworksByTypeBySubject(
          type as string,
          subjectId as string,
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
        .json({ error: err.message ?? "Internal server error" });
    }
    return res
      .status(500)
      .json({ error: err.message ?? "Internal server error" });
  }
}
