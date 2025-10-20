import useSWR from "swr";
import { useSelector } from "react-redux";
import { ApiError } from "../utils/classes";
import { RootState } from "../store/store";
import { ReportsFilters } from "@/utils/types";

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

export function useReportsDropdownsAll(
  incrementLoading?: () => void,
  decrementLoading?: () => void
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token;
  const { data, error, mutate } = useSWR(
    shouldFetch ? ["/api/reports-dropdowns?action=all", token] : null,
    async ([url, token]) => {
      incrementLoading && incrementLoading();
      try {
        return await fetcherWithAuth(url, token);
      } finally {
        decrementLoading && decrementLoading();
      }
    }
  );
  return {
    dropdowns: data as ReportsFilters,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}

export function useReportsDropdownsSelective(
  {
    students,
    teachers,
    courseworks,
    classrooms,
    subjects,
    extraCurriculars,
    calendarYears,
  }: {
    students?: boolean;
    teachers?: boolean;
    courseworks?: boolean;
    classrooms?: boolean;
    subjects?: boolean;
    extraCurriculars?: boolean;
    calendarYears?: boolean;
  },
  incrementLoading?: () => void,
  decrementLoading?: () => void
) {
  const token = useSelector((state: RootState) => state.auth.token);
  const shouldFetch = !!token && (students || teachers || courseworks || classrooms || subjects || extraCurriculars || calendarYears);

  const params = new URLSearchParams();
  if (students !== undefined) params.append("students", String(students));
  if (teachers !== undefined) params.append("teachers", String(teachers));
  if (courseworks !== undefined)
    params.append("courseworks", String(courseworks));
  if (classrooms !== undefined) params.append("classrooms", String(classrooms));
  if (subjects !== undefined) params.append("subjects", String(subjects));
  if (extraCurriculars !== undefined)
    params.append("extraCurriculars", String(extraCurriculars));
  if (calendarYears !== undefined)
    params.append("calendarYears", String(calendarYears));

  const { data, error, mutate } = useSWR(
    shouldFetch
      ? [`/api/reports-dropdowns?action=selective&${params.toString()}`, token]
      : null,
    async ([url, token]) => {
      incrementLoading && incrementLoading();
      try {
        return await fetcherWithAuth(url, token);
      } finally {
        decrementLoading && decrementLoading();
      }
    }
  );
  return {
    dropdowns: data as ReportsFilters,
    isLoading: shouldFetch && !error && !data,
    isError: error,
    mutate,
  };
}
