import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError, Page } from "../utils/classes";
import { RootState } from "./../store/store"; // Adjust path if needed
import { Subject } from "@/utils/types";

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

export function useSubjectsAll({
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
    direction: direction ?? "DESC",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/subject?action=all&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    subjects: data as Page<Subject>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSubjectsAllByFilter({
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
    direction: direction ?? "DESC",
    filter: filter ?? "",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/subject?action=filtered&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    subjects: data as Page<Subject>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSubjectById(subjectId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!subjectId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/subject?action=one&subjectId=${subjectId}`, token]
      : null,
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
    subject: data as Subject,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useSubjectsByExample(exampleDto: any) {
  const token = useSelector((state: RootState) => state.auth.token);
  const key =
    exampleDto && token
      ? [`/api/subject?action=example`, exampleDto, token]
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
    subjects: data as Page<Subject>,
    isLoading: !!exampleDto && !!token && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSubjectsList({
  sort,
  direction,
}: {
  sort?: string;
  direction?: "ASC" | "DESC";
}) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const params = new URLSearchParams({
    sort: sort ? `${sort}` : "",
    direction: direction ?? "DESC",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/subject?action=list&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    subjects: data as Subject[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}
