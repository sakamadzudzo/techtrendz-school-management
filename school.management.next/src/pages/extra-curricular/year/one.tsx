import { useExtraCurricularYearById } from "@/hooks/useExtraCurricularYears";
import { Accordion, AccordionItem, Button, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  StudentIcon,
  PencilSimpleLineIcon,
  ChalkboardTeacherIcon,
  InfoIcon,
  ExamIcon,
} from "@phosphor-icons/react";
import MyChip from "@/components/MyChip";
import { formatDate } from "@/utils/helpers";
import TeacherViewCard from "@/components/TeacherViewCard";
import StudentViewCard from "@/components/StudentViewCard";
import useToastError from "@/hooks/toasts/ToastError";
import React, { useEffect } from "react";
import { useActivityPointsByActivityId } from "@/hooks/useActivityPoints";
import { ViewActivityReport } from "@/components/ViewActivityReport";
import { Rating } from "@/utils/types";
import { ViewRatings } from "@/components/ViewRatings";

export default function OneExtraCurricularYearPage({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { extraCurricularYear, isLoading, isError, mutate } =
    useExtraCurricularYearById(id!);
  const {
    activityPoints,
    isLoading: apLoading,
    isError: apError,
    mutate: apMutate,
  } = useActivityPointsByActivityId(id!);
  const [viewActivityReportOpen, setViewActivityReportOpen] =
    React.useState<boolean>(false);
  const [viewRatingsOpen, setViewRatingsOpen] = React.useState<boolean>(false);
  const [ratings, setRatings] = React.useState<Rating[]>([]);
  const [activityPointId, setActivityPointId] = React.useState<number>(0);
  const [studentName, setStudentName] = React.useState<string>("");

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    apError &&
      useToastError({
        error: apError?.message,
        statusCode: apError?.statusCode,
      });
  }, [apError]);

  return (
    <>
      <ViewActivityReport
        key={`view-activity-report-open-${id}`}
        activityPoints={activityPoints}
        isOpen={viewActivityReportOpen}
        extraCurricularYearId={extraCurricularYear?.id}
        students={extraCurricularYear?.students}
        decrementLoading={decrementLoading}
        incrementLoading={incrementLoading}
        reloadTrigger={apMutate}
        onOpenChange={setViewActivityReportOpen}
        setRatings={setRatings}
        setViewRatingsOpen={setViewRatingsOpen}
        setActivityPointId={setActivityPointId}
        setStudentName={setStudentName}
      />
      <ViewRatings
        key={`view-ratings-report-open-${activityPointId}`}
        ratings={ratings}
        activityPointId={activityPointId}
        activityName={extraCurricularYear?.name}
        isOpen={viewRatingsOpen}
        studentName={studentName}
        onOpenChange={setViewRatingsOpen}
        reloadTrigger={apMutate}
        decrementLoading={decrementLoading}
        incrementLoading={incrementLoading}
      />
      <div className="grow overflow-auto">
        <div
          aria-label="Page buttons"
          className="w-full flex justify-end gap-4"
        >
          <Button
            color="primary"
            endContent={<ExamIcon weight="thin" size={34} />}
            onPress={() => setViewActivityReportOpen(true)}
          >
            Grade/Rate
          </Button>
          <Button
            color="primary"
            endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
            href={`/extra-curricular/year/edit?id=${extraCurricularYear?.id}`}
          >
            Edit
          </Button>
        </div>
        <Accordion isCompact={true} defaultExpandedKeys={["1"]}>
          <AccordionItem
            key="1"
            aria-label="Extra Curricular Activity Overview"
            title="Extra Curricular Activity Overview"
            subtitle="Quick snapshot of the extra curricular activity"
            startContent={<InfoIcon weight="thin" size={34} />}
          >
            <div className="flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Activity:</div>
                <div className="info-row-content flex-col">
                  {extraCurricularYear?.name}
                  <div className="font-extralight italic text-sm -mt-1">
                    {extraCurricularYear?.description}
                  </div>
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Calendar Year:</div>
                <div className="info-row-content">
                  {extraCurricularYear?.calendarYear}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">System ID:</div>
                <div className="info-row-content">
                  {extraCurricularYear?.id}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={extraCurricularYear?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(extraCurricularYear?.datestamp)}
                </div>
              </div>
            </div>
          </AccordionItem>
          <AccordionItem
            key="2"
            aria-label="Facilitators"
            title="Facilitators"
            subtitle="Teachers overseeing the extra curricular activity"
            startContent={<ChalkboardTeacherIcon weight="thin" size={34} />}
          >
            {extraCurricularYear?.facilitators &&
              extraCurricularYear?.facilitators.map((facilitator) => (
                <TeacherViewCard teacher={facilitator} />
              ))}
          </AccordionItem>
          <AccordionItem
            key="3"
            aria-label="Students"
            title="Students"
            subtitle="Students participating in the extra curricular activity"
            startContent={<StudentIcon weight="thin" size={34} />}
          >
            {extraCurricularYear?.students &&
              extraCurricularYear?.students.map((student) => (
                <StudentViewCard student={student} />
              ))}
          </AccordionItem>
        </Accordion>
      </div>
    </>
  );
}
