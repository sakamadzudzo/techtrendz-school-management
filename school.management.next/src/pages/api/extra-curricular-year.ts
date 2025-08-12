import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/extra-curricular/year";

// Helper to get Authorization header from request
function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveExtraCurricularYear(
  extraCurricularYearDto: any,
  authHeader: any
) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(extraCurricularYearDto),
  });
  if (!res.ok)
    throw new ApiError("Extra curricular year creation failed", res.status);
  return res.json();
}

async function getExtraCurricularYears(authHeader: any, query: any) {
  const params = new URLSearchParams();
  if (query.page) params.append("page", query.page);
  if (query.size) params.append("size", query.size);
  if (query.sort) params.append("sortBy", query.sort);
  if (query.direction) params.append("sortDir", query.direction);

  const res = await fetch(`${API_BASE}/view/get/all?${params.toString()}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok)
    throw new ApiError("Fetching extra curricular years failed", res.status);
  return res.json();
}

async function getExtraCurricularYearsByFilter(authHeader: any, query: any) {
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
  if (!res.ok)
    throw new ApiError("Fetching extra curricular years failed", res.status);
  return res.json();
}

// async function updateExtraCurricularYear(extraCurricularYearId: string, extraCurricularYearDto: any, authHeader: any) {
//   const res = await fetch(`${API_BASE}/update/${extraCurricularYearId}`, {
//     method: "PUT",
//     headers: { "Content-Type": "application/json", ...authHeader },
//     body: JSON.stringify(extraCurricularYearDto),
//   });
//   if (!res.ok) throw new ApiError("Extra curricular year update failed", res.status);
//   return res.json();
// }

// async function deleteExtraCurricularYear(extraCurricularYearId: string, authHeader: any) {
//   const res = await fetch(`${API_BASE}/delete/${extraCurricularYearId}`, {
//     method: "DELETE",
//     headers: { "Content-Type": "application/json", ...authHeader },
//   });
//   if (!res.ok) throw new ApiError("Extra curricular year deletion failed", res.status);
//   return res.json();
// }

async function getExtraCurricularYearById(
  extraCurricularYearId: string,
  authHeader: any
) {
  const params = new URLSearchParams({ id: extraCurricularYearId }).toString();
  const res = await fetch(`${API_BASE}/view/get/one?` + params, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching extra curricular year failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getExtraCurricularYearsByExample(
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
    throw new ApiError(
      "Fetching extra curricular years by example failed",
      res.status
    );
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action, extraCurricularYearId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveExtraCurricularYear(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "example") {
        const data = await getExtraCurricularYearsByExample(
          req.body,
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "all") {
        const data = await getExtraCurricularYears(authHeader, req.query);
        return res.status(200).json(data);
      }
      if (action === "one" && extraCurricularYearId) {
        const data = await getExtraCurricularYearById(
          extraCurricularYearId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
      if (action === "filtered") {
        const data = await getExtraCurricularYearsByFilter(
          authHeader,
          req.query
        );
        return res.status(200).json(data);
      }
    }

    // if (req.method === "PUT") {
    //   if (extraCurricularYearId) {
    //     const data = await updateExtraCurricularYear(extraCurricularYearId as string, req.body, authHeader);
    //     return res.status(200).json(data);
    //   }
    // }

    // if (req.method === "DELETE") {
    //   if (extraCurricularYearId) {
    //     const data = await deleteExtraCurricularYear(extraCurricularYearId as string, authHeader);
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
