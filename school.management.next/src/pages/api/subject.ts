import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/subject";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function createSubject(subjectDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(subjectDto),
  });
  if (!res.ok) throw new ApiError("Subject creation failed", res.status);
  return res.json();
}

async function getSubjects(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching subjects failed", res.status);
  return res.json();
}

async function getSubjectsByFilter(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);
  if (query.filter) params.append("filter", query.filter);

  const res = await fetch(
    `${API_BASE}/get/all/by/filter?${params.toString()}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
    }
  );
  if (!res.ok) throw new ApiError("Fetching subjects failed", res.status);
  return res.json();
}

// async function updateSubject(subjectId: string, subjectDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${subjectId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(subjectDto),
//   });
//   if (!res.ok) throw new ApiError("Subject update failed", res.status);
//   return res.json();
// }

// async function deleteSubject(subjectId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${subjectId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Subject deletion failed", res.status);
//   return res.json();
// }

async function getSubjectById(subjectId: string, authHeader: any) {
  const params = new URLSearchParams({ id: subjectId }).toString();
  const res = await fetch(`${API_BASE}/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching subject failed", res.status);
  return res.json();
}

async function getSubjectsByExample(
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
    `${API_BASE}/get/all/by/example?${params.toString()}`,
    {
      method: "GET",
      headers: { "Content-Type": "application/json", ...authHeader },
      body: JSON.stringify(exampleDto),
    }
  );
  if (!res.ok)
    throw new ApiError("Fetching subjects by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, subjectId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "create") {
        const data = await createSubject(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getSubjectsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getSubjects(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && subjectId) {
        const data = await getSubjectById(subjectId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getSubjectsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (subjectId) {
    //     const data = await updateSubject(subjectId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (subjectId) {
    //     const data = await deleteSubject(subjectId as string, authHeader);
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
