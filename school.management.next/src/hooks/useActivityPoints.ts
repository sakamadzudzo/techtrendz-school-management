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

export function useSaveActivityPoint() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveActivityPoint = async (
    activityPointDto: any,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/activity-point?action=save-activity-point", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(activityPointDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Activity point creation failed";
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

  return { saveActivityPoint };
}

export function useSaveAllActivityPoints() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveAllActivityPoints = async (
    activityPointsDto: any[],
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/activity-point?action=save-all-activity-points", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(activityPointsDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Bulk activity point creation failed";
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

  return { saveAllActivityPoints };
}
