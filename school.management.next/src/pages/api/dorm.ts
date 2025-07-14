import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/dorm";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveDorm(dormDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(dormDto),
  });
  if (!res.ok) throw new ApiError("Dorm creation failed", res.status);
  return res.json();
}

async function getDorms(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching dorms failed", res.status);
  return res.json();
}

async function getDormsByFilter(authHeader: any, query: any) {
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
  if (!res.ok) throw new ApiError("Fetching dorms failed", res.status);
  return res.json();
}

// async function updateDorm(dormId: string, dormDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${dormId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(dormDto),
//   });
//   if (!res.ok) throw new ApiError("Dorm update failed", res.status);
//   return res.json();
// }

// async function deleteDorm(dormId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${dormId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Dorm deletion failed", res.status);
//   return res.json();
// }

async function getDormById(dormId: string, authHeader: any) {
  const params = new URLSearchParams({ id: dormId }).toString();
  const res = await fetch(`${API_BASE}/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching dorm failed", res.status);
  return res.json();
}

async function getDormsByExample(
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
    throw new ApiError("Fetching dorms by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, dormId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveDorm(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getDormsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getDorms(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && dormId) {
        const data = await getDormById(dormId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getDormsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (dormId) {
    //     const data = await updateDorm(dormId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (dormId) {
    //     const data = await deleteDorm(dormId as string, authHeader);
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
