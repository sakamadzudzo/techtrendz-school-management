import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";
import { API_URL } from "@/utils/helpers";

const API_BASE = API_URL + "/rating";

function getAuthHeader(req: NextApiRequest) {
  const auth = req.headers.authorization;
  return auth ? { Authorization: auth } : {};
}

async function saveRating(ratingDto: any, authHeader: any) {
  const res = await fetch(`${API_BASE}/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(ratingDto),
  });
  if (!res.ok) {
    let backendError = "Rating creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function saveAllRatings(ratingsDto: any[], authHeader: any) {
  const res = await fetch(`${API_BASE}/save/all`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeader },
    body: JSON.stringify(ratingsDto),
  });
  if (!res.ok) {
    let backendError = "Bulk rating creation failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getRatingById(ratingId: string, authHeader: any) {
  const params = new URLSearchParams({ id: ratingId }).toString();
  const res = await fetch(`${API_BASE}/get/one?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching rating failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllRatings(authHeader: any) {
  const res = await fetch(`${API_BASE}/get/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching all ratings failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
    } catch {}
    throw new ApiError(backendError, res.status);
  }
  return res.json();
}

async function getAllRatingsByActivityId(activityId: string, authHeader: any) {
  const params = new URLSearchParams({ activityId }).toString();
  const res = await fetch(`${API_BASE}/get/all/by/activity?${params}`, {
    method: "GET",
    headers: { "Content-Type": "application/json", ...authHeader },
  });
  if (!res.ok) {
    let backendError = "Fetching ratings by activity failed";
    try {
      const errorBody = await res.json();
      backendError = errorBody.message || errorBody.error || backendError;
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
    const { action, ratingId, activityId } = req.query;
    const authHeader = getAuthHeader(req);

    if (req.method === "POST") {
      if (action === "save") {
        const data = await saveRating(req.body, authHeader);
        return res.status(201).json(data);
      }
      if (action === "save-all") {
        const data = await saveAllRatings(req.body, authHeader);
        return res.status(201).json(data);
      }
    }

    if (req.method === "GET") {
      if (action === "one" && ratingId) {
        const data = await getRatingById(ratingId as string, authHeader);
        return res.status(200).json(data);
      }
      if (action === "all") {
        const data = await getAllRatings(authHeader);
        return res.status(200).json(data);
      }
      if (action === "by-activity" && activityId) {
        const data = await getAllRatingsByActivityId(
          activityId as string,
          authHeader
        );
        return res.status(200).json(data);
      }
    }

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