import { useDormById } from "@/hooks/useDorms";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import { useEffect } from "react";
import { Wrapper } from "@/components/Wrapper";
import React from "react";

export default function OneDormPage({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const [fetchStarted, setFetchStarted] = React.useState(false);
  const id: string | null = searchParams.get("id");
  const { dorm, isLoading, isError, errorMessage, statusCode } = useDormById(
    id!
  );

  useEffect(() => {
    // If we have an id and are loading, mark fetch as started
    if (id && isLoading && !fetchStarted) {
      setFetchStarted(true);
      incrementLoading();
    }
    // If fetch was started and isLoading is now false, decrement
    if (fetchStarted && !isLoading) {
      decrementLoading();
      setFetchStarted(false); // Reset for next fetch
    }
    // eslint-disable-next-line
  }, [id, isLoading]);

  return (
    <Wrapper hasData={!!dorm} error={errorMessage} statusCode={statusCode}>
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/dorm/edit?id=${dorm?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">Dorm:</div>
          <div className="info-row-content flex-col">
            {dorm?.name}
            <div className="font-extralight italic text-sm -mt-1">
              {dorm?.description}
            </div>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Dorm Address:</div>
          <div className="info-row-content">{dorm?.address}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Linked School:</div>
          <div className="info-row-content">
            <Link href={`/school/one?id=${dorm?.school?.id}`} isExternal>
              {dorm?.school?.name}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{dorm?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={dorm?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">{formatDate(dorm?.datestamp)}</div>
        </div>
      </div>
    </Wrapper>
  );
}
