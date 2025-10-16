import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/parent";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveParent(parentDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(parentDto),
  });
  if (!res.ok) {
    let backendError = "Parent creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function saveAllParents(parentsDto: any[], authHeader: any) {
  const res = await fetch(`${API_BASE}/save/all`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(parentsDto),
  });
  if (!res.ok) {
    let backendError = "Parents creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getParents(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/view/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching parents failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getParentsByFilter(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);
  if (query.filter) params.append("filter", query.filter);

  const res = await fetch(
    `${API_BASE}/view/get/all/by/filter?${params.toString()}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) {
    let backendError = "Fetching parents failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getParentById(parentId: string, authHeader: any) {
  const params = new URLSearchParams({ id: parentId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching parent failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getParentByPersonId(personId: string, authHeader: any) {
  const params = new URLSearchParams({ personId: personId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one/by/person?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching parent failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getParentsDropdown(authHeader: any) {
  const res = await fetch(`${API_BASE}/dropdown/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching parents failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function manageParentStudents(parentId: string, studentIds: number[], authHeader: any) {
  const params = new URLSearchParams({ parentId: parentId }).toString();
  const res = await fetch(`${API_BASE}/manage/students?` + params, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(studentIds),
  });
  if (!res.ok) {
    let backendError = "Managing parent students failed";
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
    const { action, parentId, personId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveParent(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "save-all") {
        const data = await saveAllParents(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "manage-students" && parentId) {
        const data = await manageParentStudents(parentId as string, req.body, authHeader);
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getParents(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && parentId) {
        const data = await getParentById(parentId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "one-by-person" && personId) {
        const data = await getParentByPersonId(personId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getParentsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "dropdown") {
        const data = await getParentsDropdown(authHeader);
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