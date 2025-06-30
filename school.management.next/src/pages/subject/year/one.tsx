import { useSubjectYearById } from "@/hooks/useSubjectYears";
import { Accordion, AccordionItem, Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  BookOpenTextIcon,
  ChalkboardTeacherIcon,
  InfoIcon,
  PencilSimpleLineIcon,
} from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import SubjectViewCard from "@/components/SubjectViewCard";
import TeacherViewCard from "@/components/TeacherViewCard";
import classroomYear from "@/pages/api/classroom-year";

export default function OneSubjectYearPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { subjectYear, isLoading, isError } = useSubjectYearById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
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
              <div className="info-row-content">{subjectYear?.curriculum}</div>
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
        </AccordionItem>
      </Accordion>
    </div>
  );
}
