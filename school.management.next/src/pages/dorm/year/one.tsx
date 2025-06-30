import { useDormYearById } from "@/hooks/useDormYears";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";

export default function OneDormYearPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { dormYear, isLoading, isError } = useDormYearById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/dorm/year/edit?id=${dormYear?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">Dorm:</div>
          <div className="info-row-content flex-col">
            {dormYear?.dorm?.name}
            <div className="font-extralight italic text-sm -mt-1">
              {dormYear?.dorm?.description}
            </div>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Calendar Year:</div>
          <div className="info-row-content">{dormYear?.calendarYear}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Dorm Address:</div>
          <div className="info-row-content">{dormYear?.dorm?.address}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Linked School:</div>
          <div className="info-row-content">
            <Link href={`/school/one?id=${dormYear?.dorm?.school?.id}`} isExternal>
              {dormYear?.dorm?.school?.name}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{dormYear?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={dormYear?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">{formatDate(dormYear?.datestamp)}</div>
        </div>
      </div>
    </div>
  );
}
