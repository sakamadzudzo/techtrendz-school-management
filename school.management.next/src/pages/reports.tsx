import React, { useCallback, useState } from "react";
import { Form, Card, CardFooter, Button, Tooltip, Divider } from "@heroui/react";
import { ArrowClockwiseIcon, ClipboardIcon } from "@phosphor-icons/react";
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

  const fetchReport = useCallback(async () => {
    if (!validate()) {
      return;
    }

    try {
      if (reportType?.function) {
        const reportResult = await reportType.function();
        if (reportResult?.data) {
          setReportData(reportResult.data);
        }
      } else {
        const res = await fetch(reportType?.endpoint || '');
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        const data = await res.arrayBuffer();
        setReportData(data);
      }
    } catch (error) {
      setErrorMessage("Failed to fetch report data");
      setStatusCode(500);
    }
  }, [validate, reportType, setReportData, setErrorMessage, setStatusCode]);

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

  return (
    <Wrapper hasData={true} error={errorMessage} statusCode={statusCode}>
      <EditFormWrapper
        isNew={true}
        newMsg={`Select report type and filters, then fetch`}
        editMsg=""
      >
        <Form
          onSubmit={(e) => e.preventDefault}
          className="h-full w-full md:w-1/2 pb-2 flex flex-col overflow-auto"
        >
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
                  <Tooltip>
                    <Button
                      color="primary"
                      className="button !w-fit"
                      startContent={<ClipboardIcon weight="thin" size={25} />}
                      onPress={fetchReport}
                    >
                      Fetch Report
                    </Button>
                  </Tooltip>
                )}
              </CardFooter>
            </Card>
          )}
        </Form>
      </EditFormWrapper>
    </Wrapper>
  );
}
