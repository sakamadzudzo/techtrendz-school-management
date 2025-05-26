import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/classroom";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function createClassroom(classroomDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(classroomDto),
  });
  if (!res.ok) throw new ApiError("Classroom creation failed", res.status);
  return res.json();
}

async function getClassrooms(authHeader: any) {
  // fetch with pagination parameters if needed
  // const params = new URLSearchParams({ page: String(page), size: String(size) }).toString();
  // const res = await fetch(`${API_BASE}/get/all?${params}`, {
  const res = await fetch(`${API_BASE}/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching classrooms failed", res.status);
  return res.json();
}

// async function updateClassroom(classroomId: string, classroomDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${classroomId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(classroomDto),
//   });
//   if (!res.ok) throw new ApiError("Classroom update failed", res.status);
//   return res.json();
// }

// async function deleteClassroom(classroomId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${classroomId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Classroom deletion failed", res.status);
//   return res.json();
// }

async function getClassroomById(classroomId: string, authHeader: any) {
  const params = new URLSearchParams({ id: classroomId }).toString();
  const res = await fetch(`${API_BASE}/get/one` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching classroom failed", res.status);
  return res.json();
}

async function getClassroomsByExample(exampleDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/example`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(exampleDto),
  });
  if (!res.ok)
    throw new ApiError("Fetching classrooms by example failed", res.status);
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
        const data = await createClassroom(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getClassroomsByExample(req.body, authHeader);
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getClassrooms(authHeader);
        return res.status(200).json(data);
      }
      if (action === "one" && classroomId) {
        const data = await getClassroomById(classroomId as string, authHeader);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (classroomId) {
    //     const data = await updateClassroom(classroomId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (classroomId) {
    //     const data = await deleteClassroom(classroomId as string, authHeader);
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