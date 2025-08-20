import MyChip from "@/components/MyChip";
import { useStudentById } from "@/hooks/useStudents";
import { formatDate, getInitials, sanitizeForWhatsapp } from "@/utils/helpers";
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Image,
  Link,
  Spacer,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  IdentificationCardIcon,
  UserFocusIcon,
  StudentIcon,
  BookOpenTextIcon,
  HouseLineIcon,
  ChalkboardSimpleIcon,
  PencilSimpleLineIcon,
} from "@phosphor-icons/react";
import SubjectViewCard from "@/components/SubjectViewCard";
import useToastError from "@/hooks/toasts/ToastError";
import { useEffect } from "react";

export default function OneStudentPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { student, isLoading, isError } = useStudentById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/student/edit?id=${student?.id}`}
          as={Link}
        >
          Edit
        </Button>
      </div>
      <Accordion isCompact={true} defaultExpandedKeys={["1"]}>
        <AccordionItem
          key="1"
          aria-label="Student Overview"
          title="Student Overview"
          subtitle="Quick snapshot of the student's current status and core identifiers"
          startContent={<IdentificationCardIcon weight="thin" size={34} />}
        >
          <div className="w-full px-4 flex flex-col md:flex-row">
            <div className="w-full md:w-1/4 aspect-square flex justify-center items-center">
              <div className="w-full aspect-square">
                {!!student?.avatarUrl ? (
                  <Image
                    // src={student?.avatarUrl}
                    src="C:\Users\sakamadzudzo\Desktop\Pro_Pic_3.jpg"
                    alt="Profile picture"
                    shadow="md"
                    className="w-full h-full"
                  />
                ) : (
                  <Avatar
                    src={student?.avatarUrl}
                    color="primary"
                    className="cursor-pointer w-full h-full"
                  >
                    {!student?.avatarUrl &&
                      getInitials(student?.name ?? "User")}
                  </Avatar>
                )}
              </div>
            </div>
            <div className="w-3/4 flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Name:</div>
                <div className="info-row-content">{student?.name}</div>
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
                  {`${student?.classroom?.name} - ${student?.classroom?.calendarYear}`}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Current Dorm:</div>
                <div className="info-row-content">
                  {`${student?.dorm?.name} - ${student?.dorm?.calendarYear}`}
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
          subtitle="Full details of the student's personal profile"
          startContent={<UserFocusIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">Salutation:</div>
              <div className="info-row-content">{student?.salutation}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">First Name:</div>
              <div className="info-row-content">{student?.firstName}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Other Names:</div>
              <div className="info-row-content">{student?.otherNames}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Last Name:</div>
              <div className="info-row-content">{student?.lastName}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">D.O.B:</div>
              <div className="info-row-content">
                {formatDate(student?.dateOfBirth)}
              </div>
            </div>
            {student?.email && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">E-mail:</div>
                  <div className="info-row-content whitespace-pre-wrap">
                    <Link isExternal href={`mailto:${student?.email}`}>
                      {student?.email}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.mobileNumber && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">
                    {student?.mobileNumber2 ? "Cell 1" : "Cell"}
                  </div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${student?.mobileNumber}`}>
                      {student?.mobileNumber}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.mobileNumber2 && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Cell 2:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${student?.mobileNumber2}`}>
                      {student?.mobileNumber2}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.telNumber && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Tel:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${student?.telNumber}`}>
                      {student?.telNumber}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.whatsapp && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">WhatsApp:</div>
                  <div className="info-row-content">
                    <Link
                      isExternal
                      href={sanitizeForWhatsapp(student?.whatsapp)}
                    >
                      {student?.whatsapp}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.linkedin && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">LinkedIn:</div>
                  <div className="info-row-content">
                    <Link isExternal href={student?.linkedin}>
                      {student?.linkedin}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {student?.facebook && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Facebook:</div>
                  <div className="info-row-content">
                    <Link isExternal href={student?.facebook}>
                      {student?.facebook}
                    </Link>
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
          subtitle="Shows enrollment and classroom info"
          startContent={<StudentIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">Start Year - End Year:</div>
              <div className="info-row-content">
                {`${student?.startYear} - ${student?.endYear}`}
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row flex-col">
              <div className="info-row-title">Current Classroom:</div>
              <div className="info-row-content pl-8 flex-col">
                <div className="flex">
                  <div className="w-48 font-normal">Name:</div>
                  <div>{student?.classroom?.name}</div>
                </div>
                <Spacer x={4} y={4} />
                <div className="flex">
                  <div className="w-48 font-normal">Description:</div>
                  <div>{student?.classroom?.description}</div>
                </div>
                <Spacer x={4} y={4} />
                <div className="flex">
                  <div className="w-48 font-normal">Year:</div>
                  <div>{student?.classroom?.calendarYear}</div>
                </div>
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="4"
          aria-label="Subjects & Teachers"
          title="Subjects & Teachers"
          subtitle="Lists subjects taken and their respective teachers"
          startContent={<BookOpenTextIcon weight="thin" size={34} />}
        >
          {student?.classroom?.subjects.map((subject) => (
            <SubjectViewCard subject={subject} />
          ))}
        </AccordionItem>
        <AccordionItem
          key="5"
          aria-label="Dormitory Information"
          title="Dormitory Information"
          subtitle="Dormitory accommodation details"
          startContent={<HouseLineIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">Dorm:</div>
              <div className="info-row-content flex-col">
                {student?.dorm?.name}
                <div className="font-extralight italic text-sm -mt-1">
                  {student?.dorm?.description}
                </div>
              </div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Dorm Address:</div>
              <div className="info-row-content">{student?.dorm?.address}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Linked School:</div>
              <div className="info-row-content">
                {student?.dorm?.linkedSchool}
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="6"
          aria-label="School Information"
          title="School Information"
          subtitle="Institutional context of the student"
          startContent={<ChalkboardSimpleIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">School:</div>
              <div className="info-row-content">{student?.school?.name}</div>
            </div>
            <Spacer x={4} y={4} />
            {student?.school?.email && (
              <>
                <div className="info-row">
                  <div className="info-row-title">E-mail:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`mailto:${student?.school?.email}`}>
                      {student?.school?.email}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.school?.website && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Website:</div>
                  <div className="info-row-content">
                    <Link isExternal href={student?.school?.website}>
                      {student?.school?.website}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.school?.mobileNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">
                    {student?.school?.mobileNumber2 ? "Cell 1" : "Cell"}
                  </div>
                  <div className="info-row-content">
                    <Link
                      isExternal
                      href={`tel:${student?.school?.mobileNumber}`}
                    >
                      {student?.school?.mobileNumber}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.school?.mobileNumber2 && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Cell 2:</div>
                  <div className="info-row-content">
                    <Link
                      isExternal
                      href={`tel:${student?.school?.mobileNumber2}`}
                    >
                      {student?.school?.mobileNumber2}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.school?.telNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Tel:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${student?.school?.telNumber}`}>
                      {student?.school?.telNumber}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {student?.school?.address && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Address:</div>
                  <div className="info-row-content">
                    {student?.school?.address}
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
          </div>
        </AccordionItem>
        {/* <AccordionItem
          key="7"
          aria-label="System Metadata"
          title="System Metadata"
          subtitle="Shows system-level data"
          startContent={<GearIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">School:</div>
              <div className="info-row-content">{student?.school?.name}</div>
            </div>
            <Spacer x={4} y={4} />
          </div>
        </AccordionItem> */}
      </Accordion>
    </div>
  );
}
