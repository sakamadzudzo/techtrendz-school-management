import MyTable, { Column } from "@/components/MyTable";
import useToastError from "@/hooks/toasts/ToastError";
import { useTeachersAll, useTeachersAllByFilter } from "@/hooks/useTeachers";
import { statuses } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";
import { TeacherView } from "@/utils/types";
import { SortDescriptor } from "@heroui/react";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

export default function AllTeachersPage() {
  const [page, setPage] = React.useState(1);
  const [pages, setPages] = React.useState(1);
  const [total, setTotal] = React.useState(1);
  const [size, setSize] = React.useState(25);
  const [offset, setOffset] = React.useState(0);
  const [first, setFirst] = React.useState(true);
  const [last, setLast] = React.useState(false);
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "person.lastName",
    direction: "ascending",
  });
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [searchValue, setSearchValue] = React.useState<string>("");
  const router = useRouter();

  let teachers;
  let isLoading;
  let isError;

  if (searchValue) {
    ({ teachers, isLoading, isError } = useTeachersAllByFilter({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
      filter: searchValue,
    }));
  } else {
    ({ teachers, isLoading, isError } = useTeachersAll({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
    }));
  }

  const viewOne = (id: string) => {
    router.push(`/teacher/one?id=${id}`);
  };

  const editOne = (id: string) => {
    router.push(`/teacher/edit?id=${id}`);
  };

  useEffect(() => {
    if (!filterValue) {
      setSearchValue("");
    }
  }, [filterValue]);

  useEffect(() => {
    if (teachers) {
      setTotal(teachers.totalElements);
      setPages(teachers.totalPages);
      setSize(teachers.size);
      setOffset(teachers.pageable.offset);
      setFirst(teachers.first);
      setLast(teachers.last);
    }
  }, [teachers]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const rows = React.useMemo(() => {
    if (!teachers) return [];
    return (teachers.content ?? []).map((row: TeacherView | any) => ({
      ...row,
      key: row.id ?? row._id ?? row.name,
      "person.firstName": row.person?.firstName ?? row.firstName,
      "person.otherNames": row.person?.otherNames ?? row.otherNames,
      "person.lastName": row.person?.lastName ?? row.lastName,
      classes: row.classrooms?.length ?? 0,
      subjects: row.subjects?.length ?? 0,
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
  }, [teachers]);

  useEffect(() => {
    if (sortDescriptor) {
      setPage(1);
    }
  }, [sortDescriptor]);

  const columns = React.useMemo<Column[]>(
    () => [
      {
        key: "person.firstName",
        label: "First Name",
        allowsSorting: true,
      },
      {
        key: "person.otherNames",
        label: "Other Names",
        allowsSorting: true,
      },
      {
        key: "person.lastName",
        label: "Last Name",
        allowsSorting: true,
      },
      {
        key: "classes",
        label: "Classes",
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

  const addNew = () => {
    router.push("/teacher/edit");
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
      addFunction={addNew}
    />
  );
}
