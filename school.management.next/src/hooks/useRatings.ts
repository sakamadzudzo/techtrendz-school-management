import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "../store/store"; // Adjust path if needed
import { Rating } from "@/utils/types";

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

export function useRatingsAll() {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/rating?action=all`, token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    ratings: data as Rating[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useRatingById(ratingId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!ratingId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/rating?action=one&ratingId=${ratingId}`, token] : null,
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
    rating: data as Rating,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useRatingsByActivityId(activityId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!activityId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/rating?action=by-activity&activityId=${activityId}`, token] : null,
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
    ratings: data as Rating[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}


export function useSaveAllRatings() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveAllRatings = async (
    ratingsDto: any[],
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/rating?action=save-all", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(ratingsDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || data.message || "Bulk rating creation failed";
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

  return { saveAllRatings };
}

export function useSaveRating() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveRating = async (
    ratingDto: any,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/rating?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(ratingDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Rating creation failed";
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

  return { saveRating };
}