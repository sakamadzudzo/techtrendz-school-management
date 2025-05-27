import MyTable, { Column } from "@/components/MyTable";
import { useClassroomsAll } from "@/hooks/useClassrooms";
import { SortDescriptor } from "@heroui/react";
import React, { useEffect } from "react";

export default function AllClassroomsPage() {
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
  const [rows, setRows] = React.useState<any[]>([]);
  
  const { classrooms, isLoading, isError } = useClassroomsAll({
    page: page - 1,
    size,
    sort: sortDescriptor.column.toString(),
    direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
    filter: searchValue,
  });

  useEffect(() => {
    if (classrooms) {
      setTotal(classrooms.totalElements);
      setPages(classrooms.totalPages);
      setSize(classrooms.size);
      setOffset(classrooms.pageable.offset);
      setFirst(classrooms.first);
      setLast(classrooms.last);

      setRows(
        (classrooms.content || []).map((row: any) => ({
          ...row,
          key: row.id || row._id || row.name,
        }))
      );
    }
  }, [classrooms]);

  useEffect(() => {
    if (sortDescriptor) {
      setPage(1);
    }
  }, [sortDescriptor]);

  const columns: Column[] = [
    {
      key: "name",
      label: "Name",
      allowsSorting: true,
    },
    {
      key: "description",
      label: "Description",
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
  ];

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

  const statusOptions = [
    "ACTIVE",
    "INACTIVE",
    "SUSPENDED",
    "DELETED",
    "PENDING",
    "APPROVED",
    "REJECTED",
    "BLOCKED",
    "UNBLOCKED",
    "ENABLED",
    "DISABLED",
  ].sort();

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
    />
  );
}
