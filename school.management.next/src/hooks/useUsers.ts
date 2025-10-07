import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError, Page } from "../utils/classes";
import { RootState } from "./../store/store"; // Adjust path if needed
import { UserView } from "@/utils/types";

const fetcherWithAuth = (url: string, token?: string) =>
  fetch(url, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  }).then(async (res) => {
    if (!res.ok) {
      throw new ApiError(
        "Fetch failed: " + (await res.json()).error,
        res.status
      );
    }
    return res.json();
  });

export function useUsersAll({
  page,
  size,
  sort,
  direction,
  filter,
}: {
  page: number;
  size: number;
  sort?: string;
  direction?: "ASC" | "DESC";
  filter?: string;
}) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
    sort: sort ? `${sort}` : "",
    direction: direction || "DESC",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/user?action=all&${params.toString()}`, token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    users: data as Page<UserView>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useUsersAllByFilter({
  page,
  size,
  sort,
  direction,
  filter,
}: {
  page: number;
  size: number;
  sort?: string;
  direction?: "ASC" | "DESC";
  filter?: string;
}) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
    sort: sort ? `${sort}` : "",
    direction: direction || "DESC",
    filter: filter || "",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/user?action=filtered&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    users: data as Page<UserView>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useUserById(userId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!userId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/user?action=one&userId=${userId}`, token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );

  let errorMessage: string | undefined = undefined;
  let statusCode: number | undefined = undefined;
  if (error instanceof ApiError) {
    errorMessage = error.message;
    statusCode = error.statusCode;
  } else if (error && error.message) {
    errorMessage = error.message;
  }

  return {
    user: data as UserView,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useUsersByExample(exampleDto: any) {
  const token = useSelector((state: RootState) => state.auth.token);
  const key =
    exampleDto && token
      ? [`/api/user?action=example`, exampleDto, token]
      : null;
  const fetcherWithBody = async ([url, body, token]: [string, any, string]) => {
    const res = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      body: JSON.stringify(body),
    });
    if (!res.ok) {
      throw new ApiError(
        "Fetch failed: " + (await res.json()).error,
        res.status
      );
    }
    return res.json();
  };
  const { data, error, mutate } = useSWR(key, fetcherWithBody);
  return {
    users: data as Page<UserView>,
    isLoading: !!exampleDto && !!token && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSaveUser() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveUser = async (
    userDto: any,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/user?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(userDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "User creation failed";
        const code = res.status || 500;
        onError?.(err, code);
        return { success: false, error: err, statusCode: code };
      } else {
        onSuccess?.();
        return { success: true, data };
      }
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || "Network/Server error";
      const statusCode = err.status || 500;
      onError?.(errorMessage, statusCode);
      return { success: false, error: errorMessage, statusCode };
    } finally {
      decrementLoading?.();
    }
  };

  return { saveUser };
}
