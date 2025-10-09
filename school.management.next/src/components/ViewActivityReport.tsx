import useToastError from "@/hooks/toasts/ToastError";
import { useSaveActivityPoint } from "@/hooks/useActivityPoints";
import { RootState } from "@/store/store";
import {
  ActivityPoint,
  ExtraCurricularYear,
  Rating,
  Student,
  StudentView,
} from "@/utils/types";
import {
  Button,
  Form,
  Input,
  Modal,
  ModalBody,
  ModalContent,
  ModalFooter,
  SortDescriptor,
} from "@heroui/react";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import MyTable, { Column } from "./MyTable";
import { FloppyDiskIcon, XIcon } from "@phosphor-icons/react";
import EditFormWrapper from "./EditFormWrapper";
import InlineError from "./InlineError";

type ViewActivityReportProps = {
  activityPoints: ActivityPoint[];
  students: StudentView[];
  extraCurricularYearId: number;
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  incrementLoading: () => void;
  decrementLoading: () => void;
  reloadTrigger: () => void;
  setViewRatingsOpen: (open: boolean) => void;
  setRatings: (ratings: Rating[]) => void;
  setActivityPointId: (activityPointId: number) => void;
  setStudentName: (studentName: string) => void;
};

type ActivityReport = {
  key: number | string;
  studentName: string;
  ratingString: number | string;
};

