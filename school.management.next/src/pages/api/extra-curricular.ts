import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/extra-curricular";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function createExtraCurricular(extraCurricularDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(extraCurricularDto),
  });
  if (!res.ok) throw new ApiError("Extra curricular creation failed", res.status);
  return res.json();
}

async function getExtraCurriculars(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching extra curriculars failed", res.status);
  return res.json();
}

async function getExtraCurricularsByFilter(authHeader: any, query: any) {
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
  if (!res.ok) throw new ApiError("Fetching extra curriculars failed", res.status);
  return res.json();
}

// async function updateExtraCurricular(extraCurricularId: string, extraCurricularDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${extraCurricularId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(extraCurricularDto),
//   });
//   if (!res.ok) throw new ApiError("Extra curricular update failed", res.status);
//   return res.json();
// }

// async function deleteExtraCurricular(extraCurricularId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${extraCurricularId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Extra curricular deletion failed", res.status);
//   return res.json();
// }

async function getExtraCurricularById(extraCurricularId: string, authHeader: any) {
  const params = new URLSearchParams({ id: extraCurricularId }).toString();
  const res = await fetch(`${API_BASE}/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching extra curricular failed", res.status);
  return res.json();
}

async function getExtraCurricularsByExample(
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
    throw new ApiError("Fetching extra curriculars by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, extraCurricularId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "create") {
        const data = await createExtraCurricular(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getExtraCurricularsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getExtraCurriculars(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && extraCurricularId) {
        const data = await getExtraCurricularById(extraCurricularId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getExtraCurricularsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (extraCurricularId) {
    //     const data = await updateExtraCurricular(extraCurricularId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (extraCurricularId) {
    //     const data = await deleteExtraCurricular(extraCurricularId as string, authHeader);
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
