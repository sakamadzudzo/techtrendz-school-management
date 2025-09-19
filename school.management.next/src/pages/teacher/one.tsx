import MyChip from "@/components/MyChip";
import { useTeacherById } from "@/hooks/useTeachers";
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
  PencilSimpleLineIcon,
  PhoneIcon,
  BriefcaseIcon,
  ShareNetworkIcon,
  ChalkboardTeacherIcon,
  FolderSimpleIcon,
} from "@phosphor-icons/react";
import React, { useEffect } from "react";
import SubjectViewCard from "@/components/SubjectViewCard";
import useToastError from "@/hooks/toasts/ToastError";

export default function OneTeacherPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { teacher, isLoading, isError } = useTeacherById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  const hasContactInfo: boolean = React.useMemo(() => {
    return !!(
      teacher?.email ||
      teacher?.mobileNumber ||
      teacher?.mobileNumber2 ||
      teacher?.telNumber
    );
  }, [teacher]);

  const hasSocials: boolean = React.useMemo(() => {
    return !!(teacher?.whatsapp || teacher?.linkedin || teacher?.facebook);
  }, [teacher]);

  const disabledKeys: string[] = React.useMemo(() => {
    let disabled: string[] = [];
    if (!hasContactInfo) {
      disabled.push("2");
    }
    if (!hasSocials) {
      disabled.push("4");
    }
    return disabled;
  }, [hasContactInfo, hasSocials]);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/teacher/edit?id=${teacher?.id}`}
          as={Link}
        >
          Edit
        </Button>
      </div>
      <Accordion
        isCompact={true}
        defaultExpandedKeys={["1"]}
        disabledKeys={disabledKeys}
      >
        <AccordionItem
          key="1"
          aria-label="Basic Information"
          title="Basic Information"
          subtitle="Teacher's identity and personal details."
          startContent={<IdentificationCardIcon weight="thin" size={34} />}
        >
          <div className="w-full px-4 flex flex-col md:flex-row">
            <div className="w-full md:w-1/4 aspect-square flex justify-center items-start">
              <div className="w-full aspect-square">
                {teacher?.avatarUrl ? (
                  <Image
                    src={teacher?.avatarUrl}
                    alt="Profile picture"
                    shadow="md"
                    className="w-full h-full"
                  />
                ) : (
                  <Avatar
                    src={teacher?.avatarUrl}
                    color="primary"
                    className="cursor-pointer w-full h-full"
                  >
                    {!teacher?.avatarUrl &&
                      getInitials(teacher?.name || "User")}
                  </Avatar>
                )}
              </div>
            </div>
            <div className="w-3/4 flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Full Name:</div>
                <div className="info-row-content">{teacher?.fullname}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Salutation:</div>
                <div className="info-row-content">{teacher?.salutation}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">First Name:</div>
                <div className="info-row-content">{teacher?.firstName}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Other Names:</div>
                <div className="info-row-content">{teacher?.otherNames}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Last Name:</div>
                <div className="info-row-content">{teacher?.lastName}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date of Birth:</div>
                <div className="info-row-content">
                  {formatDate(teacher?.dateOfBirth, false)}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={teacher?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(teacher?.datestamp)}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">System ID:</div>
                <div className="info-row-content">{teacher?.id}</div>
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="2"
          aria-label="Contact Details"
          title="Contact Details"
          subtitle="Channels for communication."
          startContent={<PhoneIcon weight="thin" size={34} />}
        >
          {teacher?.email && (
            <>
              <div className="info-row">
                <div className="info-row-title">E-mail:</div>
                <div className="info-row-content whitespace-pre-wrap">
                  <Link isExternal href={`mailto:${teacher?.email}`}>
                    {teacher?.email}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {teacher?.mobileNumber && (
            <>
              <div className="info-row">
                <div className="info-row-title">
                  {teacher?.mobileNumber2 ? "Cell 1" : "Cell"}
                </div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${teacher?.mobileNumber}`}>
                    {teacher?.mobileNumber}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {teacher?.mobileNumber2 && (
            <>
              <div className="info-row">
                <div className="info-row-title">Cell 2:</div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${teacher?.mobileNumber2}`}>
                    {teacher?.mobileNumber2}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {teacher?.telNumber && (
            <>
              <div className="info-row">
                <div className="info-row-title">Tel:</div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${teacher?.telNumber}`}>
                    {teacher?.telNumber}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
        </AccordionItem>
        <AccordionItem
          key="3"
          aria-label="Employment Status"
          title="Employment Status"
          subtitle="Record status and metadata."
          startContent={<BriefcaseIcon weight="thin" size={34} />}
        >
          <div className="info-row">
            <div className="info-row-title">Status:</div>
            <MyChip
              className="capitalize info-row-content"
              data={teacher?.status}
              size="sm"
              variant="flat"
            />
          </div>
        </AccordionItem>
        <AccordionItem
          key="4"
          aria-label="Online Presence"
          title="Online Presence"
          subtitle="Professional or social links."
          startContent={<ShareNetworkIcon weight="thin" size={34} />}
        >
          {teacher?.whatsapp && (
            <>
              <div className="info-row">
                <div className="info-row-title">WhatsApp:</div>
                <div className="info-row-content">
                  <Link
                    isExternal
                    href={sanitizeForWhatsapp(teacher?.whatsapp)}
                  >
                    {teacher?.whatsapp}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {teacher?.linkedin && (
            <>
              <div className="info-row">
                <div className="info-row-title">LinkedIn:</div>
                <div className="info-row-content">
                  <Link isExternal href={teacher?.linkedin}>
                    {teacher?.linkedin}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {teacher?.facebook && (
            <>
              <div className="info-row">
                <div className="info-row-title">Facebook:</div>
                <div className="info-row-content">
                  <Link isExternal href={teacher?.facebook}>
                    {teacher?.facebook}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
        </AccordionItem>
        <AccordionItem
          key="5"
          aria-label="Subjects & Classes"
          title="Subjects & Classes"
          subtitle="Teaching responsibilities."
          startContent={<ChalkboardTeacherIcon weight="thin" size={34} />}
        >
          {teacher?.subjects &&
            teacher?.subjects.map((subject) => (
              <SubjectViewCard subject={subject} />
            ))}
        </AccordionItem>
        <AccordionItem
          key="6"
          aria-label="Documents"
          title="Documents"
          subtitle="Supporting files."
          startContent={<FolderSimpleIcon weight="thin" size={34} />}
        ></AccordionItem>
      </Accordion>
    </div>
  );
}