export const ViewActivityReport = ({
  activityPoints,
  students,
  extraCurricularYearId,
  isOpen,
  onOpenChange,
  incrementLoading,
  decrementLoading,
  reloadTrigger,
  setViewRatingsOpen,
  setRatings,
  setActivityPointId,
  setStudentName,
}: ViewActivityReportProps) => {
  const [error, setError] = React.useState<string | null>(null);
  const [statusCode, setStatusCode] = React.useState(0);
  const token = useSelector((state: RootState) => state.auth.token);
  const page = 0,
    pages = 1,
    total = activityPoints?.length || 0,
    size = activityPoints?.length || 0,
    offset = 0,
    first = true,
    last = true,
    isLoading = false;
  const [filterValue, setFilterValue] = React.useState<string>("");
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "studentName",
    direction: "ascending",
  });
  const [remarks, setRemarks] = React.useState<string>("");
  const [editing, setEditing] = React.useState<boolean>(false);
  const [editingStudentId, setEditingStudentId] = React.useState<number>(0);
  const { saveActivityPoint } = useSaveActivityPoint();
  const studentName = React.useMemo(() => {
    let student = students?.find(
      (s) => Number(s.id) === Number(editingStudentId)
    );
    return student ? student.fullname : "";
  }, [editingStudentId]);

  const columns = React.useMemo<Column[]>(() => {
    return [
      {
        label: "Name",
        key: "studentName",
        allowsSorting: true,
      },
      {
        label: "Overal Rating",
        key: "ratingString",
        allowsSorting: true,
      },
      {
        label: "Actions",
        key: "actions",
        allowsSorting: false,
      },
    ];
  }, []);

  const rows = React.useMemo(() => {
    // for every student not in marks, add initialMark to list (with student set to iterate value)
    // iterate students and operate from that POV
    return students
      ?.map((row: StudentView) => {
        const studentId = row.id;
        let activityPointTemp: ActivityPoint | null =
          activityPoints?.find(
            (a) => Number(a.student?.id) === Number(studentId)
          ) || null;
        let ratingStringValue: number | string;
        let hasRating = false;
        let ratings: Rating[] = [];
        if (activityPointTemp) {
          ratings = activityPointTemp.ratings;
          let total = 0;
          let count = 1;
          ratings?.forEach((rating: Rating, index) => {
            total += rating.rating;
            count = index + 1;
          });
          ratingStringValue = (total / count).toFixed(2);
          hasRating = true;
        } else {
          ratingStringValue = "Unrated";
        }

        return {
          key: studentId,
          studentName: row.fullname,
          ratingString: ratingStringValue,
          hasRating: hasRating,
          ratings: ratings,
          activityPointId: activityPointTemp?.id || 0,
          actions: [
            {
              label: `${hasRating ? "Enter Mark" : "Create Report"}`,
              function: () => {
                if (hasRating) {
                  setRatings(ratings);
                  setStudentName(row.fullname);
                  setActivityPointId(activityPointTemp?.id || 0);
                  setViewRatingsOpen(true);
                } else {
                  setEditingStudentId(studentId);
                  setEditing(true);
                }
              },
            },
          ],
        };
      })
      ?.filter((item) =>
        item.studentName.toLowerCase().includes(filterValue.toLowerCase())
      )
      .sort((a: ActivityReport, b: ActivityReport) => {
        const col: keyof ActivityReport =
          sortDescriptor.column as keyof ActivityReport;
        if (a[col]! < b[col]!)
          return sortDescriptor.direction === "ascending" ? -1 : 1;
        if (a[col]! > b[col]!)
          return sortDescriptor.direction === "ascending" ? 1 : -1;
        return 0;
      });
  }, [students, filterValue, sortDescriptor, activityPoints]);

  const gradeStudent = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);

    const newActivityReport: ActivityPoint = {
      extraCurricularYear: { id: extraCurricularYearId } as ExtraCurricularYear,
      remarks: remarks,
      student: { id: editingStudentId } as Student,
    } as ActivityPoint;

    await saveActivityPoint(
      newActivityReport,
      incrementLoading,
      decrementLoading,
      () => {
        reloadTrigger();
        setEditingStudentId(0);
        setEditing(false);
        setRemarks("");
      },
      (error: string, statusCode: number) => {
        setError(error);
        setStatusCode(statusCode);
        useToastError({ error, statusCode });
      }
    );
  };

  useEffect(() => {
    setEditingStudentId(0);
    setEditing(false);
    setRemarks("");
  }, [isOpen]);

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
      <ModalContent className="h-full">
        <ModalBody className="grow mt-4 overflow-hidden">
          {!editing ? (
            <MyTable
              key={`activity-points-table`}
              isHeaderSticky={true}
              columns={columns}
              rows={rows}
              setPage={() => {}}
              page={page}
              pages={pages}
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
                let item = rows.find((r) => Number(r.key) === Number(row));
                if (item?.hasRating) {
                  setRatings(item?.ratings || []);
                  setStudentName(item.studentName);
                  setActivityPointId(item.activityPointId);
                  setViewRatingsOpen(true);
                } else {
                  setEditingStudentId(row);
                  setEditing(true);
                }
              }}
            />
          ) : (
            <EditFormWrapper
              editMsg={``}
              newMsg={`Add remarks for the activity for student '${studentName}'`}
              isNew={true}
            >
              <Form
                onSubmit={gradeStudent}
                className="h-full w-full md:w-1/2 pb-2 flex flex-col"
              >
                {error && <InlineError statusCode={statusCode} title={error} />}
                <div className="space-y-4 h-full w-full py-2 overflow-y-auto grow flex flex-col justify-center">
                  <div className="w-full flex flex-col justify-end space-y-4">
                    <Input
                      isRequired
                      label="Remarks"
                      name="remarks"
                      placeholder="Enter remarks"
                      type="text"
                      value={remarks || ""}
                      onValueChange={(e) => setRemarks(e)}
                      size="sm"
                      variant="bordered"
                      autoFocus={true}
                    />
                  </div>
                  <div className="w-full space-y-4">
                    <Button
                      type="submit"
                      color="primary"
                      className="button"
                      endContent={<FloppyDiskIcon weight="thin" size={25} />}
                    >
                      Save
                    </Button>
                    <Button
                      type="submit"
                      color="danger"
                      className="button"
                      endContent={<XIcon weight="thin" size={25} />}
                      onPress={() => {
                        setEditingStudentId(0);
                        setEditing(false);
                        setRemarks("");
                      }}
                    >
                      Cancel
                    </Button>
                  </div>
                </div>
              </Form>
            </EditFormWrapper>
          )}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
