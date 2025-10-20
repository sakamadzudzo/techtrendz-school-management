import MyChip from "@/components/MyChip";
import { useParentById } from "@/hooks/useParents";
import { formatDate, getInitials, sanitizeForWhatsapp } from "@/utils/helpers";
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Form,
  Image,
  Link,
  Popover,
  PopoverContent,
  PopoverTrigger,
  Select,
  SelectItem,
  Spacer,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  IdentificationCardIcon,
  UserFocusIcon,
  StudentIcon,
  PencilSimpleLineIcon,
} from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import React, { useCallback, useEffect } from "react";
import {
  useGenerateMarksReport,
  useGenerateProgressReport,
  useGenerateRatingsReport,
} from "@/hooks/useReports";
import { ReportFilter, StudentView } from "@/utils/types";

export default function OneParentPage({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { parent, isLoading, isError } = useParentById(id!);
  const [calendarYear, setCalendarYear] = React.useState<number | undefined>(
    undefined
  );
  const { generateMarksByStudentYearReport } = useGenerateMarksReport();
  const { generateRatingsByStudentYearReport } = useGenerateRatingsReport();
  const { generateProgressReportByStudent } = useGenerateProgressReport();

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const getMarksReport = useCallback(
    async (student: StudentView, type: "excel" | "pdf") => {
      const link2 = document.createElement("a");
      link2.href = "#";
      link2.download = `placeholder`;
      document.body.appendChild(link2);
      link2.focus();
      document.body.removeChild(link2);

      const reportFilter: ReportFilter = {} as ReportFilter;
      reportFilter.studentId = student.id;
      reportFilter.year = calendarYear;

      const date = new Date();
      const dateString = `${date.getFullYear()}-${(date.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
      const filename = `Marks By Student By Year - ${student.fullname} - ${calendarYear} - ${dateString}`;
      reportFilter.fileName = filename;
      reportFilter.type = type;

      const { data, error, statusCode, success } =
        await generateMarksByStudentYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          () => {
            setCalendarYear(undefined);
          },
          (error, statusCode) => {
            useToastError({ error, statusCode });
            setCalendarYear(undefined);
          }
        );

      if (statusCode === 204) {
        useToastError({ error: "No Content", statusCode });
        setCalendarYear(undefined);
        return;
      }

      if (statusCode !== 200) {
        return;
      }

      let blob;
      if (type === "excel") {
        blob = new Blob([data!], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
      } else {
        blob = new Blob([data!], {
          type: "application/pdf",
        });
      }
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = `${filename}.xlsx`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    },
    [generateMarksByStudentYearReport]
  );

  const getRatingsReport = useCallback(
    async (student: StudentView, type: "excel" | "pdf") => {
      const link2 = document.createElement("a");
      link2.href = "#";
      link2.download = `placeholder`;
      document.body.appendChild(link2);
      link2.focus();
      document.body.removeChild(link2);

      const reportFilter: ReportFilter = {} as ReportFilter;
      reportFilter.studentId = student.id;
      reportFilter.year = calendarYear;

      const date = new Date();
      const dateString = `${date.getFullYear()}-${(date.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
      const filename = `Ratings By Student By Year - ${student.fullname} - ${calendarYear} - ${dateString}`;
      reportFilter.fileName = filename;
      reportFilter.type = type;

      const { data, error, statusCode, success } =
        await generateRatingsByStudentYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          () => {
            setCalendarYear(undefined);
          },
          (error, statusCode) => {
            useToastError({ error, statusCode });
            setCalendarYear(undefined);
          }
        );

      if (statusCode === 204) {
        useToastError({ error: "No Content", statusCode });
        setCalendarYear(undefined);
        return;
      }

      if (statusCode !== 200) {
        return;
      }

      let blob;
      if (type === "excel") {
        blob = new Blob([data!], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
      } else {
        blob = new Blob([data!], {
          type: "application/pdf",
        });
      }
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = `${filename}.xlsx`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    },
    [generateRatingsByStudentYearReport]
  );

  const getProgressReport = useCallback(
    async (student: StudentView, type: "excel" | "pdf") => {
      const link2 = document.createElement("a");
      link2.href = "#";
      link2.download = `placeholder`;
      document.body.appendChild(link2);
      link2.focus();
      document.body.removeChild(link2);

      const reportFilter: ReportFilter = {} as ReportFilter;
      reportFilter.studentId = student.id;

      const date = new Date();
      const dateString = `${date.getFullYear()}-${(date.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
      const filename = `Progress By Student - ${student.fullname} - ${dateString}`;
      reportFilter.fileName = filename;
      reportFilter.type = type;

      const { data, error, statusCode, success } =
        await generateProgressReportByStudent(
          reportFilter,
          incrementLoading,
          decrementLoading,
          () => {
            setCalendarYear(undefined);
          },
          (error, statusCode) => {
            useToastError({ error, statusCode });
            setCalendarYear(undefined);
          }
        );

      if (statusCode === 204) {
        useToastError({ error: "No Content", statusCode });
        setCalendarYear(undefined);
        return;
      }

      if (statusCode !== 200) {
        return;
      }

      let blob;
      if (type === "excel") {
        blob = new Blob([data!], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
      } else {
        blob = new Blob([data!], {
          type: "application/pdf",
        });
      }
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = `${filename}.xlsx`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    },
    [generateRatingsByStudentYearReport]
  );

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
                    {!parent?.avatarUrl && getInitials(parent?.name || "User")}
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
              <div
                key={student.id}
                id={`student-${student.id}`}
                className="border rounded-lg p-4"
              >
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
                      <Popover placement="right" backdrop="blur">
                        <PopoverTrigger>
                          <Button size="sm" color="primary" variant="flat">
                            Marks Report
                          </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                          <div className="px-1 py-2">
                            <Form>
                              <Select
                                label="Year"
                                variant="bordered"
                                selectionMode="single"
                                placeholder="Select year..."
                                className={calendarYear ? "mb-2" : ""}
                                selectedKeys={
                                  calendarYear ? [String(calendarYear)] : []
                                }
                                errorMessage={
                                  !calendarYear ? "Year is required" : ""
                                }
                                isInvalid={!calendarYear}
                                onSelectionChange={(e) =>
                                  setCalendarYear(
                                    e ? Number(e.currentKey) : undefined
                                  )
                                }
                              >
                                {[
                                  ...Array.from(
                                    {
                                      length:
                                        Number(student.endYear) +
                                        1 -
                                        Number(student.startYear),
                                    },
                                    (v, k) => k + Number(student.startYear)
                                  ),
                                ].map((year) => (
                                  <SelectItem key={String(year)}>
                                    {String(year)}
                                  </SelectItem>
                                ))}
                              </Select>
                            </Form>
                            {calendarYear && (
                              <div className="space-x-2">
                                <Button
                                  size="sm"
                                  color="primary"
                                  variant="flat"
                                  onPress={() => getMarksReport(student, "pdf")}
                                >
                                  PDF
                                </Button>
                                <Button
                                  size="sm"
                                  color="primary"
                                  variant="flat"
                                  onPress={() =>
                                    getMarksReport(student, "excel")
                                  }
                                >
                                  Excel
                                </Button>
                              </div>
                            )}
                          </div>
                        </PopoverContent>
                      </Popover>
                      <Popover placement="right" backdrop="blur">
                        <PopoverTrigger>
                          <Button size="sm" color="secondary" variant="flat">
                            Ratings Report
                          </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                          <div className="px-1 py-2">
                            <Form>
                              <Select
                                label="Year"
                                variant="bordered"
                                selectionMode="single"
                                placeholder="Select year..."
                                className={calendarYear ? "mb-2" : ""}
                                selectedKeys={
                                  calendarYear ? [String(calendarYear)] : []
                                }
                                errorMessage={
                                  !calendarYear ? "Year is required" : ""
                                }
                                isInvalid={!calendarYear}
                                onSelectionChange={(e) =>
                                  setCalendarYear(
                                    e ? Number(e.currentKey) : undefined
                                  )
                                }
                              >
                                {[
                                  ...Array.from(
                                    {
                                      length:
                                        Number(student.endYear) +
                                        1 -
                                        Number(student.startYear),
                                    },
                                    (v, k) => k + Number(student.startYear)
                                  ),
                                ].map((year) => (
                                  <SelectItem key={String(year)}>
                                    {String(year)}
                                  </SelectItem>
                                ))}
                              </Select>
                            </Form>
                            {calendarYear && (
                              <div className="space-x-2">
                                <Button
                                  size="sm"
                                  color="secondary"
                                  variant="flat"
                                  onPress={() =>
                                    getRatingsReport(student, "pdf")
                                  }
                                >
                                  PDF
                                </Button>
                                <Button
                                  size="sm"
                                  color="secondary"
                                  variant="flat"
                                  onPress={() =>
                                    getRatingsReport(student, "excel")
                                  }
                                >
                                  Excel
                                </Button>
                              </div>
                            )}
                          </div>
                        </PopoverContent>
                      </Popover>
                      <Popover placement="right" backdrop="blur">
                        <PopoverTrigger>
                          <Button size="sm" color="success" variant="flat">
                            Progress Report
                          </Button>
                        </PopoverTrigger>
                        <PopoverContent>
                          <div className="px-1 py-2">
                            <div className="space-x-2">
                              <Button
                                size="sm"
                                color="success"
                                variant="flat"
                                onPress={() =>
                                  getProgressReport(student, "pdf")
                                }
                              >
                                PDF
                              </Button>
                              <Button
                                size="sm"
                                color="success"
                                variant="flat"
                                onPress={() =>
                                  getProgressReport(student, "excel")
                                }
                              >
                                Excel
                              </Button>
                            </div>
                          </div>
                        </PopoverContent>
                      </Popover>
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
