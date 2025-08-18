import { useExtraCurricularYearById } from "@/hooks/useExtraCurricularYears";
import { Accordion, AccordionItem, Button, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  IdentificationCardIcon,
  UserFocusIcon,
  StudentIcon,
  PencilSimpleLineIcon,
  ChalkboardTeacherIcon,
  InfoIcon,
} from "@phosphor-icons/react";
import MyChip from "@/components/MyChip";
import { formatDate } from "@/utils/helpers";
import TeacherViewCard from "@/components/TeacherViewCard";
import StudentViewCard from "@/components/StudentViewCard";
import useToastError from "@/hooks/toasts/ToastError";
import { useEffect } from "react";

export default function OneExtraCurricularYearPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { extraCurricularYear, isLoading, isError } =
    useExtraCurricularYearById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
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
              <div className="info-row-content">{extraCurricularYear?.id}</div>
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
          {extraCurricularYear.facilitators &&
            extraCurricularYear.facilitators.map((facilitator) => (
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
          {extraCurricularYear.students &&
            extraCurricularYear.students.map((student) => (
              <StudentViewCard student={student} />
            ))}
        </AccordionItem>
      </Accordion>
    </div>
  );
}
