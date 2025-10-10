import { useSubjectById } from "@/hooks/useSubjects";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import useToastError from "@/hooks/toasts/ToastError";
import { useEffect } from "react";

export default function OneSubjectPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { subject, isLoading, isError } = useSubjectById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/subject/edit?id=${subject?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">Subject:</div>
          <div className="info-row-content flex-col">
            {subject?.name}
            <div className="font-extralight italic text-sm -mt-1">
              {subject?.description}
            </div>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Code:</div>
          <div className="info-row-content">{subject?.code}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Syllabus:</div>
          <div className="info-row-content">{subject?.syllabus}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Curriculum:</div>
          <div className="info-row-content">{subject?.curriculum}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Level:</div>
          <div className="info-row-content">{subject?.level}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Type:</div>
          <div className="info-row-content">{subject?.type}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Category:</div>
          <div className="info-row-content">{subject?.category}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{subject?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={subject?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">
            {formatDate(subject?.datestamp)}
          </div>
        </div>
      </div>
    </div>
  );
}
