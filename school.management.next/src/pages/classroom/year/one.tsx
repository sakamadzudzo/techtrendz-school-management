import { useClassroomYearById } from "@/hooks/useClassroomYears";
import { Accordion, AccordionItem, Button, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  BookOpenTextIcon,
  ChalkboardTeacherIcon,
  InfoIcon,
  PencilSimpleLineIcon,
  StudentIcon,
} from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import TeacherViewCard from "@/components/TeacherViewCard";
import SubjectViewCard from "@/components/SubjectViewCard";

export default function OneClassroomYearPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { classroomYear, isLoading, isError } = useClassroomYearById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/classroom-year/edit?id=${classroomYear?.id}`}
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
              <div className="info-row-title">Classroom:</div>
              <div className="info-row-content flex-col">
                {classroomYear?.name}
                <div className="font-extralight italic text-sm -mt-1">
                  {classroomYear?.description}
                </div>
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Calendar Year:</div>
              <div className="info-row-content">
                {classroomYear?.calendarYear}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">System ID:</div>
              <div className="info-row-content">{classroomYear?.id}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Status:</div>
              <MyChip
                className="capitalize info-row-content"
                data={classroomYear?.status}
                size="sm"
                variant="flat"
              />
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Date Created/Modified:</div>
              <div className="info-row-content">
                {formatDate(classroomYear?.datestamp)}
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="2"
          aria-label="Homeroom Teacher"
          title="Homeroom Teacher"
          subtitle="Teacher assigned to oversee the classroom"
          startContent={<ChalkboardTeacherIcon weight="thin" size={34} />}
        >
          <TeacherViewCard teacher={classroomYear?.teacher} />
        </AccordionItem>
        <AccordionItem
          key="3"
          aria-label="Subjects"
          title="Subjects"
          subtitle="Subjects taught in this classroom"
          startContent={<BookOpenTextIcon weight="thin" size={34} />}
        >
          {classroomYear?.subjects &&
            classroomYear?.subjects.map((subject) => (
              <SubjectViewCard subject={subject} />
            ))}
        </AccordionItem>
      </Accordion>
    </div>
  );
}
