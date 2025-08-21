import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/student";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveStudent(studentDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(studentDto),
  });
  if (!res.ok) {
    let backendError = "Student creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getStudents(authHeader: any, query: any) {
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
    let backendError = "Fetching students failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getStudentsByFilter(authHeader: any, query: any) {
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
    let backendError = "Fetching students failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

// async function updateStudent(studentId: string, studentDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${studentId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(studentDto),
//   });
//   if (!res.ok) throw new ApiError("Student update failed", res.status);
//   return res.json();
// }

// async function deleteStudent(studentId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${studentId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Student deletion failed", res.status);
//   return res.json();
// }

async function getStudentById(studentId: string, authHeader: any) {
  const params = new URLSearchParams({ id: studentId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching student failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getStudentsByExample(
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
    let backendError = "Fetching students by example failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message ?? errorBody.error ?? backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getStudentsDropdown(authHeader: any) {
  const res = await fetch(`${API_BASE}/dropdown/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching students failed";
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
    const { action, studentId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveStudent(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getStudentsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getStudents(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && studentId) {
        const data = await getStudentById(studentId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getStudentsByFilter(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "dropdown") {
        const data = await getStudentsDropdown(authHeader);
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (studentId) {
    //     const data = await updateStudent(studentId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (studentId) {
    //     const data = await deleteStudent(studentId as string, authHeader);
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
