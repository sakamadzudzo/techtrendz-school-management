import MyTable, { Column } from "@/components/MyTable";
import {
  useExtraCurricularYearsAll,
  useExtraCurricularYearsAllByFilter,
} from "@/hooks/useExtraCurricularYears";
import { statuses } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";
import { SortDescriptor } from "@heroui/react";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

export default function AllExtraCurricularYearsPage() {
  const [page, setPage] = React.useState(1);
  const [pages, setPages] = React.useState(1);
  const [total, setTotal] = React.useState(1);
  const [size, setSize] = React.useState(25);
  const [offset, setOffset] = React.useState(0);
  const [first, setFirst] = React.useState(true);
  const [last, setLast] = React.useState(false);
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "calendarYear,extraCurricular.name",
    direction: "ascending",
  });
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [searchValue, setSearchValue] = React.useState<string>("");
  const router = useRouter();

  let extraCurriculars;
  let isLoading;
  let isError;

  if (searchValue) {
    ({
      data: extraCurriculars,
      isLoading,
      isError,
    } = useExtraCurricularYearsAllByFilter({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
      filter: searchValue,
    }));
  } else {
    ({
      data: extraCurriculars,
      isLoading,
      isError,
    } = useExtraCurricularYearsAll({
      page: page - 1,
      size,
      sort: sortDescriptor.column.toString(),
      direction: sortDescriptor.direction === "ascending" ? "ASC" : "DESC",
    }));
  }

  const viewOne = (id: string) => {
    router.push(`/extra-curricular/year/one/${id}`);
  };

  const editOne = (id: string) => {
    router.push(`/extra-curricular/year/edit/${id}`);
  };

  useEffect(() => {
    if (!filterValue) {
      setSearchValue("");
    }
  }, [filterValue]);

  useEffect(() => {
    if (extraCurriculars) {
      setTotal(extraCurriculars.totalElements);
      setPages(extraCurriculars.totalPages);
      setSize(extraCurriculars.size);
      setOffset(extraCurriculars.pageable.offset);
      setFirst(extraCurriculars.first);
      setLast(extraCurriculars.last);
    }
  }, [extraCurriculars]);

  const rows = React.useMemo(() => {
    if (!extraCurriculars) return [];
    return (extraCurriculars.content || []).map((row: any) => ({
      ...row,
      key: row.id || row._id || row.name,
      "extraCurricular.name": row.extraCurricular?.name || row.name,
      "extraCurricular.description": row.extraCurricular?.description || row.description,
      facilitators: row.facilitators?.length || 0,
      students: row.students?.length || 0,
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
  }, [extraCurriculars]);

  useEffect(() => {
    if (sortDescriptor) {
      setPage(1);
    }
  }, [sortDescriptor]);

  const columns = React.useMemo<Column[]>(
    () => [
      {
        key: "extraCurricular.name",
        label: "Name",
        allowsSorting: true,
      },
      {
        key: "extraCurricular.description",
        label: "Description",
        allowsSorting: true,
      },
      {
        key: "calendarYear",
        label: "Calendar Year",
        allowsSorting: true,
      },
      {
        key: "facilitators",
        label: "Facilitators",
        allowsSorting: true,
      },
      {
        key: "students",
        label: "Students",
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
