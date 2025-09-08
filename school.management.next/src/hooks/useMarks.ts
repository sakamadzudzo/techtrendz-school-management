import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "../store/store";
import { Mark } from "@/utils/types";

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

export function useMarksAll() {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? ["/api/mark?action=get-all", token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarkById(markId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!markId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/mark?action=get-by-id&markId=${markId}`, token] : null,
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
    mark: data as Mark,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useMarksByCourseworkId(courseworkId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!courseworkId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/mark?action=get-all-by-coursework&courseworkId=${courseworkId}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksByStudentId(studentId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/mark?action=get-all-by-student&studentId=${studentId}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksBySubjectId(subjectId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!subjectId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/mark?action=get-all-by-subject&subjectId=${subjectId}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksBySubjectIdByCourseworkId(
  subjectId: string | undefined,
  courseworkId: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!subjectId && !!courseworkId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-all-by-subject-by-coursework&subjectId=${subjectId}&courseworkId=${courseworkId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksByStudentIdBySubjectId(
  studentId: string | undefined,
  subjectId: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!subjectId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-all-by-student-by-subject&studentId=${studentId}&subjectId=${subjectId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarkByStudentIdByCourseworkId(
  studentId: string | undefined,
  courseworkId: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!courseworkId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-by-student-by-coursework&studentId=${studentId}&courseworkId=${courseworkId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    mark: data as Mark,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksBySubjectIdByCourseworkType(
  subjectId: string | undefined,
  type: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!subjectId && !!type && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-all-by-subject-by-coursework-type&subjectId=${subjectId}&type=${type}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksByStudentIdByCourseworkType(
  studentId: string | undefined,
  type: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!type && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-all-by-student-by-coursework-type&studentId=${studentId}&type=${type}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useMarksByStudentIdBySubjectIdByCourseworkType(
  studentId: string | undefined,
  subjectId: string | undefined,
  type: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!subjectId && !!type && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/mark?action=get-all-by-student-by-subject-by-coursework-type&studentId=${studentId}&subjectId=${subjectId}&type=${type}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    marks: data as Mark[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}
