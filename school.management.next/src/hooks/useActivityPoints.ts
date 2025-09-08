import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "../store/store";
import { ActivityPoint } from "@/utils/types";

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

export function useActivityPointsAll() {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? ["/api/activity-point?action=get-all", token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    activityPoints: data as ActivityPoint[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useActivityPointById(activityPointId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!activityPointId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/activity-point?action=get-by-id&activityPointId=${activityPointId}`,
          token,
        ]
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
    activityPoint: data as ActivityPoint,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useActivityPointsByActivityId(activityId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!activityId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/activity-point?action=get-all-by-activity&activityId=${activityId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    activityPoints: data as ActivityPoint[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useActivityPointsByStudentId(studentId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/activity-point?action=get-all-by-student&studentId=${studentId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    activityPoints: data as ActivityPoint[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useActivityPointByStudentIdByActivityId(
  studentId: string | undefined,
  activityId: string | undefined
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!studentId && !!activityId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [
          `/api/activity-point?action=get-by-student-by-activity&studentId=${studentId}&activityId=${activityId}`,
          token,
        ]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    activityPoint: data as ActivityPoint,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}
