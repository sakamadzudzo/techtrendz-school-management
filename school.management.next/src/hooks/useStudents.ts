import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "./../store/store"; // Adjust path if needed
import { Student } from "@/utils/types";

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

export function useStudentsAll({
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
    direction: direction || "DESC"
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/student?action=all&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    data,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useStudentsAllByFilter({
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
      ? [`/api/student?action=filtered&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    data,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useStudentById(studentId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/student?action=one&studentId=${studentId}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    student: data as Student,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useStudentsByExample(exampleDto: any) {
  const token = useSelector((state: RootState) => state.auth.token);
  const key =
    exampleDto && token
      ? [`/api/student?action=example`, exampleDto, token]
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
    students: data,
    isLoading: !!exampleDto && !!token && !error && !data,
    isError: error,
    mutate,
  };
}
