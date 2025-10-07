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

export function useSaveRating() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveRating = async (ratingDto: any) => {
    if (!token) throw new Error("No authentication token");

    const res = await fetch("/api/rating?action=save", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
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
  };

  return { saveRating };
}

export function useSaveAllRatings() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveAllRatings = async (ratingsDto: any[]) => {
    if (!token) throw new Error("No authentication token");

    const res = await fetch("/api/rating?action=save-all", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
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
  };

  return { saveAllRatings };
}