import { useSubjectYearById } from "@/hooks/useSubjectYears";
import { Accordion, AccordionItem, Button, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  ChalkboardTeacherIcon,
  InfoIcon,
  PencilSimpleLineIcon,
  StackIcon,
  StackPlusIcon,
} from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import TeacherViewCard from "@/components/TeacherViewCard";
import useToastError from "@/hooks/toasts/ToastError";
import { useEffect, useMemo, useState } from "react";
import { ViewCourseworks } from "@/components/ViewCourseworks";
import { AddCoursework } from "@/components/AddCoursework";
import { Coursework } from "@/utils/types";
import { useStudentsDropdownForSubject } from "@/hooks/useStudents";

export default function OneSubjectYearPage({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  let { subjectYear, isLoading, isError, mutate } = useSubjectYearById(id!);
  const [viewCourseworksOpen, setViewCourseworksOpen] = useState(false);
  const [addCourseworksOpen, setAddCourseworksOpen] = useState(false);
  const [coursework, setCoursework] = useState<Coursework>({} as Coursework);
  const {
    students,
    isLoading: studentsLoading,
    isError: studentsError,
  } = useStudentsDropdownForSubject(id ? id : undefined);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    studentsError &&
      useToastError({
        error: studentsError?.message,
        statusCode: studentsError?.statusCode,
      });
  }, [studentsError]);

  return (
    <>
      <ViewCourseworks
        courseworks={subjectYear?.courseworks}
        key={`view-courseworks-on-subject-${isLoading}`}
        isOpen={viewCourseworksOpen}
        onOpenChange={setViewCourseworksOpen}
        subjectName={subjectYear?.name}
        subjectId={subjectYear?.id}
        students={students}
        incrementLoading={incrementLoading}
        decrementLoading={decrementLoading}
        addCourseworksOpen={addCourseworksOpen}
        setAddCourseworksOpen={setAddCourseworksOpen}
        setEditCoursework={setCoursework}
        reloadTrigger={mutate}
      />
      <AddCoursework
        key={`${
          !!coursework
            ? "edit-coursework-" + coursework.id
            : "add-coursework-to-subject"
        }`}
        isOpen={addCourseworksOpen}
        onOpenChange={setAddCourseworksOpen}
        subjectName={subjectYear?.name}
        subjectYearId={subjectYear?.id}
        incrementLoading={incrementLoading}
        decrementLoading={decrementLoading}
        reloadTrigger={mutate}
        coursework={coursework}
      />
      <div className="grow overflow-auto">
        <div
          aria-label="Page buttons"
          className="w-full flex justify-end gap-4"
        >
          <Button
            color="primary"
            endContent={<StackIcon weight="thin" size={34} />}
            onPress={() => setViewCourseworksOpen(true)}
          >
            View All Coursework
          </Button>
          <Button
            color="primary"
            endContent={<StackPlusIcon weight="thin" size={34} />}
            onPress={() => setAddCourseworksOpen(true)}
          >
            Add Coursework
          </Button>
          <Button
            color="primary"
            endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
            href={`/subject/year/edit?id=${subjectYear?.id}`}
          >
            Edit
          </Button>
        </div>
        <Accordion isCompact={true} defaultExpandedKeys={["1"]}>
          <AccordionItem
            key="1"
            aria-label="Subject Overview"
            title="Subject Overview"
            subtitle="Quick snapshot of the subject"
            startContent={<InfoIcon weight="thin" size={34} />}
          >
            <div className="flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Subject:</div>
                <div className="info-row-content flex-col">
                  {subjectYear?.name}
                  <div className="font-extralight italic text-sm -mt-1">
                    {subjectYear?.description}
                  </div>
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Calendar Year:</div>
                <div className="info-row-content">
                  {subjectYear?.calendarYear}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Code:</div>
                <div className="info-row-content">{subjectYear?.code}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Syllabus:</div>
                <div className="info-row-content">{subjectYear?.syllabus}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Curriculum:</div>
                <div className="info-row-content">
                  {subjectYear?.curriculum}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Level:</div>
                <div className="info-row-content">{subjectYear?.level}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Type:</div>
                <div className="info-row-content">{subjectYear?.type}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Category:</div>
                <div className="info-row-content">{subjectYear?.category}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">System ID:</div>
                <div className="info-row-content">{subjectYear?.id}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={subjectYear?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(subjectYear?.datestamp)}
                </div>
              </div>
            </div>
          </AccordionItem>
          <AccordionItem
            key="2"
            aria-label="Teacher"
            title="Teacher"
            subtitle="Teacher tutoring the subject for the year's classroom"
            startContent={<ChalkboardTeacherIcon weight="thin" size={34} />}
          >
            <TeacherViewCard teacher={subjectYear?.teacher} />
            <div className="px-4 w-full flex justify-end mb-4">
              <Button
                color="primary"
                endContent={<StackIcon weight="thin" size={34} />}
                onPress={() => setViewCourseworksOpen(true)}
              >
                View All Coursework
              </Button>
            </div>
          </AccordionItem>
        </Accordion>
      </div>
    </>
  );
}
