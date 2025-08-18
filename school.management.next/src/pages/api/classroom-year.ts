import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/classroom/year";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveClassroomYear(classroomYearDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(classroomYearDto),
  });
  if (!res.ok) {
    let backendError = "Classroom year creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getClassroomYears(authHeader: any, query: any) {
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
    let backendError = "Fetching classroom years failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getClassroomYearsByFilter(authHeader: any, query: any) {
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
    let backendError = "Fetching classroom years failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

// async function updateClassroomYear(classroomYearId: string, classroomYearDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${classroomYearId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(classroomYearDto),
//   });
//   if (!res.ok) throw new ApiError("Classroom year update failed", res.status);
//   return res.json();
// }

// async function deleteClassroomYear(classroomYearId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${classroomYearId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Classroom year deletion failed", res.status);
//   return res.json();
// }

async function getClassroomYearById(classroomYearId: string, authHeader: any) {
  const params = new URLSearchParams({ id: classroomYearId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching classroom year failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getClassroomYearsByExample(
  exampleDto: any,
  authHeader: any,
  query: any
) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);
  if (query.filter) params.append("filter", query.filter);

  const res = await fetch(
    `${API_BASE}/view/get/all/by/example?${params.toString()}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
      body: JSON.stringify(exampleDto),
    }
  );
  if (!res.ok) {
    let backendError = "Fetching classroom years by example failed";
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
    const { action, classroomYearId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveClassroomYear(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getClassroomYearsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getClassroomYears(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && classroomYearId) {
        const data = await getClassroomYearById(
          classroomYearId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getClassroomYearsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (classroomYearId) {
    //     const data = await updateClassroomYear(classroomYearId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (classroomYearId) {
    //     const data = await deleteClassroomYear(classroomYearId as string, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

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
