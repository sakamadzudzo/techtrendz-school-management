import { useExtraCurricularById } from "@/hooks/useExtraCurriculars";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";

export default function OneExtraCurricularPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { extraCurricular, isLoading, isError } = useExtraCurricularById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/extra-curricular/edit?id=${extraCurricular?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">ExtraCurricular:</div>
          <div className="info-row-content flex-col">
            {extraCurricular?.name}
            <div className="font-extralight italic text-sm -mt-1">
              {extraCurricular?.description}
            </div>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{extraCurricular?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={extraCurricular?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">{formatDate(extraCurricular?.datestamp)}</div>
        </div>
      </div>
    </div>
  );
}
