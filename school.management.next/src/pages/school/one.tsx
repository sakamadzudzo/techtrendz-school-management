import { useSchoolById } from "@/hooks/useSchools";
import { Button, Link, Spacer } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { PencilSimpleLineIcon } from "@phosphor-icons/react";
import { formatDate } from "@/utils/helpers";
import MyChip from "@/components/MyChip";

export default function OneSchoolPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { school, isLoading, isError } = useSchoolById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          as={Link}
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/school/edit?id=${school?.id}`}
        >
          Edit
        </Button>
      </div>
      <div className="flex flex-col px-4">
        <div className="info-row">
          <div className="info-row-title">Name:</div>
          <div className="info-row-content">{school?.name}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Email:</div>
          <div className="info-row-content">
            <Link isExternal href={`mailto:${school?.email}`}>
              {school?.email}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Tel Number:</div>
          <div className="info-row-content">
            <Link isExternal href={`tel:${school?.telNumber}`}>
              {school?.telNumber}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Cell 1:</div>
          <div className="info-row-content">
            <Link isExternal href={`tel:${school?.mobileNumber}`}>
              {school?.mobileNumber}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Cell 2:</div>
          <div className="info-row-content">
            <Link isExternal href={`tel:${school?.mobileNumber2}`}>
              {school?.mobileNumber2}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">School Address:</div>
          <div className="info-row-content">{school?.address}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Website:</div>
          <div className="info-row-content">
            <Link isExternal href={school?.website}>
              {school?.website}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">System ID:</div>
          <div className="info-row-content">{school?.id}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={school?.status}
            size="sm"
            variant="flat"
          />
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Date Created/Modified:</div>
          <div className="info-row-content">
            {formatDate(school?.datestamp)}
          </div>
        </div>
      </div>
    </div>
  );
}
