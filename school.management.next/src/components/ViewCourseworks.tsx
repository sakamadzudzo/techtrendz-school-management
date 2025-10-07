import { Coursework, Mark, YearDropdown } from "@/utils/types";
import {
  Modal,
  ModalBody,
  ModalContent,
  ModalHeader,
  SortDescriptor,
} from "@heroui/react";
import MyTable, { Column } from "./MyTable";
import React, { useEffect } from "react";
import { courseworkTypes } from "@/utils/classes";
import { formatDate } from "@/utils/helpers";
import { ViewMarks } from "./ViewMarks";

type ViewCourseworksProps = {
  courseworks?: Coursework[];
  isOpen: boolean;
  subjectId: number;
  subjectName: string;
  addCourseworksOpen: boolean;
  editCoursework?: Coursework;
  students?: YearDropdown[];
  onOpenChange: (open: boolean) => void;
  decrementLoading: () => void;
  incrementLoading: () => void;
  setAddCourseworksOpen: (open: boolean) => void;
  setEditCoursework?: (coursework: Coursework) => void;
  reloadTrigger: () => void;
};

export const ViewCourseworks = ({
  courseworks,
  isOpen,
  subjectName,
  students,
  onOpenChange,
  setAddCourseworksOpen,
  setEditCoursework,
  incrementLoading,
  decrementLoading,
  reloadTrigger,
}: ViewCourseworksProps) => {
  const page = 0,
    pages = 1,
    total = courseworks?.length || 0,
    size = courseworks?.length || 0,
    offset = 0,
    first = true,
    last = true,
    isLoading = false;
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "title",
    direction: "ascending",
  });
  const [currentCoursework, setCurrentCoursework] = React.useState<Coursework>(
    {} as Coursework
  );
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
        key: "graded",
        label: "Graded",
        allowsSorting: false,
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
        key: row.id || row.title,
        datestamp: formatDate(row.datestamp),
        graded: `${Number(row?.marks?.length)} of ${Number(students?.length)}`,
        actions: [
          {
            label: "View Marks",
            function: () => {
              setMarks(row.marks || []);
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
          {
            label: "Grade",
            function: () => {
              setCurrentCoursework(row!);
              setViewMarks(true);
            },
            key: `grade-${row.id}`,
          },
        ],
      }));
  }, [courseworks, filterValue, sortDescriptor]);

  return (
    <>
      <ViewMarks
        key={`view-marks-for-${currentCoursework?.id}`}
        courseworkId={currentCoursework?.id || 0}
        courseworkName={currentCoursework?.title || ""}
        marks={currentCoursework?.marks || []}
        students={students}
        isOpen={viewMarks}
        onOpenChange={setViewMarks}
        incrementLoading={incrementLoading}
        decrementLoading={decrementLoading}
        reloadTrigger={reloadTrigger}
      />
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
        <ModalContent className="h-full">
          <ModalHeader className="text-xl font-bold">{`View coursework for subject { ${subjectName} }`}</ModalHeader>
          <ModalBody className="grow mt-4 overflow-hidden">
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
                // setEditCoursework!(courseworks![row - 1]);
                // setAddCourseworksOpen(true);
                setCurrentCoursework(
                  courseworks?.find(
                    (work) => Number(work.id) === Number(row)
                  ) || ({} as Coursework)
                );
                setViewMarks(true);
              }}
              addFunction={() => {
                setEditCoursework!({} as Coursework);
                setAddCourseworksOpen(true);
              }}
            />
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};
