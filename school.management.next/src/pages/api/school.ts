import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/school";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveSchool(schoolDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(schoolDto),
  });
  console.log(authHeader);
  if (!res.ok) throw new ApiError("School creation failed", res.status);
  return res.json();
}

async function getSchools(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching schools failed", res.status);
  return res.json();
}

async function getSchoolsList(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/get/all/list?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) throw new ApiError("Fetching schools failed", res.status);
  return res.json();
}

async function getSchoolsByFilter(authHeader: any, query: any) {
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
  if (!res.ok) throw new ApiError("Fetching schools failed", res.status);
  return res.json();
}

// async function updateSchool(schoolId: string, schoolDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${schoolId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(schoolDto),
//   });
//   if (!res.ok) throw new ApiError("School update failed", res.status);
//   return res.json();
// }

// async function deleteSchool(schoolId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${schoolId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("School deletion failed", res.status);
//   return res.json();
// }

async function getSchoolById(schoolId: string, authHeader: any) {
  const params = new URLSearchParams({ id: schoolId }).toString();
  const res = await fetch(`${API_BASE}/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching school failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getSchoolsByExample(
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
    throw new ApiError("Fetching schools by example failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, schoolId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveSchool(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getSchoolsByExample(req.body, authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getSchools(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && schoolId) {
        const data = await getSchoolById(schoolId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getSchoolsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "list") {
        const data = await getSchoolsList(authHeader, req.query);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (schoolId) {
    //     const data = await updateSchool(schoolId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (schoolId) {
    //     const data = await deleteSchool(schoolId as string, authHeader);
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
