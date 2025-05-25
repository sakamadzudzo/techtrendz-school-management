import type {
  NumberInputVariantProps,
  Selection,
  SortDescriptor,
} from "@heroui/react";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Spinner,
  Pagination,
  Button,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownTrigger,
  Input,
} from "@heroui/react";
import React from "react";
import MyChip from "./MyChip";
import {
  SearchIcon,
  ChevronDownIcon,
  statusOptions,
  capitalize,
  PlusIcon,
  users,
} from "@/pages/subject/all";

type SelectionColor =
  | "primary"
  | "secondary"
  | "success"
  | "warning"
  | "danger";

type SelectionMode = "single" | "multiple";

type SelectionBehavior = "replace" | "toggle";

type Row = (typeof rows)[0];

type MyTableProps = {
  isHeaderSticky?: boolean;
  isLoading?: boolean;
  selectionColor?: SelectionColor;
  selectionMode?: SelectionMode;
  selectionBehavior?: SelectionBehavior;
  selectedKeys?: Selection;
  setSelectedKeys?: React.Dispatch<React.SetStateAction<Selection>>;
  page?: number;
  pages?: number;
  total?: number;
  size?: number;
  offset?: number;
  first?: boolean;
  last?: boolean;
  setPage?: React.Dispatch<React.SetStateAction<number>>;
  nextPage?: React.Dispatch<React.SetStateAction<number>>;
  prevPage?: React.Dispatch<React.SetStateAction<number>>;
  setSize?: React.Dispatch<React.SetStateAction<number>>;
  statusOptions?: string[];
};

const rows = [
  {
    key: "1",
    name: "Tony Reichert",
    role: "CEO",
    status: "ACTIVE",
  },
  {
    key: "2",
    name: "Zoey Lang",
    role: "Technical Lead",
    status: "BLOCKED",
  },
  {
    key: "3",
    name: "Jane Fisher",
    role: "Senior Developer",
    status: "ACTIVE",
  },
  {
    key: "4",
    name: "William Howard",
    role: "Community Manager",
    status: "DISABLED",
  },
  {
    key: "5",
    name: "Tony Reichert",
    role: "CEO",
    status: "ACTIVE",
  },
  {
    key: "6",
    name: "Zoey Lang",
    role: "Technical Lead",
    status: "BLOCKED",
  },
  {
    key: "7",
    name: "Jane Fisher",
    role: "Senior Developer",
    status: "ACTIVE",
  },
  //   {
  //     key: "8",
  //     name: "William Howard",
  //     role: "Community Manager",
  //     status: "DISABLED",
  //   },
  //   {
  //     key: "9",
  //     name: "Tony Reichert",
  //     role: "CEO",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "10",
  //     name: "Zoey Lang",
  //     role: "Technical Lead",
  //     status: "BLOCKED",
  //   },
  //   {
  //     key: "11",
  //     name: "Jane Fisher",
  //     role: "Senior Developer",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "12",
  //     name: "William Howard",
  //     role: "Community Manager",
  //     status: "DISABLED",
  //   },
  //   {
  //     key: "13",
  //     name: "Tony Reichert",
  //     role: "CEO",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "14",
  //     name: "Zoey Lang",
  //     role: "Technical Lead",
  //     status: "BLOCKED",
  //   },
  //   {
  //     key: "15",
  //     name: "Jane Fisher",
  //     role: "Senior Developer",
  //     status: "ACTIVE",
  //   },
  //   {
  //     key: "16",
  //     name: "William Howard",
  //     role: "Community Manager",
  //     status: "DISABLED",
  //   },
];

const columns = [
  {
    key: "name",
    label: "NAME",
    allowsSorting: true,
  },
  {
    key: "role",
    label: "ROLE",
  },
  {
    key: "status",
    label: "STATUS",
    allowsSorting: true,
  },
];

