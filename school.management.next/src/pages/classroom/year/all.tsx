import MyTable, { Column } from "@/components/MyTable";
import useToastError from "@/hooks/toasts/ToastError";
import {
  useClassroomYearsAll,
  useClassroomYearsAllByFilter,
} from "@/hooks/useClassroomYears";
import { statuses } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";
import { SortDescriptor } from "@heroui/react";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

export default function AllClassroomYearsPage() {
  const [page, setPage] = React.useState(1);
  const [pages, setPages] = React.useState(1);
  const [total, setTotal] = React.useState(1);
  const [size, setSize] = React.useState(25);
  const [offset, setOffset] = React.useState(0);
  const [first, setFirst] = React.useState(true);
  const [last, setLast] = React.useState(false);
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "calendarYear,classroom.name",
    direction: "ascending",
  });
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [searchValue, setSearchValue] = React.useState<string>("");
  const router = useRouter();

  let classroomYears;
  let isLoading;
  let isError;

  if (searchValue) {
    ({ classroomYears, isLoading, isError } = useClassroomYearsAllByFilter({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
      filter: searchValue,
    }));
  } else {
    ({ classroomYears, isLoading, isError } = useClassroomYearsAll({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
    }));
  }

  const viewOne = (id: string) => {
    router.push(`/classroom/year/one?id=${id}`);
  };

  const editOne = (id: string) => {
    router.push(`/classroom/year/edit?id=${id}`);
  };

  useEffect(() => {
    if (!filterValue) {
      setSearchValue("");
    }
  }, [filterValue]);

  useEffect(() => {
    if (classroomYears) {
      setTotal(classroomYears.totalElements);
      setPages(classroomYears.totalPages);
      setSize(classroomYears.size);
      setOffset(classroomYears.pageable.offset);
      setFirst(classroomYears.first);
      setLast(classroomYears.last);
    }
  }, [classroomYears]);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  const rows = React.useMemo(() => {
    if (!classroomYears) return [];
    return (classroomYears.content ?? []).map((row: any) => ({
      ...row,
      key: row.id ?? row._id ?? row.name,
      "classroom.name": row.classroom?.name ?? row.name,
      "classroom.description": row.classroom?.description ?? row.description,
      subjects: row.classroom?.subjects?.length ?? row.subjects?.length ?? 0,
      datestamp: formatDate(row.datestamp),
      actions: [
        {
          label: "View",
          function: () => viewOne(row.id),
          key: `view-${row.id}`,
        },
        {
          label: "Edit",
          function: () => editOne(row.id),
          key: `edit-${row.id}`,
        },
      ],
    }));
  }, [classroomYears]);

  useEffect(() => {
    if (sortDescriptor) {
      setPage(1);
    }
  }, [sortDescriptor]);

  const columns = React.useMemo<Column[]>(
    () => [
      {
        key: "classroom.name",
        label: "Name",
        allowsSorting: true,
      },
      {
        key: "classroom.description",
        label: "Description",
        allowsSorting: true,
      },
      {
        key: "calendarYear",
        label: "Calendar Year",
        allowsSorting: true,
      },
      {
        key: "subjects",
        label: "Subjects",
        allowsSorting: true,
      },
      {
        key: "id",
        label: "ID",
        allowsSorting: true,
      },
      {
        key: "datestamp",
        label: "Date Created/Modified",
        allowsSorting: true,
      },
      {
        key: "status",
        label: "Status",
        allowsSorting: true,
      },
      {
        key: "actions",
        label: "Actions",
        allowsSorting: false,
      },
    ],
    []
  );

  const nextPage = () => {
    if (last) return;
    setPage((prev) => prev + 1);
    alert("Clicked next page.\n" + `Page: ${page}`);
  };

  const prevPage = () => {
    if (first) return;
    setPage((prev) => prev - 1);
    alert("Clicked previous page.\n" + `Page: ${page}`);
  };

  const statusOptions = statuses;

  const search = () => {
    setSearchValue(filterValue);
    setPage(1);
  };

  return (
    <MyTable
      columns={columns}
      rows={rows}
      setPage={setPage}
      page={page}
      pages={pages}
      statusOptions={statusOptions}
      sortDescriptor={sortDescriptor}
      setSortDescriptor={setSortDescriptor}
      filterValue={filterValue}
      setFilterValue={setFilterValue}
      searchBox={true}
      search={search}
      isLoading={isLoading}
      total={total}
      size={size}
      setSize={setSize}
      offset={offset}
      first={first}
      last={last}
      nextPage={nextPage}
      prevPage={prevPage}
      onRowAction={viewOne}
    />
  );
}
