import React from "react";
import { Card, CardBody, CardHeader, Divider } from "@heroui/react";
import { ReportType, ReportFilter, Errors, AvailableFilters } from "@/utils/types";
import { FilterDropdown } from "./FilterDropdown";

interface ReportFiltersProps {
  reportType: ReportType | null;
  reportFilter: ReportFilter;
  errors: Errors<ReportFilter, string>;
  dropdowns: any;
  onFilterChange: (key: keyof ReportFilter, value: number) => void;
  onValidate: () => void;
}

export const ReportFilters: React.FC<ReportFiltersProps> = ({
  reportType,
  reportFilter,
  errors,
  dropdowns,
  onFilterChange,
  onValidate,
}) => {
  if (!reportType) {
    return (
      <Card className="w-full">
        <CardBody className="flex justify-center items-center">
          Please first select report type above...
        </CardBody>
      </Card>
    );
  }

  const availableFilters = reportType.availableFilters;

  return (
    <Card className="w-full">
      <CardHeader>Enter your filter data</CardHeader>
      <Divider />
      <CardBody
        aria-label="Report dropdown filters"
        className="space-y-2"
      >
        {!dropdowns ? (
          <div className="flex justify-center items-center">
            Please fetch filter data
          </div>
        ) : (
          <>
            {availableFilters?.students && (
              <FilterDropdown
                label="Student"
                placeholder="Select student..."
                type="studentId"
                options={dropdowns?.students || []}
                value={reportFilter.studentId}
                error={errors.studentId}
                isRequired
                onSelectionChange={(value) => onFilterChange("studentId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.teachers && (
              <FilterDropdown
                label="Teacher"
                placeholder="Select teacher..."
                type="teacherId"
                options={dropdowns?.teachers || []}
                value={reportFilter.teacherId}
                error={errors.teacherId}
                isRequired
                onSelectionChange={(value) => onFilterChange("teacherId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.courseworks && (
              <FilterDropdown
                label="Coursework"
                placeholder="Select coursework..."
                type="courseworkId"
                options={dropdowns?.courseworks || []}
                value={reportFilter.courseworkId}
                error={errors.courseworkId}
                isRequired
                onSelectionChange={(value) => onFilterChange("courseworkId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.classrooms && (
              <FilterDropdown
                label="Classroom"
                placeholder="Select classroom..."
                type="classroomId"
                options={dropdowns?.classrooms || []}
                value={reportFilter.classroomId}
                error={errors.classroomId}
                isRequired
                onSelectionChange={(value) => onFilterChange("classroomId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.subjects && (
              <FilterDropdown
                label="Subject"
                placeholder="Select subject..."
                type="subjectId"
                options={dropdowns?.subjects || []}
                value={reportFilter.subjectId}
                error={errors.subjectId}
                isRequired
                onSelectionChange={(value) => onFilterChange("subjectId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.extraCurriculars && (
              <FilterDropdown
                label="Extra Curricular"
                placeholder="Select extra curricular..."
                type="extraCurricularId"
                options={dropdowns?.extraCurriculars || []}
                value={reportFilter.extraCurricularId}
                error={errors.extraCurricularId}
                isRequired
                onSelectionChange={(value) => onFilterChange("extraCurricularId", value)}
                onValidate={onValidate}
              />
            )}

            {availableFilters?.calendarYears && (
              <FilterDropdown
                label="Year"
                placeholder="Select year..."
                type="year"
                options={dropdowns?.calendarYears || []}
                value={reportFilter.year}
                error={errors.year}
                isRequired
                onSelectionChange={(value) => onFilterChange("year", value)}
                onValidate={onValidate}
              />
            )}
          </>
        )}
      </CardBody>
      <Divider />
    </Card>
  );
};