export default function MyTable({
  selectionColor = "primary",
  selectionMode = "single",
  selectionBehavior = "replace",
  selectedKeys,
  setSelectedKeys,
  isHeaderSticky = true,
  isLoading = false,
  page = 1,
  pages = 1,
  total = 1,
  size = 25,
  offset = 0,
  first = true,
  last = false,
  setPage,
  nextPage,
  prevPage,
  setSize,
  statusOptions
}: MyTableProps) {
  const isStripped = true;
  const [internalSelectedKeys, setInternalSelectedKeys] =
    React.useState<Selection>(new Set([]));
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: columns[0].key,
    direction: "ascending",
  });
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [statusFilter, setStatusFilter] = React.useState<Selection>("all");
  const [visibleColumns, setVisibleColumns] = React.useState<Selection>(
    new Set(columns.map((column) => column.key))
  );

  const actualSelectedKeys = selectedKeys ?? internalSelectedKeys;
  const actualSetSelectedKeys = setSelectedKeys ?? setInternalSelectedKeys;
  const hasSearchFilter = Boolean(filterValue);

  const renderCell = React.useCallback((row: Row, columnKey: React.Key) => {
    const cellValue = row[columnKey as keyof Row];

    switch (columnKey) {
      case "status":
        return (
          <MyChip
            className="capitalize"
            data={cellValue}
            size="sm"
            variant="flat"
          />
        );
      default:
        return cellValue;
    }
  }, []);

  const sortedItems = React.useMemo(() => {
    return [...rows].sort((a: Row, b: Row) => {
      const first = a[sortDescriptor.column as keyof Row];
      const second = b[sortDescriptor.column as keyof Row];

      let cmp = 0;
      if (typeof first === "number" && typeof second === "number") {
        cmp = first - second;
      } else {
        cmp = String(first).localeCompare(String(second));
      }

      return sortDescriptor.direction === "descending" ? -cmp : cmp;
    });
  }, [sortDescriptor, rows]);

  const bottomContent =
    pages > 0 ? (
      <div className="flex w-full justify-center">
        <Pagination
          isCompact
          showControls
          showShadow
          color="primary"
          page={page}
          total={pages}
          onChange={(page) => setPage!(page)}
        />
      </div>
    ) : null;

  const onClear = React.useCallback(() => {
    setFilterValue("");
    setPage!(1);
  }, []);

  const onSearchChange = React.useCallback((value?: string) => {
    if (value) {
      setFilterValue(value);
      setPage!(1);
    } else {
      setFilterValue("");
    }
  }, []);

  const onRowsPerPageChange = React.useCallback(
    (e: React.ChangeEvent<HTMLSelectElement>) => {
      setSize!(Number(e.target.value));
      setPage!(1);
    },
    []
  );

  const topContent = React.useMemo(() => {
    return (
      <div className="flex flex-col gap-4">
        <div className="flex justify-between gap-3 items-end">
          <Input
            isClearable
            className="w-full sm:max-w-[44%]"
            placeholder="Search by name..."
            startContent={<SearchIcon />}
            value={filterValue}
            onClear={() => onClear()}
            onValueChange={onSearchChange}
          />
          <div className="flex gap-3">
            <Dropdown>
              <DropdownTrigger className="hidden sm:flex">
                <Button
                  endContent={<ChevronDownIcon className="text-small" />}
                  variant="flat"
                >
                  Status
                </Button>
              </DropdownTrigger>
              <DropdownMenu
                disallowEmptySelection
                aria-label="Table Columns"
                closeOnSelect={false}
                selectedKeys={statusFilter}
                selectionMode="multiple"
                onSelectionChange={setStatusFilter}
              >
                {statusOptions! && statusOptions.map((status) => (
                  <DropdownItem key={status} className="capitalize">
                    {capitalize(status)}
                  </DropdownItem>
                ))}
              </DropdownMenu>
            </Dropdown>
            <Dropdown>
              <DropdownTrigger className="hidden sm:flex">
                <Button
                  endContent={<ChevronDownIcon className="text-small" />}
                  variant="flat"
                >
                  Columns
                </Button>
              </DropdownTrigger>
              <DropdownMenu
                disallowEmptySelection
                aria-label="Table Columns"
                closeOnSelect={false}
                selectedKeys={visibleColumns}
                selectionMode="multiple"
                onSelectionChange={setVisibleColumns}
              >
                {columns.map((column) => (
                  <DropdownItem key={column.key} className="capitalize">
                    {capitalize(column.label)}
                  </DropdownItem>
                ))}
              </DropdownMenu>
            </Dropdown>
            <Button color="primary" endContent={<PlusIcon />}>
              Add New
            </Button>
          </div>
        </div>
        <div className="flex justify-between items-center">
          <span className="text-default-400 text-small">
            Total {users.length} users
          </span>
          <label className="flex items-center text-default-400 text-small">
            Rows per page:
            <select
              className="bg-transparent outline-none text-default-400 text-small"
              onChange={onRowsPerPageChange}
            >
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="15">15</option>
            </select>
          </label>
        </div>
      </div>
    );
  }, [
    filterValue,
    statusFilter,
    visibleColumns,
    onSearchChange,
    onRowsPerPageChange,
    users.length,
    hasSearchFilter,
  ]);

  const headerColumns = React.useMemo(() => {
    if (visibleColumns === "all") return columns;

    return columns.filter((column) =>
      Array.from(visibleColumns).includes(column.key)
    );
  }, [visibleColumns]);

  const filteredItems = React.useMemo(() => {
    let filteredUsers = [...sortedItems];

    if (hasSearchFilter) {
      filteredUsers = filteredUsers.filter((row) => {
        const matchingKeys = Object.keys(row).filter((key) => {
          const value = row[key as keyof Row];
          if (typeof value === "string") {
            return value.toLowerCase().includes(filterValue.toLowerCase());
          }
          return value === filterValue;
        });
        return matchingKeys.length > 0;
      });
    }

    if (
      statusFilter !== "all" &&
      Array.from(statusFilter).length !== statusOptions!.length
    ) {
      filteredUsers = filteredUsers.filter((row) => {
        if (!Object.prototype.hasOwnProperty.call(row, "status")) {
          return true;
        }
        return Array.from(statusFilter).includes(row.status);
      });
    }

    return filteredUsers;
  }, [sortedItems, filterValue, hasSearchFilter, statusFilter]);

  return (
    <Table
      isHeaderSticky={isHeaderSticky}
      aria-label="Example table with dynamic content"
      isStriped={isStripped}
      color={selectionColor}
      selectionMode={selectionMode}
      selectionBehavior={selectionBehavior}
      selectedKeys={actualSelectedKeys}
      onSelectionChange={actualSetSelectedKeys}
      sortDescriptor={sortDescriptor}
      onSortChange={setSortDescriptor}
      bottomContent={bottomContent}
      topContent={topContent}
    >
      <TableHeader columns={headerColumns}>
        {(column) => (
          <TableColumn key={column.key} allowsSorting={column.allowsSorting}>
            {column.label}
          </TableColumn>
        )}
      </TableHeader>
      <TableBody
        isLoading={isLoading}
        loadingContent={
          <Spinner label="Loading..." variant="dots" color="primary" />
        }
        items={filteredItems}
        emptyContent={"No rows to display."}
      >
        {(item) => (
          <TableRow key={item.key}>
            {(columnKey) => (
              <TableCell>{renderCell(item, columnKey)}</TableCell>
            )}
          </TableRow>
        )}
      </TableBody>
    </Table>
  );
}
