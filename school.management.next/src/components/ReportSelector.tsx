import React from "react";
import { Select, SelectItem } from "@heroui/react";
import { ReportType } from "@/utils/types";

interface ReportSelectorProps {
  reportTypes: ReportType[];
  selectedReportType: ReportType | null;
  onReportTypeChange: (reportType: ReportType) => void;
}

export const ReportSelector: React.FC<ReportSelectorProps> = ({
  reportTypes,
  selectedReportType,
  onReportTypeChange,
}) => {
  const handleSelectionChange = (key: string) => {
    const selectedReportType = reportTypes.find(
      (type) => String(type.index) === key
    );
    if (selectedReportType) {
      onReportTypeChange(selectedReportType);
    }
  };

  return (
    <Select
      label="Report"
      selectedKeys={[String(selectedReportType?.index || "")]}
      variant="bordered"
      selectionMode="single"
      placeholder="Select report..."
      className="w-96"
      onSelectionChange={(keys) => {
        const key = Array.from(keys)[0] as string;
        handleSelectionChange(key);
      }}
    >
      {reportTypes.map((type) => (
        <SelectItem key={type.index}>{type.title}</SelectItem>
      ))}
    </Select>
  );
};