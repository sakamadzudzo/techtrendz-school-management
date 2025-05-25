import React from "react";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Pagination,
  Input,
} from "@heroui/react";
import { IconMagnifyingGlassThin as SearchIcon } from "./icons/magnifying-glass-thin";
import { IconPlusThin as PlusIcon } from "./icons/plus-thin"

type Column = {
  name: string;
  uid: string;
  sortable?: boolean;
  align?: "start" | "center" | "end";
};

type SortDescriptor = {
  column: string;
  direction: "ascending" | "descending";
};

type MyTableProps = {
  columns: Column[];
  items: any[];
  color?: "primary" | "secondary" | "success" | "warning" | "danger";
  size?: "sm" | "md" | "lg";
  selectableRows?: boolean;
  rowSizeOptions?: number[];
  defaultRowsPerPage?: number;
  renderCell?: (item: any, columnKey: string) => React.ReactNode;
  onSearch?: (value: string) => void;
  searchPlaceholder?: string;
  pagination?: {
    page: number;
    total: number;
    onChange: (page: number) => void;
  };
  addButtonLabel?: string;
  onAdd?: () => void;
  actions?: string[];
  onAction?: (action: string, item: any) => void;
};

export default function MyTable2({
  columns,
  items,
  color = "primary",
  size = "md",
  selectableRows = false,
  rowSizeOptions = [5, 10, 15],
  defaultRowsPerPage = 5,
  renderCell,
  onSearch,
  searchPlaceholder = "Search...",
  pagination,
  addButtonLabel = "Add New",
  onAdd,
  actions = [],
  onAction,
}: MyTableProps) {
  const [filterValue, setFilterValue] = React.useState("");
  const [selectedKeys, setSelectedKeys] = React.useState(new Set([]));
  const [rowsPerPage, setRowsPerPage] = React.useState(defaultRowsPerPage);
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: columns.find((col) => col.sortable)?.uid || columns[0].uid,
    direction: "ascending",
  });

  // Pagination logic (if not controlled)
  const pages = pagination ? pagination.total : Math.ceil(items.length / rowsPerPage) || 1;
  const page = pagination ? pagination.page : 1;

  // Sorting logic
  const sortedItems = React.useMemo(() => {
    if (!sortDescriptor.column) return items;
    const sorted = [...items].sort((a, b) => {
      const aValue = a[sortDescriptor.column];
      const bValue = b[sortDescriptor.column];
      if (aValue == null) return 1;
      if (bValue == null) return -1;
      if (typeof aValue === "number" && typeof bValue === "number") {
        return sortDescriptor.direction === "ascending"
          ? aValue - bValue
          : bValue - aValue;
      }
      return sortDescriptor.direction === "ascending"
        ? String(aValue).localeCompare(String(bValue))
        : String(bValue).localeCompare(String(aValue));
    });
    return sorted;
  }, [items, sortDescriptor]);

  // Pagination after sorting
  const pagedItems = pagination
    ? sortedItems
    : React.useMemo(() => {
        const start = (page - 1) * rowsPerPage;
        return sortedItems.slice(start, start + rowsPerPage);
      }, [sortedItems, page, rowsPerPage]);

  // Search logic
  const filteredItems = React.useMemo(() => {
    if (!filterValue) return pagedItems;
    return pagedItems.filter((item) =>
      Object.values(item)
        .join(" ")
        .toLowerCase()
        .includes(filterValue.toLowerCase())
    );
  }, [pagedItems, filterValue]);

  // Top content (search, add button, and rows per page)
  const topContent = (
    <div className="flex flex-col gap-4 mb-2">
      <div className="flex gap-3 items-end justify-between">
        <div className="flex gap-3 items-end flex-1">
          {onSearch && (
            <Input
              isClearable
              className="w-full sm:max-w-[44%]"
              placeholder={searchPlaceholder}
              startContent={<SearchIcon className="h-6 w-6 bg-inherit" />}
              value={filterValue}
              onClear={() => {
                setFilterValue("");
                onSearch("");
              }}
              onValueChange={(val) => {
                setFilterValue(val);
                onSearch(val);
              }}
            />
          )}
        </div>
        {onAdd && (
          <button
            onClick={onAdd}
            className="inline-flex items-center px-4 py-2 bg-primary text-foreground rounded-xl hover:bg-primary-300"
          >
            {addButtonLabel}
          <PlusIcon className="h-5 w-5 ml-2 bg-inherit" />
          </button>
        )}
      </div>
      <div className="flex justify-between items-center">
        <span className="text-default-400 text-small">
          Total {items.length} items
        </span>
        <label className="flex items-center text-default-400 text-small">
          Rows per page:
          <select
            className="bg-background outline-none text-default-400 text-small ml-2"
            value={rowsPerPage}
            onChange={(e) => setRowsPerPage(Number(e.target.value))}
          >
            {rowSizeOptions.map((opt) => (
              <option key={opt} value={opt}>
                {opt}
              </option>
            ))}
          </select>
        </label>
      </div>
    </div>
  );

  // Bottom content (pagination)
  const bottomContent = (
    <div className="py-2 px-2 flex justify-between items-center">
      {selectableRows && (
        <span className="w-[30%] text-small text-default-400">
          {selectedKeys === "all"
            ? "All items selected"
            : `${selectedKeys.size} selected`}
        </span>
      )}
      <Pagination
        isCompact
        showControls
        showShadow
        color={color}
        page={page}
        total={pages}
        onChange={pagination ? pagination.onChange : (p) => setPage(p)}
      />
    </div>
  );

  // Dropdown state for actions
  const [openDropdown, setOpenDropdown] = React.useState<string | null>(null);

  return (
    <Table
      isHeaderSticky
      aria-label="Custom table"
      bottomContent={bottomContent}
      bottomContentPlacement="outside"
      classNames={{
        wrapper: "max-h-[65%]",
      }}
      selectedKeys={selectedKeys}
      selectionMode={selectableRows ? "multiple" : "none"}
      topContent={topContent}
      topContentPlacement="outside"
      onSelectionChange={setSelectedKeys}
      size={size}
      sortDescriptor={sortDescriptor}
      onSortChange={setSortDescriptor}
    >
      <TableHeader columns={columns}>
        {(column) => (
          <TableColumn
            key={column.uid}
            align={column.align || (column.uid === "actions" ? "center" : "start")}
            allowsSorting={column.sortable}
          >
            {column.name}
          </TableColumn>
        )}
      </TableHeader>
      <TableBody emptyContent={"No items found"} items={filteredItems}>
        {(item) => (
          <TableRow key={item.id}>
            {(columnKey) =>
              columnKey === "actions" ? (
                <TableCell>
                  <div className="relative flex justify-center">
                    <button
                      className="px-2 py-1 rounded hover:bg-default-100"
                      onClick={() =>
                        setOpenDropdown(openDropdown === item.id ? null : item.id)
                      }
                      aria-haspopup="true"
                      aria-expanded={openDropdown === item.id}
                    >
                      <span style={{ fontSize: "2rem", lineHeight: "1" }}>⋮</span>
                    </button>
                    {openDropdown === item.id && (
                      <div
                        className="absolute z-10 right-0 mt-2 w-32 bg-background border border-default-200 rounded shadow"
                        tabIndex={-1}
                        onBlur={() => setOpenDropdown(null)}
                      >
                        {actions.map((action) => (
                          <div
                            key={action}
                            className="px-4 py-2 cursor-pointer hover:bg-default-100"
                            onClick={() => {
                              setOpenDropdown(null);
                              onAction?.(action, item);
                            }}
                          >
                            {action}
                          </div>
                        ))}
                      </div>
                    )}
                  </div>
                </TableCell>
              ) : renderCell
                ? <TableCell>{renderCell(item, columnKey)}</TableCell>
                : <TableCell>{item[columnKey]}</TableCell>
            }
          </TableRow>
        )}
      </TableBody>
    </Table>
  );
}