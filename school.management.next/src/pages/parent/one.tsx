import MyChip from "@/components/MyChip";
import { useParentById } from "@/hooks/useParents";
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
  PencilSimpleLineIcon,
  FileTextIcon,
} from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import { useEffect } from "react";

export default function OneParentPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { parent, isLoading, isError } = useParentById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  const getMarksReport = () => {
    // Put code to call reports.ts from here
  }

  const getRatingsReport = () => {

  }

  const getProgressReport = () => {

  }

  const getYearlyMarksReport = () => {

  }

  const getYearlyRatingsReport = () => {

  }

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/parent/edit?id=${parent?.id}`}
          as={Link}
        >
          Edit
        </Button>
      </div>
      <Accordion isCompact={true} defaultExpandedKeys={["3"]}>
        <AccordionItem
          key="1"
          aria-label="Parent Overview"
          title="Parent Overview"
          subtitle="Quick snapshot of the parent's current status and core identifiers"
          startContent={<IdentificationCardIcon weight="thin" size={34} />}
        >
          <div className="w-full px-4 flex flex-col md:flex-row">
            <div className="w-full md:w-1/4 aspect-square flex justify-center items-center">
              <div className="w-full aspect-square">
                {!!parent?.avatarUrl ? (
                  <Image
                    src={parent?.avatarUrl}
                    alt="Profile picture"
                    shadow="md"
                    className="w-full h-full"
                  />
                ) : (
                  <Avatar
                    src={parent?.avatarUrl}
                    color="primary"
                    className="cursor-pointer w-full h-full"
                  >
                    {!parent?.avatarUrl &&
                      getInitials(parent?.name || "User")}
                  </Avatar>
                )}
              </div>
            </div>
            <div className="w-3/4 flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Name:</div>
                <div className="info-row-content">{parent?.name}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Students Count:</div>
                <div className="info-row-content">
                  {parent?.students?.length || 0}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={parent?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(parent?.datestamp)}
                </div>
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="2"
          aria-label="Personal Information"
          title="Personal Information"
          subtitle="Full details of the parent's personal profile"
          startContent={<UserFocusIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4">
            <div className="info-row">
              <div className="info-row-title">Salutation:</div>
              <div className="info-row-content">{parent?.salutation}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">First Name:</div>
              <div className="info-row-content">{parent?.firstName}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Other Names:</div>
              <div className="info-row-content">{parent?.otherNames}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">Last Name:</div>
              <div className="info-row-content">{parent?.lastName}</div>
            </div>
            <Spacer x={4} y={4} />
            <div className="info-row">
              <div className="info-row-title">D.O.B:</div>
              <div className="info-row-content">
                {formatDate(parent?.dateOfBirth)}
              </div>
            </div>
            {parent?.email && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">E-mail:</div>
                  <div className="info-row-content whitespace-pre-wrap">
                    <Link isExternal href={`mailto:${parent?.email}`}>
                      {parent?.email}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.mobileNumber && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">
                    {parent?.mobileNumber2 ? "Cell 1" : "Cell"}
                  </div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${parent?.mobileNumber}`}>
                      {parent?.mobileNumber}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.mobileNumber2 && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Cell 2:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${parent?.mobileNumber2}`}>
                      {parent?.mobileNumber2}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.telNumber && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Tel:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${parent?.telNumber}`}>
                      {parent?.telNumber}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.whatsapp && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">WhatsApp:</div>
                  <div className="info-row-content">
                    <Link
                      isExternal
                      href={sanitizeForWhatsapp(parent?.whatsapp)}
                    >
                      {parent?.whatsapp}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.linkedin && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">LinkedIn:</div>
                  <div className="info-row-content">
                    <Link isExternal href={parent?.linkedin}>
                      {parent?.linkedin}
                    </Link>
                  </div>
                </div>
              </>
            )}
            {parent?.facebook && (
              <>
                <Spacer x={4} y={4} />
                <div className="info-row">
                  <div className="info-row-title">Facebook:</div>
                  <div className="info-row-content">
                    <Link isExternal href={parent?.facebook}>
                      {parent?.facebook}
                    </Link>
                  </div>
                </div>
              </>
            )}
          </div>
        </AccordionItem>
        <AccordionItem
          key="3"
          aria-label="Students"
          title="Students"
          subtitle="List of students associated with this parent"
          startContent={<StudentIcon weight="thin" size={34} />}
        >
          <div className="flex flex-col px-4 gap-4">
            {parent?.students?.map((student, index) => (
              <div key={student.id} className="border rounded-lg p-4">
                <div className="flex justify-between items-start">
                  <div className="flex-1">
                    <div className="info-row">
                      <div className="info-row-title">Name:</div>
                      <div className="info-row-content">{student.name}</div>
                    </div>
                    <Spacer x={4} y={2} />
                    <div className="info-row">
                      <div className="info-row-title">Period:</div>
                      <div className="info-row-content">
                        {`${student.startYear} - ${student.endYear}`}
                      </div>
                    </div>
                    <Spacer x={4} y={2} />
                    <div className="flex gap-2 flex-wrap">
                      <Button
                        size="sm"
                        color="primary"
                        variant="flat"
                        onPress={getMarksReport}
                      >
                        Marks Report
                      </Button>
                      <Button
                        size="sm"
                        color="secondary"
                        variant="flat"
                        onPress={getRatingsReport}
                      >
                        Ratings Report
                      </Button>
                      <Button
                        size="sm"
                        color="success"
                        variant="flat"
                        onPress={getProgressReport}
                      >
                        Progress Report
                      </Button>
                      <Button
                        size="sm"
                        color="warning"
                        variant="flat"
                        onPress={getYearlyMarksReport}
                      >
                        Yearly Marks
                      </Button>
                      <Button
                        size="sm"
                        color="danger"
                        variant="flat"
                        onPress={getYearlyRatingsReport}
                      >
                        Yearly Ratings
                      </Button>
                    </div>
                  </div>
                  <Button
                    size="sm"
                    variant="light"
                    as={Link}
                    href={`/student/one?id=${student.id}`}
                    startContent={<StudentIcon size={16} />}
                  >
                    View Student
                  </Button>
                </div>
              </div>
            ))}
            {(!parent?.students || parent.students.length === 0) && (
              <div className="text-center text-gray-500 py-8">
                No students associated with this parent
              </div>
            )}
          </div>
        </AccordionItem>
      </Accordion>
    </div>
  );
}