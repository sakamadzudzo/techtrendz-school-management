import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useStudentById, useSaveStudent } from "@/hooks/useStudents";
import { RootState } from "@/store/store";
import { Errors, Student } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  SelectItem,
  Select,
  Autocomplete,
  AutocompleteItem,
  DateRangePicker,
  SharedSelection,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { FloppyDiskIcon, StudentIcon } from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import {
  emailError,
  emailRegex,
  phoneNumberRegex,
  studentViewToStudent,
} from "@/utils/helpers";
import { useClassroomYearsDropdown } from "@/hooks/useClassroomYears";
import { useDormYearsDropdown } from "@/hooks/useDormYears";
import { useSchoolsList } from "@/hooks/useSchools";

export default function StudentEdit({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const [error, setError] = React.useState<string | null>(null);
  const [errors, setErrors] = React.useState<Errors<Student, string>>(
    {} as Errors<Student, string>
  );
  const [statusCode, setStatusCode] = React.useState(0);
  const [student, setStudent] = React.useState<Student>({} as Student);
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { student: oldStudent, isLoading, isError } = useStudentById(id!);
  const {
    classroomYears,
    isLoading: classroomYearsLoading,
    isError: classroomYearsError,
  } = useClassroomYearsDropdown();
  const {
    dormYears,
    isLoading: dormYearsLoading,
    isError: dormYearsError,
  } = useDormYearsDropdown();
  const {
    schools,
    isLoading: schoolsLoading,
    isError: schoolsErorr,
  } = useSchoolsList({});
  const { saveStudent } = useSaveStudent();

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    classroomYearsError &&
      useToastError({
        error: classroomYearsError?.message,
        statusCode: classroomYearsError?.statusCode,
      });
  }, [classroomYearsError]);

  useEffect(() => {
    dormYearsError &&
      useToastError({
        error: dormYearsError?.message,
        statusCode: dormYearsError?.statusCode,
      });
  }, [dormYearsError]);

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <StudentIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing student:
      </div>
      <div className="flex justify-center">
        Edit information about the student
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <StudentIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating student:
      </div>
      <div className="flex justify-center">
        Enter information about the student
      </div>
    </div>
  );

  useEffect(() => {
    if (oldStudent) setStudent(studentViewToStudent(oldStudent));
  }, [id, oldStudent]);

  const validate = () => {
    setErrors({} as Errors<Student, string>);
    let tempErrors: Errors<Student, string> = {} as Errors<Student, string>;

    if (!student?.startYear) {
      tempErrors = { ...tempErrors, startYear: "Start year is required" };
    }
    if (!student?.endYear) {
      tempErrors = { ...tempErrors, endYear: "End year is required" };
    }

    if (!student?.person?.salutation) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, salutation: "Salutation is required" },
      };
    }
    if (!student?.person?.firstName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, firstName: "First name is required" },
      };
    }
    if (!student?.person?.lastName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, lastName: "Last name is required" },
      };
    }
    if (!student?.person?.email) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, email: "Email is required" },
      };
    } else {
      if (!emailRegex.test(student?.person?.email)) {
        tempErrors = {
          ...tempErrors,
          person: { ...tempErrors?.person, email: emailError },
        };
      }
    }
    if (!student?.person?.dateOfBirth) {
      tempErrors = {
        ...tempErrors,
        person: {
          ...tempErrors?.person,
          dateOfBirth: "Date of birth is required",
        },
      };
    }

    setErrors(tempErrors);
    return (
      Object.keys(tempErrors).length === 0 &&
      Object.keys(tempErrors?.person || {}).length === 0
    );
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);

    if (!validate()) {
      return;
    }

    await saveStudent(
      student,
      incrementLoading,
      decrementLoading,
      () => {
        if (student?.id) {
          router.push(`/student/one?id=${student.id}`);
        }
      }, // onSuccess callback with router.push
      (error, statusCode) => {
        setError(error);
        setStatusCode(statusCode);
        useToastError({ error, statusCode });
      } // onError callback
    );
  };

  const changeStudyDuration = (field: string, value: SharedSelection) => {
    if (field.toLowerCase() === "startyear") {
      setErrors({ ...errors, startYear: "" });
      if (Number(value.currentKey) > Number(student.endYear)) {
        setErrors({
          ...errors,
          startYear: "Start year must be earlier than end year",
        });
      } else {
        setStudent({ ...student, startYear: value.currentKey! });
      }
    }
    if (field.toLowerCase() === "endyear") {
      setErrors({ ...errors, endYear: "" });
      if (Number(value.currentKey) < Number(student.startYear)) {
        setErrors({
          ...errors,
          endYear: "End year must be later than start year",
        });
      } else {
        setStudent({ ...student, endYear: value.currentKey! });
      }
    }
  };

  const changeNumbers = (field: string, value: string) => {
    if (!phoneNumberRegex.test(value)) {
      setErrors({ ...errors, person: { ...errors.person, [field]: value } });
    } else {
      setStudent({
        ...student,
        person: { ...student.person, mobileNumber: value },
      });
    }
  };

  return (
    <EditFormWrapper editMsg={editMsg} newMsg={newMsg} isNew={!id}>
      <Form
        onSubmit={onSubmit}
        className="h-full w-full md:w-1/2 pb-2 flex flex-col"
      >
        {error && <InlineError statusCode={statusCode} title={error} />}
        <div className="space-y-4 h-full w-full py-2 overflow-y-auto grow">
          <Input
            label="Salutation"
            name="salutation"
            placeholder="Enter salutation"
            type="text"
            value={student.person?.salutation || ""}
            errorMessage={errors?.person?.salutation}
            isInvalid={!!errors?.person?.salutation}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, salutation: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="First Name"
            name="firstName"
            placeholder="Enter first name"
            type="text"
            value={student.person?.firstName || ""}
            errorMessage={errors?.person?.firstName}
            isInvalid={!!errors?.person?.firstName}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, firstName: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Other Names"
            name="otherNames"
            placeholder="Enter other names"
            type="text"
            value={student.person?.otherNames || ""}
            errorMessage={errors?.person?.otherNames}
            isInvalid={!!errors?.person?.otherNames}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, otherNames: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Last Name"
            name="lastName"
            placeholder="Enter last name"
            type="text"
            value={student.person?.lastName || ""}
            errorMessage={errors?.person?.lastName}
            isInvalid={!!errors?.person?.lastName}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, lastName: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Email"
            name="email"
            placeholder="Enter email"
            type="email"
            value={student.person?.email || ""}
            errorMessage={errors?.person?.email}
            isInvalid={!!errors?.person?.email}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, email: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Mobile Number"
            name="mobileNumber"
            placeholder="Enter mobile number"
            type="text"
            value={student.person?.mobileNumber || ""}
            errorMessage={errors?.person?.mobileNumber}
            isInvalid={!!errors?.person?.mobileNumber}
            onValueChange={(e) => changeNumbers("mobileNumber", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Mobile Number 2"
            name="mobileNumber2"
            placeholder="Enter secondary mobile number"
            type="text"
            value={student.person?.mobileNumber2 || ""}
            errorMessage={errors?.person?.mobileNumber2}
            isInvalid={!!errors?.person?.mobileNumber2}
            onValueChange={(e) => changeNumbers("mobileNumber2", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Tel Number"
            name="telNumber"
            placeholder="Enter telephone number"
            type="text"
            value={student.person?.telNumber || ""}
            errorMessage={errors?.person?.telNumber}
            isInvalid={!!errors?.person?.telNumber}
            onValueChange={(e) => changeNumbers("telNumber", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="WhatsApp"
            name="whatsapp"
            placeholder="Enter WhatsApp number"
            type="text"
            value={student.person?.whatsapp || ""}
            errorMessage={errors?.person?.whatsapp}
            isInvalid={!!errors?.person?.whatsapp}
            onValueChange={(e) => changeNumbers("whatsapp", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Facebook"
            name="facebook"
            placeholder="Enter Facebook profile"
            type="text"
            value={student.person?.facebook || ""}
            errorMessage={errors?.person?.facebook}
            isInvalid={!!errors?.person?.facebook}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, facebook: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="LinkedIn"
            name="linkedin"
            placeholder="Enter LinkedIn profile"
            type="text"
            value={student.person?.linkedin || ""}
            errorMessage={errors?.person?.linkedin}
            isInvalid={!!errors?.person?.linkedin}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, linkedin: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Date of Birth"
            name="dateOfBirth"
            placeholder="Enter date of birth"
            type="date"
            value={
              student.person?.dateOfBirth
                ? typeof student.person.dateOfBirth === "string"
                  ? student.person.dateOfBirth
                  : new Date(student.person.dateOfBirth)
                      .toISOString()
                      .substring(0, 10)
                : ""
            }
            errorMessage={errors?.person?.dateOfBirth}
            isInvalid={!!errors?.person?.dateOfBirth}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, dateOfBirth: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <div className="w-full flex gap-5">
            <Select
              label="Start Year"
              variant="bordered"
              selectedKeys={[String(student.startYear)]}
              errorMessage={errors.startYear}
              isInvalid={!!errors.startYear}
              onSelectionChange={(e) => changeStudyDuration("startYear", e)}
            >
              {[...Array(31).keys()].map((i) => {
                const year = (new Date().getFullYear() - 15 + i).toString();
                return <SelectItem key={year}>{year}</SelectItem>;
              })}
            </Select>
            <Select
              label="End Year"
              variant="bordered"
              selectedKeys={[String(student.endYear)]}
              errorMessage={errors.endYear}
              isInvalid={!!errors.endYear}
              onSelectionChange={(e) => changeStudyDuration("endYear", e)}
            >
              {[...Array(31).keys()].map((i) => {
                const year = (new Date().getFullYear() - 15 + i).toString();
                return <SelectItem key={year}>{year}</SelectItem>;
              })}
            </Select>
          </div>
          <Autocomplete
            label="Classroom"
            selectedKey={String(student?.classroom?.id)}
            placeholder="Select classroom"
            errorMessage={errors?.classroom?.general}
            isInvalid={!!errors?.classroom?.general}
            variant="bordered"
            isVirtualized={true}
            onSelectionChange={(key) =>
              setStudent({
                ...student,
                classroom: {
                  ...student.classroom,
                  id: !!key
                    ? classroomYears.find((c) => c.id === Number(key))!.id
                    : 0,
                },
              })
            }
          >
            {classroomYears?.map((classroom) => (
              <AutocompleteItem
                key={classroom.id}
              >{`${classroom.name} - ${classroom.calendarYear}`}</AutocompleteItem>
            ))}
          </Autocomplete>
          <Autocomplete
            label="Dorm"
            selectedKey={String(student?.dorm?.id)}
            placeholder="Select dorm"
            errorMessage={errors?.dorm?.general}
            isInvalid={!!errors?.dorm?.general}
            variant="bordered"
            isVirtualized={true}
            onSelectionChange={(key) =>
              setStudent({
                ...student,
                dorm: {
                  ...student.dorm,
                  id: !!key
                    ? dormYears.find((d) => d.id === Number(key))!.id
                    : 0,
                },
              })
            }
          >
            {dormYears?.map((dorm) => (
              <AutocompleteItem
                key={dorm.id}
              >{`${dorm.name} - ${dorm.calendarYear}`}</AutocompleteItem>
            ))}
          </Autocomplete>
          <Select
            label="School"
            selectedKeys={
              student?.school?.id ? [String(student.school.id)] : []
            }
            errorMessage={errors?.school?.general}
            isInvalid={!!errors?.school?.general}
            variant="bordered"
            onSelectionChange={(keys) =>
              setStudent({
                ...student,
                school: schools.find((s) => s.id === Number([...keys][0]))!,
              })
            }
          >
            {schools?.map((school) => (
              <SelectItem key={school.id}>{school.name}</SelectItem>
            ))}
          </Select>
        </div>
        <Button
          type="submit"
          color="primary"
          className="button"
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
