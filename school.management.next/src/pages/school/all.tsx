import MyTable, { Column } from "@/components/MyTable";
import useToastError from "@/hooks/toasts/ToastError";
import { useSchoolsAll, useSchoolsAllByFilter } from "@/hooks/useSchools";
import { statuses } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";
import { SortDescriptor } from "@heroui/react";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

export default function AllSchoolsPage() {
  const [page, setPage] = React.useState(1);
  const [pages, setPages] = React.useState(1);
  const [total, setTotal] = React.useState(1);
  const [size, setSize] = React.useState(25);
  const [offset, setOffset] = React.useState(0);
  const [first, setFirst] = React.useState(true);
  const [last, setLast] = React.useState(false);
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "name",
    direction: "ascending",
  });
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [searchValue, setSearchValue] = React.useState<string>("");
  const router = useRouter();

  let schools;
  let isLoading;
  let isError;

  if (searchValue) {
    ({ schools, isLoading, isError } = useSchoolsAllByFilter({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
      filter: searchValue,
    }));
  } else {
    ({ schools, isLoading, isError } = useSchoolsAll({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
    }));
  }

  const viewOne = (id: string) => {
    router.push(`/school/one?id=${id}`);
  };

  const editOne = (id: string) => {
    router.push(`/school/edit?id=${id}`);
  };

  useEffect(() => {
    if (!filterValue) {
      setSearchValue("");
    }
  }, [filterValue]);

  useEffect(() => {
    if (schools) {
      setTotal(schools.totalElements);
      setPages(schools.totalPages);
      setSize(schools.size);
      setOffset(schools.pageable.offset);
      setFirst(schools.first);
      setLast(schools.last);
    }
  }, [schools]);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  const rows = React.useMemo(() => {
    if (!schools) return [];
    return (schools.content ?? []).map((row: any) => ({
      ...row,
      key: row.id ?? row._id ?? row.name,
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
  }, [schools]);

  useEffect(() => {
    if (sortDescriptor) {
      setPage(1);
    }
  }, [sortDescriptor]);

  const columns = React.useMemo<Column[]>(
    () => [
      {
        key: "name",
        label: "Name",
        allowsSorting: true,
      },
      {
        key: "id",
        label: "ID",
        allowsSorting: true,
      },
      {
        key: "address",
        label: "Address",
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
