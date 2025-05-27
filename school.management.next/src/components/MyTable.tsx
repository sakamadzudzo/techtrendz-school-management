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

export type Column = {
  key: string;
  label: string;
  allowsSorting?: boolean;
};

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
  columns: Column[];
  rows?: any[];
  sortDescriptor?: SortDescriptor;
  setSortDescriptor?: React.Dispatch<React.SetStateAction<SortDescriptor>>;
  fetchButtonLabel?: string;
  fetchButton?: boolean;
  search?: () => void;
  filterValue?: string;
  setFilterValue?: React.Dispatch<React.SetStateAction<string>>;
  searchBox?: boolean;
};

export default function MyTable(props: MyTableProps) {
  const {
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
    statusOptions,
    columns,
    rows,
    sortDescriptor,
    setSortDescriptor,
    fetchButtonLabel = "Fetch",
    fetchButton = true,
    search,
    filterValue,
    setFilterValue,
    searchBox = true,
  } = props;

  const isStripped = true;
  const [internalSelectedKeys, setInternalSelectedKeys] =
    React.useState<Selection>(new Set([]));
  const [statusFilter, setStatusFilter] = React.useState<Selection>("all");
  const [visibleColumns, setVisibleColumns] = React.useState<Selection>(new Set());
  const [headerColumns, setHeaderColumns] = React.useState<Column[]>(props.columns);

  // Update visibleColumns and headerColumns when columns change
  React.useEffect(() => {
    // If columns is not empty, set visibleColumns to all column keys
    if (props.columns && props.columns.length > 0) {
      setVisibleColumns(new Set(props.columns.map((col) => col.key)));
      setHeaderColumns(props.columns);
    }
  }, [props.columns]);

  // Update headerColumns when visibleColumns changes
  React.useEffect(() => {
    if (visibleColumns === "all") {
      setHeaderColumns(props.columns);
    } else {
      setHeaderColumns(
        props.columns.filter((column) =>
          Array.from(visibleColumns).includes(column.key)
        )
      );
    }
  }, [visibleColumns, props.columns]);

  const actualSelectedKeys = selectedKeys ?? internalSelectedKeys;
  const actualSetSelectedKeys = setSelectedKeys ?? setInternalSelectedKeys;
  const hasSearchFilter = Boolean(filterValue);

  const renderCell = React.useCallback((row: any, columnKey: React.Key) => {
    const cellValue = row[columnKey as keyof any];

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
    setFilterValue!("");
    setPage!(1);
  }, []);

  const onSearchChange = React.useCallback((value?: string) => {
    if (value) {
      setFilterValue!(value);
      setPage!(1);
    } else {
      setFilterValue!("");
    }
  }, []);

  const onRowsPerPageChange = React.useCallback(
    (e: React.ChangeEvent<HTMLSelectElement>) => {
      setSize!(Number(e.target.value));
      setPage!(1);
    },
    []
  );

  const filteredItems = React.useMemo(() => {
    let filteredUsers = [...rows!];

    if (hasSearchFilter) {
      filteredUsers = filteredUsers.filter((row) => {
        const matchingKeys = Object.keys(row).filter((key) => {
          const value = row[key as keyof any];
          if (typeof value === "string") {
            return value.toLowerCase().includes(filterValue!.toLowerCase());
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
  }, [rows, filterValue, hasSearchFilter, statusFilter]);

  const topContent = React.useMemo(() => {
    return (
      <div className="flex flex-col gap-4">
        <div className="flex justify-between gap-3 items-end">
          {searchBox && (
            <div className="flex gap-1 w-full sm:max-w-[44%]">
              <Input
                isClearable
                className=""
                placeholder="Search by name..."
                startContent={<SearchIcon />}
                value={filterValue}
                onClear={() => onClear()}
                onValueChange={onSearchChange}
              />
              {fetchButton && (
                <Button onPress={search}>{fetchButtonLabel}</Button>
              )}
            </div>
          )}
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
                {statusOptions! &&
                  statusOptions.map((status) => (
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
            Total {filteredItems.length} rows
          </span>
          <label className="flex items-center text-default-400 text-small">
            Rows per page:
            <select
              className="bg-transparent outline-none text-default-400 text-small"
              onChange={onRowsPerPageChange}
              value={size}
            >
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="25">25</option>
              <option value="50">50</option>
              <option value="100">100</option>
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
      bottomContentPlacement="outside"
      topContent={topContent}
      topContentPlacement="outside"
      className="h-full w-full flex flex-col mb-4 overflow-hidden"
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
        className="grow overflow-auto"
      >
        {(item) => (
          <TableRow key={item.key}>
            {headerColumns.map((column) => (
              <TableCell key={column.key}>{renderCell(item, column.key)}</TableCell>
            ))}
          </TableRow>
        )}
      </TableBody>
    </Table>
  );
}
