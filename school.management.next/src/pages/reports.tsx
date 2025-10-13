import EditFormWrapper from "@/components/EditFormWrapper";
import { Wrapper } from "@/components/Wrapper";
import {
  useGenerateMarksReport,
  useGenerateRatingsReport,
  useGenerateTeacherReport,
  useGenerateProgressReport,
} from "@/hooks/useReports";
import { useReportsDropdownsSelective } from "@/hooks/useReportsDropdowns";
import {
  AvailableFilters,
  Errors,
  ReportFilter,
  ReportType,
} from "@/utils/types";
import {
  Select,
  SelectItem,
  Form,
  Card,
  CardHeader,
  CardFooter,
  Button,
  CardBody,
  Tooltip,
  Divider,
  Autocomplete,
  AutocompleteItem,
} from "@heroui/react";
import {
  ArrowClockwiseIcon,
  CalendarIcon,
  ChalkboardIcon,
  ChalkboardTeacherIcon,
  ClipboardIcon,
  ScrollIcon,
  StrategyIcon,
  StudentIcon,
  SubtitlesIcon,
} from "@phosphor-icons/react";
import React from "react";

export default function Reports({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const availableFilters: AvailableFilters = {
    students: false,
    teachers: false,
    courseworks: false,
    classrooms: false,
    subjects: false,
    extraCurriculars: false,
    calendarYears: false,
  };
  const [reportData, setReportData] = React.useState<ArrayBuffer | null>(null);
  const [errorMessage, setErrorMessage] = React.useState<string>("");
  const [statusCode, setStatusCode] = React.useState<number | undefined>();
  const [reportType, setReportType] = React.useState<ReportType>({
    availableFilters,
  } as ReportType);
  const [reportFilter, setReportFiler] = React.useState<ReportFilter>(
    {} as ReportFilter
  );
  const [errors, setErrors] = React.useState<Errors<ReportFilter, string>>(
    {} as Errors<ReportFilter, string>
  );

  const { dropdowns, isError, isLoading, mutate } =
    useReportsDropdownsSelective(
      reportType.availableFilters,
      incrementLoading,
      decrementLoading
    );

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

  const resetStates = () => {
    setReportData(null);
    setErrorMessage("");
    setStatusCode(undefined);
    setReportType({ availableFilters } as ReportType);
    setReportFiler({} as ReportFilter);
  };

  const reportTypes: ReportType[] = [
    {
      title: "Marks By Classroom Year",
      endpoint: "marks_by_classroom_year",
      index: 1,
      function: () =>
        generateMarksByClassroomYearReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student",
      endpoint: "marks_by_student",
      index: 2,
      function: () =>
        generateMarksByStudentReport(
          reportFilter,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Marks By Student Classroom Year",
      endpoint: "marks_student_classroom_year",
      index: 3,
      function: () =>
        generateMarksByStudentClassroomYearReport(
          reportFilter.year!,
          reportFilter.classroomId,
          reportFilter.studentId,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student Subject Year",
      endpoint: "marks_student_subject_year",
      index: 4,
      function: () =>
        generateMarksByStudentSubjectYearReport(
          reportFilter.year!,
          reportFilter.subjectId!,
          reportFilter.studentId,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Student Year",
      endpoint: "marks_student_year",
      index: 5,
      function: () =>
        generateMarksByStudentYearReport(
          reportFilter.year!,
          reportFilter.studentId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Year",
      endpoint: "marks_year",
      index: 6,
      function: () =>
        generateMarksByYearReport(
          reportFilter.year!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Marks By Subject Year",
      endpoint: "marks_subject_year",
      index: 7,
      function: () =>
        generateMarksBySubjectYearReport(
          reportFilter.year!,
          reportFilter.subjectId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Student Activity Year",
      endpoint: "ratings_student_activity_year",
      index: 8,
      function: () =>
        generateRatingsByStudentActivityYearReport(
          reportFilter.year!,
          reportFilter.extraCurricularId!,
          reportFilter.studentId,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Student Year",
      endpoint: "ratings_student_year",
      index: 9,
      function: () =>
        generateRatingsByStudentYearReport(
          reportFilter.year!,
          reportFilter.studentId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Activity Year",
      endpoint: "ratings_activity_year",
      index: 10,
      function: () =>
        generateRatingsByActivityYearReport(
          reportFilter.year!,
          reportFilter.extraCurricularId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Activity",
      endpoint: "ratings_activity",
      index: 11,
      function: () =>
        generateRatingsByActivityReport(
          reportFilter.extraCurricularId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      },
    },
    {
      title: "Marks By Teacher Year",
      endpoint: "marks_teacher_year",
      index: 12,
      function: () =>
        generateMarksByTeacherYearReport(
          reportFilter.year!,
          reportFilter.teacherId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Ratings By Teacher Year",
      endpoint: "ratings_teacher_year",
      index: 13,
      function: () =>
        generateRatingsByTeacherYearReport(
          reportFilter.year!,
          reportFilter.teacherId!,
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      },
    },
    {
      title: "Progress By Student",
      endpoint: "progress_student",
      index: 14,
      function: () =>
        generateProgressReportByStudent(
          reportFilter.studentId!,
          "student",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Teacher",
      endpoint: "progress_teacher",
      index: 15,
      function: () =>
        generateProgressReportByTeacher(
          reportFilter.teacherId!,
          "teacher",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Subject",
      endpoint: "progress_subject",
      index: 16,
      function: () =>
        generateProgressReportBySubject(
          reportFilter.subjectId!,
          "subject",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Classroom",
      endpoint: "progress_classroom",
      index: 17,
      function: () =>
        generateProgressReportByClassroom(
          reportFilter.classroomId!,
          "classroom",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Activity",
      endpoint: "progress_activity",
      index: 18,
      function: () =>
        generateProgressReportByActivity(
          reportFilter.extraCurricularId!,
          "activity",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      },
    },
    {
      title: "Progress By Facilitator",
      endpoint: "progress_facilitator",
      index: 19,
      function: () =>
        generateProgressReportByFacilitator(
          reportFilter.teacherId!,
          "facilitator",
          incrementLoading,
          decrementLoading,
          setReportData,
          (error, statusCode) => {
            setErrorMessage(error);
            setStatusCode(statusCode);
          }
        ),
      availableFilters: {
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      },
    },
  ];

  const validate = () => {
    setErrors({} as Errors<ReportFilter, string>);
    let tempErrors: Errors<ReportFilter, string> = {} as Errors<
      ReportFilter,
      string
    >;

    if (reportType?.availableFilters?.students && !reportFilter?.studentId) {
      tempErrors = {
        ...tempErrors,
        studentId: "Student is required for this report",
      };
    }
    if (reportType?.availableFilters?.teachers && !reportFilter?.teacherId) {
      tempErrors = {
        ...tempErrors,
        teacherId: "Teacher is required for this report",
      };
    }
    if (
      reportType?.availableFilters?.courseworks &&
      !reportFilter?.courseworkId
    ) {
      tempErrors = {
        ...tempErrors,
        courseworkId: "Coursework is required for this report",
      };
    }
    if (
      reportType?.availableFilters?.classrooms &&
      !reportFilter?.classroomId
    ) {
      tempErrors = {
        ...tempErrors,
        classroomId: "Classroom is required for this report",
      };
    }
    if (reportType?.availableFilters?.subjects && !reportFilter?.subjectId) {
      tempErrors = {
        ...tempErrors,
        subjectId: "Subject is required for this report",
      };
    }
    if (
      reportType?.availableFilters?.extraCurriculars &&
      !reportFilter?.extraCurricularId
    ) {
      tempErrors = {
        ...tempErrors,
        extraCurricularId:
          "Extra curricular activity is required for this report",
      };
    }
    if (reportType?.availableFilters?.calendarYears && !reportFilter?.year) {
      tempErrors = { ...tempErrors, year: "Year is required for this report" };
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const fetchDropdowns = async () => {
    mutate();
    if (isError) {
      setErrorMessage(isError.error);
      setStatusCode(isError.statusCode);
    }
  };

  const fetchReport = async () => {
    if (!validate()) {
      return;
    }

    let reportResult;

    // switch (reportType.index) {
    //   case 1:
    //     reportResult = await generateMarksByClassroomYearReport(
    //       reportFilter,
    //       incrementLoading,
    //       decrementLoading,
    //       setReportData,
    //       (error, statusCode) => {
    //         setErrorMessage(error);
    //         setStatusCode(statusCode);
    //       }
    //     );
    //     break;
    //   case 2:
    //     reportResult = await generateMarksByStudentReport(
    //       reportFilter,
    //       incrementLoading,
    //       decrementLoading,
    //       setReportData,
    //       (error, statusCode) => {
    //         setErrorMessage(error);
    //         setStatusCode(statusCode);
    //       }
    //     );
    //     break;
    //   default:
    //     const res = await fetch(reportType.endpoint);
    //     const data = await res.arrayBuffer();
    //     setReportData(data);
    //     return;
    // }
    if (reportType.function) {
      reportResult = await reportType.function();
      if (reportResult?.data) {
        setReportData(reportResult.data);
      }
    } else {
      const res = await fetch(reportType.endpoint);
      const data = await res.arrayBuffer();
      setReportData(data);
    }
  };

  const hasEnoughFilters = React.useMemo(() => {
    if (
      JSON.stringify(reportFilter) === JSON.stringify({}) ||
      JSON.stringify(reportType) === JSON.stringify({})
    ) {
      return false;
    }

    const { availableFilters } = reportType;

    // Define filter requirements
    const requirements = [
      { needs: availableFilters.students, has: !!reportFilter.studentId },
      { needs: availableFilters.teachers, has: !!reportFilter.teacherId },
      { needs: availableFilters.courseworks, has: !!reportFilter.courseworkId },
      { needs: availableFilters.classrooms, has: !!reportFilter.classroomId },
      { needs: availableFilters.subjects, has: !!reportFilter.subjectId },
      {
        needs: availableFilters.extraCurriculars,
        has: !!reportFilter.extraCurricularId,
      },
      { needs: availableFilters.calendarYears, has: !!reportFilter.year },
    ];

    return requirements.every(({ needs, has }) => !needs || has);
  }, [reportFilter, reportType]);

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
          <Select
            label="Report"
            selectedKeys={[String(reportType?.index || "")]}
            variant="bordered"
            selectionMode="single"
            placeholder="Select report..."
            className="w-96"
            onSelectionChange={(key) =>
              setReportType(
                reportTypes?.find(
                  (type) => Number(type.index) === Number(key.currentKey)
                ) || ({} as ReportType)
              )
            }
          >
            {reportTypes?.map((type) => (
              <SelectItem key={type.index}>{type.title}</SelectItem>
            ))}
          </Select>
          {reportType && (
            <Card className="w-full">
              <CardHeader>Enter your filter data</CardHeader>
              <Divider />
              <CardBody
                aria-label="Report dropdown filters"
                className="space-y-2"
              >
                {dropdowns ? (
                  <>
                    {reportType?.availableFilters?.students && (
                      <Autocomplete
                        key="student-dropdown"
                        variant="bordered"
                        label="Student"
                        placeholder="Select student..."
                        startContent={<StudentIcon weight="thin" size={18} />}
                        isInvalid={!!errors.studentId}
                        errorMessage={errors.studentId}
                        selectedKey={String(reportFilter.studentId)}
                        defaultItems={dropdowns?.students || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            studentId: Number(key),
                          });
                          console.log(Number(key));
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(student) => (
                          <AutocompleteItem key={student.id}>
                            {student.name}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.teachers && (
                      <Autocomplete
                        key="teacher-dropdown"
                        variant="bordered"
                        label="Teacher"
                        placeholder="Select teacher..."
                        startContent={
                          <ChalkboardTeacherIcon weight="thin" size={18} />
                        }
                        isInvalid={!!errors.teacherId}
                        errorMessage={errors.teacherId}
                        selectedKey={reportFilter.teacherId}
                        defaultItems={dropdowns?.teachers || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            teacherId: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(teacher) => (
                          <AutocompleteItem key={teacher.id}>
                            {teacher.name}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.courseworks && (
                      <Autocomplete
                        key="coursework-dropdown"
                        variant="bordered"
                        label="Coursework"
                        placeholder="Select coursework..."
                        startContent={<ScrollIcon weight="thin" size={18} />}
                        isInvalid={!!errors.courseworkId}
                        errorMessage={errors.courseworkId}
                        selectedKey={reportFilter.courseworkId}
                        defaultItems={dropdowns?.courseworks || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            courseworkId: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(coursework) => (
                          <AutocompleteItem
                            key={coursework.id}
                            description={coursework.subject.name}
                          >
                            {coursework.type}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.classrooms && (
                      <Autocomplete
                        key="classroom-dropdown"
                        variant="bordered"
                        label="Classroom"
                        placeholder="Select classroom..."
                        startContent={
                          <ChalkboardIcon weight="thin" size={18} />
                        }
                        isInvalid={!!errors.classroomId}
                        errorMessage={errors.classroomId}
                        selectedKey={reportFilter.classroomId}
                        defaultItems={dropdowns?.classrooms || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            classroomId: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(classroom) => (
                          <AutocompleteItem key={classroom.id}>
                            {classroom.name}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.subjects && (
                      <Autocomplete
                        key="subject-dropdown"
                        variant="bordered"
                        label="Subject"
                        placeholder="Select subject..."
                        startContent={<SubtitlesIcon weight="thin" size={18} />}
                        isInvalid={!!errors.subjectId}
                        errorMessage={errors.subjectId}
                        selectedKey={reportFilter.subjectId}
                        defaultItems={dropdowns?.subjects || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            subjectId: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(subject) => (
                          <AutocompleteItem key={subject.id}>
                            {subject.name}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.extraCurriculars && (
                      <Autocomplete
                        key="extra-curricular-dropdown"
                        variant="bordered"
                        label="Extra Curricular"
                        placeholder="Select extra curricular..."
                        startContent={<StrategyIcon weight="thin" size={18} />}
                        isInvalid={!!errors.extraCurricularId}
                        errorMessage={errors.extraCurricularId}
                        selectedKey={reportFilter.extraCurricularId}
                        defaultItems={dropdowns?.extraCurriculars || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            extraCurricularId: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(activity) => (
                          <AutocompleteItem key={activity.id}>
                            {activity.name}
                          </AutocompleteItem>
                        )}
                      </Autocomplete>
                    )}

                    {reportType?.availableFilters?.calendarYears && (
                      <Autocomplete
                        key="year-dropdown"
                        variant="bordered"
                        label="Year"
                        placeholder="Select year..."
                        startContent={<CalendarIcon weight="thin" size={18} />}
                        isInvalid={!!errors.year}
                        errorMessage={errors.year}
                        selectedKey={reportFilter.year}
                        // defaultItems={dropdowns?.calendarYears || []}
                        isRequired
                        isVirtualized={true}
                        onSelectionChange={(key) => {
                          setReportFiler({
                            ...reportFilter,
                            year: Number(key),
                          });
                          validate();
                        }}
                        listboxProps={{
                          emptyContent: "No data available.",
                        }}
                      >
                        {(dropdowns?.calendarYears || ([] as number[])).map(
                          (year) => (
                            <AutocompleteItem key={year}>
                              {year}
                            </AutocompleteItem>
                          )
                        )}
                      </Autocomplete>
                    )}
                  </>
                ) : (
                  <div className="flex justify-center items-center">
                    Please fetch fitler data
                  </div>
                )}
                {!reportType?.index && (
                  <div className="flex justify-center items-center">
                    Please first select report type above...
                  </div>
                )}
              </CardBody>
              <Divider />
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
                {hasEnoughFilters && validate() && (
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
