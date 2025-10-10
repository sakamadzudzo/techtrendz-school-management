import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "../store/store";
import { Coursework } from "@/utils/types";

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

export function useCourseworksAll({
  page,
  size,
  sort,
  direction,
}: {
  page: number;
  size: number;
  sort?: string;
  direction?: "ASC" | "DESC";
}) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
    sort: sort || "",
    direction: direction || "DESC",
  });
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/coursework?action=get-all&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    courseworks: data as Coursework[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useCourseworkById(courseworkId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!courseworkId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/coursework?action=get-by-id&courseworkId=${courseworkId}`, token] : null,
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
    coursework: data as Coursework,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useCourseworksByType(type: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!type && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/coursework?action=get-all-by-type&type=${type}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    courseworks: data as Coursework[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useCourseworksBySubjectId(subjectId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!subjectId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/coursework?action=get-all-by-subject&subjectId=${subjectId}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    courseworks: data as Coursework[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useCourseworksByTypeBySubject(
  type: string | undefined,
  subjectId: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!type && !!subjectId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/coursework?action=get-all-by-type-by-subject&type=${type}&subjectId=${subjectId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    courseworks: data as Coursework[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSaveCoursework() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveCoursework = async (
    courseworkDto: any,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/coursework?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(courseworkDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Coursework creation failed";
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

  return { saveCoursework };
}

export function useSaveAllCourseworks() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveAllCourseworks = async (
    courseworksDto: any[],
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/coursework?action=save-all", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(courseworksDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Bulk coursework creation failed";
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

  return { saveAllCourseworks };
}
