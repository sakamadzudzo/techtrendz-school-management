import React from "react";
import {
  Autocomplete,
  AutocompleteItem,
  Select,
  SelectItem,
} from "@heroui/react";
import {
  StudentIcon,
  ChalkboardTeacherIcon,
  ScrollIcon,
  ChalkboardIcon,
  SubtitlesIcon,
  StrategyIcon,
  CalendarIcon,
} from "@phosphor-icons/react";
import { Errors, ReportFilter } from "@/utils/types";

interface DropdownOption {
  id: number;
  name: string;
  subject?: { name: string };
  type?: string;
}

interface FilterDropdownProps {
  label: string;
  placeholder: string;
  type: keyof ReportFilter;
  options: DropdownOption[] | number[];
  value?: string | number;
  error?: string;
  isRequired?: boolean;
  isVirtualized?: boolean;
  onSelectionChange: (value: number) => void;
  onValidate?: () => void;
}

const iconMap = {
  studentId: <StudentIcon weight="thin" size={18} />,
  teacherId: <ChalkboardTeacherIcon weight="thin" size={18} />,
  courseworkId: <ScrollIcon weight="thin" size={18} />,
  classroomId: <ChalkboardIcon weight="thin" size={18} />,
  subjectId: <SubtitlesIcon weight="thin" size={18} />,
  extraCurricularId: <StrategyIcon weight="thin" size={18} />,
  year: <CalendarIcon weight="thin" size={18} />,
  type: <ScrollIcon weight="thin" size={18} />,
};

export const FilterDropdown: React.FC<FilterDropdownProps> = ({
  label,
  placeholder,
  type,
  options,
  value,
  error,
  isRequired = false,
  isVirtualized = true,
  onSelectionChange,
  onValidate,
}) => {
  const handleSelectionChange = (key: string | number | null) => {
    if (key !== null) {
      const numericValue = Number(key);
      onSelectionChange(numericValue);
      onValidate?.();
    }
  };

  const renderOption = (option: any) => {
    if (typeof option === "number") {
      return <AutocompleteItem key={String(option)}>{option}</AutocompleteItem>;
    }

    const { id, name, subject, type: optionType } = option;

    if (type === "courseworkId" && subject) {
      return (
        <AutocompleteItem key={id} description={subject.name}>
          {optionType || name}
        </AutocompleteItem>
      );
    }

    return <AutocompleteItem key={id}>{name}</AutocompleteItem>;
  };

  if (type === "year") {
    return (
      <Select
        key={`${type}-dropdown`}
        variant="bordered"
        label={label}
        placeholder={placeholder}
        startContent={iconMap[type]}
        selectedKeys={value ? [String(value)] : []}
        errorMessage={error}
        isInvalid={!!error}
        isRequired={isRequired}
        isVirtualized={isVirtualized}
        listboxProps={{
          emptyContent: "No data available.",
        }}
        onSelectionChange={(key) =>
          handleSelectionChange(key.currentKey || null)
        }
      >
        {options.map((year) => {
          return <SelectItem key={String(year)}>{String(year)}</SelectItem>;
        })}
      </Select>
    );
  }

  return (
    <Autocomplete
      key={`${type}-dropdown`}
      variant="bordered"
      label={label}
      placeholder={placeholder}
      startContent={iconMap[type]}
      isInvalid={!!error}
      errorMessage={error}
      selectedKey={String(value || "")}
      defaultItems={options as any}
      isRequired={isRequired}
      isVirtualized={isVirtualized}
      onSelectionChange={handleSelectionChange}
      listboxProps={{
        emptyContent: "No data available.",
      }}
    >
      {renderOption}
    </Autocomplete>
  );
};
