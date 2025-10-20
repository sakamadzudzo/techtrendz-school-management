import React, { useCallback, useState } from "react";
import {
  Form,
  Card,
  CardFooter,
  Button,
  Tooltip,
  Divider,
} from "@heroui/react";
import {
  ArrowClockwiseIcon,
  ArrowCounterClockwiseIcon,
  ClipboardIcon,
} from "@phosphor-icons/react";
import EditFormWrapper from "@/components/EditFormWrapper";
import { Wrapper } from "@/components/Wrapper";
import { ReportSelector } from "@/components/ReportSelector";
import { ReportFilters } from "@/components/ReportFilters";
import {
  useGenerateMarksReport,
  useGenerateRatingsReport,
  useGenerateTeacherReport,
  useGenerateProgressReport,
} from "@/hooks/useReports";
import { useReportsDropdownsSelective } from "@/hooks/useReportsDropdowns";
import { useReportsState } from "@/hooks/useReportsState";
import InlineSuccess from "@/components/InlineSuccess";
import InlineError from "@/components/InlineError";

export default function Reports({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  // Get all the report generation functions
  const {
    generateMarksByStudentClassroomYearReport,
    generateMarksByStudentSubjectYearReport,
    generateMarksByStudentYearReport,
    generateMarksByStudentReport,
    generateMarksByYearReport,
    generateMarksByClassroomYearReport,
    generateMarksBySubjectYearReport,
  } = useGenerateMarksReport();

  const {
    generateRatingsByStudentActivityYearReport,
    generateRatingsByStudentYearReport,
    generateRatingsByActivityYearReport,
    generateRatingsByActivityReport,
  } = useGenerateRatingsReport();

  const {
    generateMarksByTeacherYearReport,
    generateRatingsByTeacherYearReport,
  } = useGenerateTeacherReport();

  const {
    generateProgressReportByStudent,
    generateProgressReportByTeacher,
    generateProgressReportBySubject,
    generateProgressReportByClassroom,
    generateProgressReportByActivity,
    generateProgressReportByFacilitator,
  } = useGenerateProgressReport();

  // State setters for the custom hook
  const [reportData, setReportData] = useState<ArrayBuffer | null>(null);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [statusCode, setStatusCode] = useState<number | undefined>();

  // Use the custom hook for state management
  const {
    reportType,
    reportFilter,
    errors,
    reportTypes,
    hasEnoughFilters,
    isValid,
    resetStates,
    validate,
    handleReportTypeChange,
    handleFilterChange,
  } = useReportsState(
    generateMarksByClassroomYearReport,
    generateMarksByStudentReport,
    generateMarksByStudentClassroomYearReport,
    generateMarksByStudentSubjectYearReport,
    generateMarksByStudentYearReport,
    generateMarksByYearReport,
    generateMarksBySubjectYearReport,
    generateRatingsByStudentActivityYearReport,
    generateRatingsByStudentYearReport,
    generateRatingsByActivityYearReport,
    generateRatingsByActivityReport,
    generateMarksByTeacherYearReport,
    generateRatingsByTeacherYearReport,
    generateProgressReportByStudent,
    generateProgressReportByTeacher,
    generateProgressReportBySubject,
    generateProgressReportByClassroom,
    generateProgressReportByActivity,
    generateProgressReportByFacilitator,
    incrementLoading,
    decrementLoading,
    setReportData,
    setErrorMessage,
    setStatusCode
  );

  // Get dropdown data
  const { dropdowns, isError, isLoading, mutate } =
    useReportsDropdownsSelective(
      reportType?.availableFilters || {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
      incrementLoading,
      decrementLoading
    );

  const fetchDropdowns = useCallback(async () => {
    setErrorMessage("");
    setStatusCode(undefined);
    try {
      mutate();
      if (isError) {
        setErrorMessage(isError.error);
        setStatusCode(isError.statusCode);
      }
    } catch (error) {
      setErrorMessage("Failed to fetch dropdown data");
      setStatusCode(500);
    }
  }, [mutate, isError, setErrorMessage, setStatusCode]);

  const fetchReport = useCallback(
    async (type: "pdf" | "excel") => {
      setErrorMessage("");
      setStatusCode(undefined);
      if (!validate()) {
        return;
      }

      try {
        let reportResult;
        if (reportType?.function) {
          let tempFilter = reportFilter;
          tempFilter.type = type;
          reportResult = await reportType.function(tempFilter);

          if (reportResult?.statusCode === 204) {
            setErrorMessage("No Content");
            setStatusCode(204);
            return;
          }

          if (reportResult?.data) {
            setReportData(reportResult.data);
          }
        } else {
          const res = await fetch(reportType?.endpoint || "");
          if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
          }
          const data = await res.arrayBuffer();
          setReportData(data);
          reportResult = { data };
        }

        // Handle Excel file download
        if (type === "excel" && !errorMessage && reportResult?.data) {
          const blob = new Blob([reportResult.data], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          const date = new Date();
          const dateString = `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`;
          const url = URL.createObjectURL(blob);
          const link = document.createElement("a");
          link.href = url;
          link.download = `${reportType?.title} - ${dateString}.xlsx`;
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          URL.revokeObjectURL(url);
        }
      } catch (error) {
        setErrorMessage("Failed to fetch report data");
        setStatusCode(500);
      }
    },
    [
      validate,
      reportType,
      reportFilter,
      setReportData,
      setErrorMessage,
      setStatusCode,
    ]
  );

  const hasFilterData = React.useMemo(() => {
    if (
      !dropdowns?.students &&
      !dropdowns?.teachers &&
      !dropdowns?.courseworks &&
      !dropdowns?.classrooms &&
      !dropdowns?.subjects &&
      !dropdowns?.extraCurriculars &&
      !dropdowns?.calendarYears
    ) {
      return false;
    }
    return true;
  }, [dropdowns]);

  const hasSelections = React.useMemo(() => {
    if (!!reportType) {
      return true;
    }
    return false;
  }, [reportType]);

  return (
    <Wrapper hasData={true} error={""} statusCode={statusCode}>
      <EditFormWrapper
        isNew={true}
        newMsg={`Select report type and filters, then fetch`}
        editMsg=""
      >
        <Form
          onSubmit={(e) => e.preventDefault}
          className="h-full w-full md:w-1/2 pb-2 flex flex-col overflow-auto"
        >
          {errorMessage && (
            <InlineError title={errorMessage} statusCode={statusCode} />
          )}
          <ReportSelector
            reportTypes={reportTypes}
            selectedReportType={reportType}
            onReportTypeChange={handleReportTypeChange}
          />

          {reportType && (
            <Card className="w-full">
              <ReportFilters
                reportType={reportType}
                reportFilter={reportFilter}
                errors={errors}
                dropdowns={dropdowns}
                onFilterChange={handleFilterChange}
                onValidate={validate}
              />

              <CardFooter className="flex gap-2 justify-end">
                {hasSelections && (
                  <Tooltip>
                    <Button
                      color="primary"
                      className="button !w-fit"
                      startContent={
                        <ArrowCounterClockwiseIcon weight="thin" size={25} />
                      }
                      onPress={resetStates}
                    >
                      Reset
                    </Button>
                  </Tooltip>
                )}
                <Tooltip>
                  <Button
                    color="primary"
                    className="button !w-fit"
                    startContent={
                      <ArrowClockwiseIcon weight="thin" size={25} />
                    }
                    onPress={fetchDropdowns}
                  >
                    {hasFilterData
                      ? "Refetch Filter Data"
                      : "Fetch Filter Data"}
                  </Button>
                </Tooltip>
                {hasEnoughFilters && isValid && (
                  <>
                    <Tooltip>
                      <Button
                        color="primary"
                        className="button !w-fit"
                        startContent={<ClipboardIcon weight="thin" size={25} />}
                        onPress={() => fetchReport("pdf")}
                      >
                        Fetch PDF
                      </Button>
                    </Tooltip>
                    <Tooltip>
                      <Button
                        color="primary"
                        className="button !w-fit"
                        startContent={<ClipboardIcon weight="thin" size={25} />}
                        onPress={() => fetchReport("excel")}
                      >
                        Fetch Excel
                      </Button>
                    </Tooltip>
                  </>
                )}
              </CardFooter>
            </Card>
          )}
        </Form>
      </EditFormWrapper>
    </Wrapper>
  );
}
