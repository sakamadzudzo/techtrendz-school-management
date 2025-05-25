import type { NextApiRequest, NextApiResponse } from "next";
import { ApiError } from "./../../utils/classes";

// Example backend API base URL
// const API_BASE = process.env.AUTH_API_BASE + "/auth" || "http://localhost:4002/auth";
const API_BASE = "http://localhost:4002/auth";

async function login(authDto: {
  username: string;
  password: string;
  rememberMe: boolean;
}) {
  const res = await fetch(`${API_BASE}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(authDto),
  });
  if (!res.ok)
    throw new ApiError("Login failed: " + (await res.json()).error, res.status);
  return res.json();
}

async function logout(token: string) {
  const res = await fetch(`${API_BASE}/logout`, {
    method: "POST",
    headers: { Authorization: `Bearer ${token}` },
  });
  if (!res.ok)
    throw new ApiError(
      "Logout failed: " + (await res.json()).error,
      res.status
    );
  return res.json();
}

async function refresh(refreshToken: string) {
  const res = await fetch(`${API_BASE}/refresh`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ refreshToken }),
  });
  if (!res.ok) throw new ApiError("Token refresh failed", res.status);
  return res.text();
}

async function register(registerDto: any) {
  const res = await fetch(`${API_BASE}/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(registerDto),
  });
  if (!res.ok) throw new ApiError("Registration failed", res.status);
  return res.json();
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  try {
    const { action } = req.query;

    if (req.method === "POST") {
      if (action === "login") {
        const data = await login(req.body);
        return res.status(200).json(data);
      }
      if (action === "logout") {
        const data = await logout(req.body.token);
        return res.status(200).json(data);
      }
      if (action === "refresh") {
        const data = await refresh(req.body.refreshToken);
        return res.status(200).json(data);
      }
      if (action === "register") {
        const data = await register(req.body);
        return res.status(201).json(data);
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
