import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/dorm/year";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function createDormYear(dormYearDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(dormYearDto),
  });
  if (!res.ok) throw new ApiError("Dorm year creation failed", res.status);
  return res.json();
}

async function getDormYears(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching dorm years failed", res.status);
  return res.json();
}

async function getDormYearsByFilter(authHeader: any, query: any) {
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
  if (!res.ok) throw new ApiError("Fetching dorm years failed", res.status);
  return res.json();
}

// async function updateDormYear(dormYearId: string, dormYearDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${dormYearId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(dormYearDto),
//   });
//   if (!res.ok) throw new ApiError("Dorm year update failed", res.status);
//   return res.json();
// }

// async function deleteDormYear(dormYearId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${dormYearId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Dorm year deletion failed", res.status);
//   return res.json();
// }

async function getDormYearById(dormYearId: string, authHeader: any) {
  const params = new URLSearchParams({ id: dormYearId }).toString();
  const res = await fetch(`${API_BASE}/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching dorm years failed", res.status);
  return res.json();
}

async function getDormYearsByExample(
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
    throw new ApiError("Fetching dorm years by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, dormYearId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "create") {
        const data = await createDormYear(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getDormYearsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getDormYears(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && dormYearId) {
        const data = await getDormYearById(dormYearId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getDormYearsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (dormYearId) {
    //     const data = await updateDormYear(dormYearId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (dormYearId) {
    //     const data = await deleteDormYear(dormYearId as string, authHeader);
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
