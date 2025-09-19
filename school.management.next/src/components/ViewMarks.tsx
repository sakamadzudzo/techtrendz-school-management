import pages from "@/pages";
import { courseworkTypes } from "@/utils/classes";
import { Coursework, Mark, Student, YearDropdown } from "@/utils/types";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  SortDescriptor,
  Input,
  Button,
  NumberInput,
  ModalFooter,
} from "@heroui/react";
import MyTable, { Column } from "./MyTable";
import React from "react";
import { FloppyDiskIcon } from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import { RootState } from "@/store/store";
import { useSelector } from "react-redux";

type ViewMarksProps = {
  marks?: Mark[];
  students?: YearDropdown[];
  isOpen: boolean;
  courseworkId: number;
  courseworkName: string;
  onOpenChange: (open: boolean) => void;
  incrementLoading: () => void;
  decrementLoading: () => void;
  reloadTrigger: () => void;
};

interface CustomMark extends Mark {
  name: string;
}

export const ViewMarks = ({
  marks,
  students,
  isOpen,
  courseworkId,
  courseworkName,
  onOpenChange,
  incrementLoading,
  decrementLoading,
  reloadTrigger,
}: ViewMarksProps) => {
  const [error, setError] = React.useState<string | null>(null);
  const [statusCode, setStatusCode] = React.useState(0);
  const token = useSelector((state: RootState) => state.auth.token);
  const initialMark: Mark = {
    coursework: { id: courseworkId } as Coursework,
    student: {} as Student,
    mark: 0,
    subjectName: "",
  } as Mark;
  const page = 0,
    pages = 1,
    total = marks?.length || 0,
    size = marks?.length || 0,
    offset = 0,
    first = true,
    last = true,
    isLoading = false;
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "name",
    direction: "ascending",
  });
  const [localMarks, setLocalMarks] = React.useState<Mark[]>(marks || []);
  const [editingStudentId, setEditingStudentId] = React.useState<number | null>(
    null
  );
  const [inputValue, setInputValue] = React.useState<number | undefined>(
    undefined
  );
  const [originalMarksTrack, setOriginalMarksTrack] = React.useState<
    { studentId: number; originalMark: number | undefined }[]
  >([]);
  const hasChanges = React.useMemo(() => {
    return originalMarksTrack?.length !== 0;
  }, [originalMarksTrack]);

  React.useEffect(() => {
    if (isOpen) {
      setLocalMarks(marks || []);
      setOriginalMarksTrack([]);
    }
  }, [isOpen, marks]);

  const columns = React.useMemo<Column[]>(() => {
    return [
      //   {
      //     label: "ID",
      //     key: "id",
      //     allowsSorting: false,
      //   },
      {
        label: "Name",
        key: "name",
        allowsSorting: true,
      },
      {
        label: "Mark",
        key: "markString",
        allowsSorting: true,
      },
      {
        label: "Actions",
        key: "actions",
        allowsSorting: false,
      },
    ];
  }, []);

  const markInput = (
    studentId: number,
    value: number | undefined,
    onChange: (value: number) => void
  ) => {
    const handleAccept = () => {
      const numValue: number = !value || isNaN(value) ? 0 : value;
      const currentMarkObj = localMarks?.find(
        (m) => m.student?.id === studentId
      );
      const currentMark = currentMarkObj ? currentMarkObj.mark : undefined;
      setOriginalMarksTrack(
        (prev: { studentId: number; originalMark: number | undefined }[]) => {
          const existing = prev.find(
            (p: { studentId: number; originalMark: number | undefined }) =>
              p.studentId === studentId
          );
          if (!existing) {
            return [...prev, { studentId, originalMark: currentMark }];
          }
          return prev;
        }
      );
      const index = students?.findIndex((s) => s.id === studentId) || 0;
      setLocalMarks((prev) => {
        const newMarks = [...prev];
        const existingIdx = newMarks.findIndex(
          (m) => m.student?.id === studentId
        );
        const newMark: Mark = {
          ...initialMark,
          student: { id: studentId } as Student,
          mark: numValue,
        };
        if (existingIdx >= 0) {
          newMarks[existingIdx] = { ...newMarks[existingIdx], mark: numValue };
        } else {
          newMarks.splice(index, 0, newMark);
        }
        return newMarks;
      });
      setEditingStudentId(null);
    };

    const handleClose = () => {
      setEditingStudentId(null);
    };

    const handleReset = () => {
      const trackEntry = originalMarksTrack.find(
        (t: { studentId: number; originalMark: number | undefined }) =>
          t.studentId === studentId
      );
      if (trackEntry) {
        setLocalMarks((prev) => {
          const newMarks = [...prev];
          const existingIdx = newMarks.findIndex(
            (m) => m.student?.id === studentId
          );
          if (existingIdx >= 0) {
            if (trackEntry.originalMark === undefined) {
              newMarks.splice(existingIdx, 1);
            } else {
              newMarks[existingIdx] = {
                ...newMarks[existingIdx],
                mark: trackEntry.originalMark,
              };
            }
          }
          return newMarks;
        });
        setOriginalMarksTrack(
          (prev: { studentId: number; originalMark: number | undefined }[]) =>
            prev.filter(
              (t: { studentId: number; originalMark: number | undefined }) =>
                t.studentId !== studentId
            )
        );
      }
      setEditingStudentId(null);
    };

    return (
      <>
        <div className="flex space-x-2 items-center w-fit">
          <NumberInput
            name="mark-input"
            label="Mark"
            className="w-32 rounded-lg"
            size="sm"
            variant="bordered"
            placeholder="Enter mark"
            value={Number(value)}
            onValueChange={onChange}
            // onKeyDown={(e) => e.key === "Enter" && handleAccept()}
            autoFocus={true}
          />
          <Button onPress={handleAccept} color="primary">
            Accept
          </Button>
          <Button onPress={handleReset} color="default">
            Reset
          </Button>
          <Button onPress={handleClose} color="danger">
            Close
          </Button>
        </div>
      </>
    );
  };

  const rows = React.useMemo(() => {
    // for every student not in marks, add initialMark to list (with student set to iterate value)
    // iterate students and operate from that POV
    return students
      ?.map((row: YearDropdown) => {
        const studentId = row.id;
        let markTemp: Mark | null =
          localMarks?.find((m) => m.student?.id === studentId) || null;
        let markStringValue: string | React.ReactNode;
        let mark: CustomMark;
        if (markTemp) {
          mark = { ...markTemp, name: row.name };
          markStringValue = markTemp.mark.toString();
        } else {
          mark = {
            ...initialMark,
            student: { id: studentId } as Student,
            name: row.name,
          };
          markStringValue = "Unmarked";
        }
        if (Number(editingStudentId) === Number(studentId)) {
          markStringValue = markInput(studentId, inputValue, setInputValue);
        }
        return {
          key: studentId || row.name,
          ...mark,
          markString: markStringValue,
          actions: [
            {
              label: "Enter Mark",
              function: () => {
                gradeStudent(studentId);
              },
            },
          ],
        };
      })
      ?.filter((mark) =>
        mark.name.toLowerCase().includes(filterValue.toLowerCase())
      )
      .sort((a: CustomMark, b: CustomMark) => {
        const col: keyof CustomMark = sortDescriptor.column as keyof CustomMark;
        if (a[col]! < b[col]!)
          return sortDescriptor.direction === "ascending" ? -1 : 1;
        if (a[col]! > b[col]!)
          return sortDescriptor.direction === "ascending" ? 1 : -1;
        return 0;
      });
  }, [
    localMarks,
    students,
    courseworkId,
    filterValue,
    sortDescriptor,
    editingStudentId,
    inputValue,
  ]);

  const gradeStudent = (studentId: number) => {
    const currentMark = localMarks?.find(
      (m) => Number(m.student?.id) === Number(studentId)
    );
    setInputValue(currentMark ? currentMark.mark : undefined);
    setEditingStudentId(studentId);
  };

  const onSave = async () => {
    if (!hasChanges) {
      useToastError({ error: "There are no changes to save" });
      return;
    }
    setError(null);
    setStatusCode(0);
    incrementLoading();

    const changedIds: number[] = originalMarksTrack.map((item) =>
      Number(item.studentId)
    );
    const changes = localMarks.filter((item) =>
      changedIds.includes(Number(item.student.id))
    );

    try {
      const res = await fetch("/api/mark?action=save-all-marks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(changes),
      });
      const data = await res.json();
      if (!res.ok) {
        const err = data.error || "Updating marks failed";
        const code = res.status || 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else {
        reloadTrigger();
      }
    } catch (err: any) {
      setError(err.response.data.message || "Network/Server error");
      setStatusCode(err.status || 500);
    } finally {
      decrementLoading();
    }
  };

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
      <ModalContent className="h-full overflow-y-auto">
        <ModalHeader className="text-xl font-bold flex justify-between">
          {`View/Edit marks for { ${courseworkName} }`}
          {hasChanges ? (
            <Button
              type="submit"
              color="primary"
              className=" w-fit mr-5"
              endContent={<FloppyDiskIcon weight="thin" size={25} />}
              onPress={onSave}
            >
              Save Marks
            </Button>
          ) : null}
        </ModalHeader>
        <ModalBody className="grow">
          <MyTable
            key={`marks-table`}
            isHeaderSticky={true}
            columns={columns}
            rows={rows}
            setPage={() => {}}
            page={page}
            pages={pages}
            // statusOptions={courseworkTypes}
            // statusCol="type"
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
              gradeStudent(row);
              // setEditingStudentId(row);
            }}
          />
        </ModalBody>
        <ModalFooter></ModalFooter>
      </ModalContent>
    </Modal>
  );
};
