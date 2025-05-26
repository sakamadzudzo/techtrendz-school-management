import MyTable, { Column } from "@/components/MyTable";
import { useClassroomsAll } from "@/hooks/useClassrooms";
import { capitalize } from "@/utils/helpers";
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
  const [rows, setRows] = React.useState<any[]>([]);
  // const [columns, setColumns] = React.useState<Column[]>([]);
  // fetch with pagination parameters if needed
  // watch page, size, sortDescriptor and auto-fetch classrooms
  const { classrooms, isLoading, isError } = useClassroomsAll();

  useEffect(() => {
    if (classrooms) {
      setPage(classrooms.number + 1);
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
      // setColumns(
      //   classrooms.content[0]
      //     ? Object.keys(classrooms.content[0]).map((key) => ({
      //         key,
      //         label: capitalize(key),
      //         allowsSorting: true,
      //       }))
      //     : []
      // );
    }
  }, [classrooms]);

  const columns: Column[] = [
    {
      key: "name",
      label: "Name",
      allowsSorting: true,
    },
    {
      key: "description",
      label: "Description",
      allowsSorting: true
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
  ]

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

  // const handleSearch = (value: string) => {
  //   if (!value) {
  //     setSearchResults(users);
  //     return;
  //   }
  //   setSearchResults(
  //     users.filter(
  //       (user) =>
  //         user.name.toLowerCase().includes(value.toLowerCase()) ||
  //         user.email.toLowerCase().includes(value.toLowerCase()) ||
  //         user.role.toLowerCase().includes(value.toLowerCase()) ||
  //         user.status.toLowerCase().includes(value.toLowerCase())
  //     )
  //   );
  // };

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

  // const rows = [
  //   {
  //     key: "1",
  //     name: "Tony Reichert",
  //     role: "CEO",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "2",
  //     name: "Zoey Lang",
  //     role: "Technical Lead",
  //     status: "BLOCKED",
  //   },
  //   {
  //     key: "3",
  //     name: "Jane Fisher",
  //     role: "Senior Developer",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "4",
  //     name: "William Howard",
  //     role: "Community Manager",
  //     status: "DISABLED",
  //   },
  //   {
  //     key: "5",
  //     name: "Tony Reichert",
  //     role: "CEO",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "6",
  //     name: "Zoey Lang",
  //     role: "Technical Lead",
  //     status: "BLOCKED",
  //   },
  //   {
  //     key: "7",
  //     name: "Jane Fisher",
  //     role: "Senior Developer",
  //     status: "ACTIVE",
  //   },
  //   //   {
  //   //     key: "8",
  //   //     name: "William Howard",
  //   //     role: "Community Manager",
  //   //     status: "DISABLED",
  //   //   },
  //   //   {
  //   //     key: "9",
  //   //     name: "Tony Reichert",
  //   //     role: "CEO",
  //   //     status: "ACTIVE",
  //   //   },
  //   //   {
  //   //     key: "10",
  //   //     name: "Zoey Lang",
  //   //     role: "Technical Lead",
  //   //     status: "BLOCKED",
  //   //   },
  //   //   {
  //   //     key: "11",
  //   //     name: "Jane Fisher",
  //   //     role: "Senior Developer",
  //   //     status: "ACTIVE",
  //   //   },
  //   //   {
  //   //     key: "12",
  //   //     name: "William Howard",
  //   //     role: "Community Manager",
  //   //     status: "DISABLED",
  //   //   },
  //   //   {
  //   //     key: "13",
  //   //     name: "Tony Reichert",
  //   //     role: "CEO",
  //   //     status: "ACTIVE",
  //   //   },
  //   //   {
  //   //     key: "14",
  //   //     name: "Zoey Lang",
  //   //     role: "Technical Lead",
  //   //     status: "BLOCKED",
  //   //   },
  //   //   {
  //   //     key: "15",
  //   //     name: "Jane Fisher",
  //   //     role: "Senior Developer",
  //   //     status: "ACTIVE",
  //   //   },
  //   //   {
  //   //     key: "16",
  //   //     name: "William Howard",
  //   //     role: "Community Manager",
  //   //     status: "DISABLED",
  //   //   },
  // ];

  // const columns = [
  //   {
  //     key: "name",
  //     label: "NAME",
  //     allowsSorting: true,
  //   },
  //   {
  //     key: "role",
  //     label: "ROLE",
  //   },
  //   {
  //     key: "status",
  //     label: "STATUS",
  //     allowsSorting: true,
  //   },
  // ];

  const sortedItems = React.useMemo(() => {
    return [...rows!].sort((a: any, b: any) => {
      const first = a[sortDescriptor.column as keyof any];
      const second = b[sortDescriptor.column as keyof any];

      let cmp = 0;
      if (typeof first === "number" && typeof second === "number") {
        cmp = first - second;
      } else {
        cmp = String(first).localeCompare(String(second));
      }

      return sortDescriptor.direction === "descending" ? -cmp : cmp;
    });
  }, [sortDescriptor, rows]);

  const search = () => {
    alert("Clicked fetch");
  };

  return (
    <MyTable
      columns={columns}
      rows={sortedItems}
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
