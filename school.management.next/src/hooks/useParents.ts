import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError, Page } from "../utils/classes";
import { RootState } from "./../store/store";
import { Parent, ParentView, YearDropdown } from "@/utils/types";

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

export function useParentsAll({
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
    shouldFetch
      ? [`/api/parent?action=all&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    parents: data as Page<ParentView>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useParentsAllByFilter({
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
      ? [`/api/parent?action=filtered&${params.toString()}`, token]
      : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    parents: data as Page<ParentView>,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useParentById(parentId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!parentId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/parent?action=one&parentId=${parentId}`, token]
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
    parent: data as ParentView,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useParentByPersonId(personId: string | undefined) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!personId && !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/parent?action=one-by-person&personId=${personId}`, token]
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
    parent: data as ParentView,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    errorMessage,
    statusCode,
    mutate,
  };
}

export function useParentsDropdown() {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? [`/api/parent?action=dropdown`, token] : null,
    ([url, token]) => fetcherWithAuth(url, token)
  );
  return {
    parents: data as YearDropdown[],
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useSaveParent() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveParent = async (
    parentDto: any,
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/parent?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(parentDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Parent creation failed";
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

  return { saveParent };
}

export function useSaveAllParents() {
  const token = useSelector((state: RootState) => state.auth.token);

  const saveAllParents = async (
    parentsDto: any[],
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch("/api/parent?action=save-all", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(parentsDto),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Parents creation failed";
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

  return { saveAllParents };
}

export function useManageParentStudents() {
  const token = useSelector((state: RootState) => state.auth.token);

  const manageStudents = async (
    parentId: string,
    studentIds: number[],
    incrementLoading?: () => void,
    decrementLoading?: () => void,
    onSuccess?: () => void,
    onError?: (error: string, statusCode: number) => void
  ) => {
    incrementLoading?.();

    try {
      const res = await fetch(`/api/parent?action=manage-students&parentId=${parentId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(studentIds),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Managing parent students failed";
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

  return { manageStudents };
}