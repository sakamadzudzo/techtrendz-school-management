import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/classroom/year";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function createClassroomYear(classroomDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(classroomDto),
  });
  if (!res.ok) throw new ApiError("Classroom year creation failed", res.status);
  return res.json();
}

async function getClassroomYears(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);
  if (query.filter) params.append("filter", query.filter);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching classroom years failed", res.status);
  return res.json();
}

// async function updateClassroomYear(classroomId: string, classroomDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${classroomId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(classroomDto),
//   });
//   if (!res.ok) throw new ApiError("Classroom year update failed", res.status);
//   return res.json();
// }

// async function deleteClassroomYear(classroomId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${classroomId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Classroom year deletion failed", res.status);
//   return res.json();
// }

async function getClassroomYearById(classroomId: string, authHeader: any) {
  const params = new URLSearchParams({ id: classroomId }).toString();
  const res = await fetch(`${API_BASE}/get/one` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching classroom year failed", res.status);
  return res.json();
}

async function getClassroomYearsByExample(exampleDto: any, authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);
  if (query.filter) params.append("filter", query.filter);
  
  const res = await fetch(`${API_BASE}/get/all/by/example?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(exampleDto),
  });
  if (!res.ok)
    throw new ApiError("Fetching classroom years by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, classroomId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "create") {
        const data = await createClassroomYear(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getClassroomYearsByExample(req.body, authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getClassroomYears(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && classroomId) {
        const data = await getClassroomYearById(classroomId as string, authHeader);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (classroomId) {
    //     const data = await updateClassroomYear(classroomId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (classroomId) {
    //     const data = await deleteClassroomYear(classroomId as string, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

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
