import { Coursework, Mark } from "@/utils/types";
import {
  Modal,
  ModalBody,
  ModalContent,
  ModalHeader,
  SortDescriptor,
} from "@heroui/react";
import MyTable, { Column } from "./MyTable";
import React from "react";
import { courseworkTypes } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";

type ViewCourseworksProps = {
  courseworks?: Coursework[];
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  subjectId: number;
  subjectName: string;
  incrementLoading: () => void;
  decrementLoading: () => void;
  addCourseworksOpen: boolean;
  setAddCourseworksOpen: (open: boolean) => void;
  editCoursework?: Coursework;
  setEditCoursework?: (coursework: Coursework) => void;
};

export const ViewCourseworks = ({
  courseworks,
  isOpen,
  onOpenChange,
  subjectName,
  setAddCourseworksOpen,
  setEditCoursework,
}: ViewCourseworksProps) => {
  const page = 0,
    pages = 1,
    total = courseworks?.length ?? 0,
    size = courseworks?.length ?? 0,
    offset = 0,
    first = true,
    last = true,
    isLoading = false;
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "title",
    direction: "ascending",
  });
  const [viewMarks, setViewMarks] = React.useState(false);
  const [marks, setMarks] = React.useState<Mark[]>([] as Mark[]);

  const columns = React.useMemo<Column[]>(() => {
    return [
      {
        key: "title",
        label: "Title",
        allowsSorting: true,
      },
      {
        key: "type",
        label: "Type",
        allowsSorting: true,
      },
      {
        key: "total",
        label: "Total",
        allowsSorting: true,
      },
      {
        key: "passMark",
        label: "Pass Mark",
        allowsSorting: true,
      },
      {
        key: "actions",
        label: "Actions",
        allowsSorting: false,
      },
    ];
  }, []);

  const rows = React.useMemo(() => {
    return courseworks
      ?.filter(
        (work) =>
          work.title.toLowerCase().includes(filterValue.toLowerCase()) ||
          work.type.toLowerCase().includes(filterValue.toLowerCase())
      )
      .sort((a: Coursework, b: Coursework) => {
        const col: keyof Coursework = sortDescriptor.column as keyof Coursework;
        if (a[col]! < b[col]!)
          return sortDescriptor.direction === "ascending" ? -1 : 1;
        if (a[col]! > b[col]!)
          return sortDescriptor.direction === "ascending" ? 1 : -1;
        return 0;
      })
      .map((row: Coursework) => ({
        ...row,
        key: row.id ?? row.title,
        datestamp: formatDate(row.datestamp),
        actions: [
          {
            label: "View Marks",
            function: () => {
              setMarks(row.marks ?? []);
              setViewMarks(true);
            },
          },
          {
            label: "Edit",
            function: () => {
              setEditCoursework!(row);
              setAddCourseworksOpen(true);
            },
            key: `edit-${row.id}`,
          },
        ],
      }));
  }, [courseworks, filterValue, sortDescriptor]);

  return (
    <Modal
      isOpen={isOpen}
      onOpenChange={onOpenChange}
      hideCloseButton={false}
      backdrop="blur"
      radius="md"
      shadow="md"
      size="5xl"
      classNames={{
        wrapper: "py-2 overflow-hidden",
      }}
      className="text-foreground"
    >
      <ModalContent className="overflow-y-auto max-h-full">
        <ModalHeader className="text-xl font-bold">{`View coursework for subject { ${subjectName} }`}</ModalHeader>
        <ModalBody>
          <MyTable
            key={`courseworks-table`}
            isHeaderSticky={true}
            columns={columns}
            rows={rows}
            setPage={() => {}}
            page={page}
            pages={pages}
            statusOptions={courseworkTypes}
            statusCol="type"
            sortDescriptor={sortDescriptor}
            setSortDescriptor={setSortDescriptor}
            filterValue={filterValue}
            setFilterValue={setFilterValue}
            searchBox={true}
            search={() => setFilterValue(filterValue)}
            isLoading={isLoading}
            total={total}
            size={size}
            setSize={() => {}}
            offset={offset}
            first={first}
            last={last}
            nextPage={() => {}}
            prevPage={() => {}}
            onRowAction={(row) => {
              setEditCoursework!(courseworks![row - 1]);
              setAddCourseworksOpen(true);
            }}
            addFunction={() => {
              setEditCoursework!({} as Coursework);
              setAddCourseworksOpen(true);
            }}
          />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
