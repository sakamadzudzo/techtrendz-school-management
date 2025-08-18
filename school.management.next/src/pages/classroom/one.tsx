import { useClassroomById } from "@/hooks/useClassrooms";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";
import { Wrapper } from "@/components/Wrapper";
import React, { useEffect } from "react";
import dorm from "../api/dorm";
import useToastError from "@/hooks/toasts/ToastError";

export default function OneClassroomPage({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const [fetchStarted, setFetchStarted] = React.useState(false);
  const id: string | null = searchParams.get("id");
  const { classroom, isLoading, isError, errorMessage, statusCode } =
    useClassroomById(id!);

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

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  return (
    <Wrapper hasData={!!classroom} error={errorMessage} statusCode={statusCode}>
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
    </Wrapper>
  );
}
