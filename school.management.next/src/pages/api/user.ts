import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

const API_BASE = "http://localhost:4002/user";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveUser(userDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(userDto),
  });
  if (!res.ok) throw new ApiError("User creation failed", res.status);
  return res.json();
}

async function getUsers(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/view/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching users failed", res.status);
  return res.json();
}

async function getUsersByFilter(authHeader: any, query: any) {
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
  if (!res.ok) throw new ApiError("Fetching users failed", res.status);
  return res.json();
}

// async function updateUser(userId: string, userDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${userId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(userDto),
//   });
//   if (!res.ok) throw new ApiError("User update failed", res.status);
//   return res.json();
// }

// async function deleteUser(userId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${userId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("User deletion failed", res.status);
//   return res.json();
// }

async function getUserById(userId: string, authHeader: any) {
  const params = new URLSearchParams({ id: userId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching user failed", res.status);
  return res.json();
}

async function getUsersByExample(
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
  if (!res.ok)
    throw new ApiError("Fetching users by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, userId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveUser(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getUsersByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getUsers(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && userId) {
        const data = await getUserById(userId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getUsersByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (userId) {
    //     const data = await updateUser(userId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (userId) {
    //     const data = await deleteUser(userId as string, authHeader);
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
