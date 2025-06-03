import { IconPencilThin } from "@/components/icons/pencil-thin";
import MyChip from "@/components/MyChip";
import { useStudentById } from "@/hooks/useStudents";
import { formatDate, getInitials } from "@/utils/helpers";
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Image,
  Input,
  Spacer,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";

export default function OneStudentPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { student, isLoading, isError } = useStudentById(id!);
  const accordionSubtitle = "Press to expand/collapse";

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={
            <IconPencilThin className="w-6 h-6 bg-transparent fill-white" />
          }
        >
          Edit
        </Button>
      </div>
      <Accordion isCompact={true} defaultExpandedKeys={["1"]}>
        <AccordionItem
          key="1"
          aria-label="Student Overview"
          title="Student Overview"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div className="w-full px-4 flex flex-col md:flex-row">
            <div className="w-full md:w-1/4 aspect-square flex justify-center items-center">
              {!!student?.person?.avatarUrl ? (
                <Image
                  // src={student?.person?.avatarUrl}
                  src="C:\Users\sakamadzudzo\Desktop\Pro_Pic_3.jpg"
                  alt="Profile picture"
                  shadow="md"
                  className="w-full aspect-square"
                />
              ) : (
                <Avatar
                  src={student?.person?.avatarUrl}
                  color="primary"
                  className="cursor-pointer w-full aspect-square"
                >
                  {!student?.person?.avatarUrl &&
                    getInitials(student?.person?.name || "User")}
                </Avatar>
              )}
            </div>
            <div className="w-3/4 flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Name:</div>
                <div className="info-row-content">{student?.person?.name}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Period:</div>
                <div className="info-row-content">
                  {`${student?.startYear} - ${student?.endYear}`}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Current Class:</div>
                <div className="info-row-content">
                  {`${student?.classroom?.classroom?.name} - ${student?.classroom?.calendarYear}`}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Current Dorm:</div>
                <div className="info-row-content">
                  {`${student?.dorm?.dorm?.name} - ${student?.dorm?.calendarYear}`}
                </div>
              </div>
              <Spacer x={4} y={4} />
              {student?.school && (
                <>
                  <div className="info-row">
                    <div className="info-row-title">School:</div>
                    <div className="info-row-content">
                      {student?.school?.name}
                    </div>
                  </div>
                  <Spacer x={4} y={4} />
                </>
              )}
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={student?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(student?.datestamp)}
                </div>
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="2"
          aria-label="Personal Information"
          title="Personal Information"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">Salutation:</div>
              <div className="info-row-content">
                {student?.person?.salutation}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">First Name:</div>
              <div className="info-row-content">
                {student?.person?.firstName}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Other Names:</div>
              <div className="info-row-content">
                {student?.person?.otherNames}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Last Name:</div>
              <div className="info-row-content">
                {student?.person?.lastName}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">D.O.B:</div>
              <div className="info-row-content">
                {formatDate(student?.person?.dateOfBirth)}
              </div>
            </div>
            <Spacer x={4} y={4} />
            {student?.person?.email && (
              <>
                <div className="info-row">
                  <div className="info-row-title">E-mail:</div>
                  <div className="info-row-content whitespace-pre-wrap">
                    {student?.person?.email}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.mobileNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">
                    {student?.person?.mobileNumber2 ? "Cell 1" : "Cell"}
                  </div>
                  <div className="info-row-content">
                    {student?.person?.mobileNumber}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.mobileNumber2 && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Cell 2:</div>
                  <div className="info-row-content">
                    {student?.person?.mobileNumber2}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.telNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Tel:</div>
                  <div className="info-row-content">
                    {student?.person?.telNumber}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.whatsapp && (
              <>
                <div className="info-row">
                  <div className="info-row-title">WhatsApp:</div>
                  <div className="info-row-content">
                    {student?.person?.whatsapp}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.linkedin && (
              <>
                <div className="info-row">
                  <div className="info-row-title">LinkedIn:</div>
                  <div className="info-row-content">
                    {student?.person?.linkedin}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.person?.facebook && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Facebook:</div>
                  <div className="info-row-content">
                    {student?.person?.facebook}
                  </div>
                </div>
              </>
            )}
          </div>
        </AccordionItem>
        <AccordionItem
          key="3"
          aria-label="Academic Details"
          title="Academic Details"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div>Academic Details</div>
        </AccordionItem>
        <AccordionItem
          key="4"
          aria-label="Subjects & Teachers"
          title="Subjects & Teachers"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div>Subjects & Teachers</div>
        </AccordionItem>
        <AccordionItem
          key="5"
          aria-label="Dormitory Information"
          title="Dormitory Information"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div>Dormitory Information</div>
        </AccordionItem>
        <AccordionItem
          key="6"
          aria-label="School Information"
          title="School Information"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div>School Information</div>
        </AccordionItem>
        <AccordionItem
          key="7"
          aria-label="System Metadata"
          title="System Metadata"
          subtitle={accordionSubtitle}
          startContent={<IconPencilThin className="w-6 h-6 bg-transparent" />}
        >
          <div>System Metadata (Optional/For Admin View)</div>
        </AccordionItem>
      </Accordion>
    </div>
  );
}
