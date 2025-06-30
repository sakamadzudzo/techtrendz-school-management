import { useClassroomById } from "@/hooks/useClassrooms";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";

export default function OneClassroomPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { classroom, isLoading, isError } = useClassroomById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/classroom/edit?id=${classroom?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">Classroom:</div>
          <div className="info-row-content flex-col">
            {classroom?.name}
            <div className="font-extralight italic text-sm -mt-1">
              {classroom?.description}
            </div>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{classroom?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={classroom?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">
            {formatDate(classroom?.datestamp)}
          </div>
        </div>
      </div>
    </div>
  );
}
