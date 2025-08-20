import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useStudentById } from "@/hooks/useStudents";
import { RootState } from "@/store/store";
import { Student } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  SelectItem,
  Select,
  Autocomplete,
  AutocompleteItem,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { FloppyDiskIcon, StudentIcon } from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import { studentViewToStudent } from "@/utils/helpers";
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

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();

    try {
      const res = await fetch("/api/student?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(student),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error ?? "Saving student failed";
        const code = res.status ?? 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/student/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Network error");
      setStatusCode(err.status ?? 500);
      useToastError({ error: "Network error", statusCode: err.status ?? 500 });
      decrementLoading();
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
            value={student.person?.salutation ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, salutation: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="First Name"
            name="firstName"
            placeholder="Enter first name"
            type="text"
            value={student.person?.firstName ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, firstName: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Other Names"
            name="otherNames"
            placeholder="Enter other names"
            type="text"
            value={student.person?.otherNames ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, otherNames: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Last Name"
            name="lastName"
            placeholder="Enter last name"
            type="text"
            value={student.person?.lastName ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, lastName: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Email"
            name="email"
            placeholder="Enter email"
            type="email"
            value={student.person?.email ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, email: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number"
            name="mobileNumber"
            placeholder="Enter mobile number"
            type="text"
            value={student.person?.mobileNumber ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, mobileNumber: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number 2"
            name="mobileNumber2"
            placeholder="Enter secondary mobile number"
            type="text"
            value={student.person?.mobileNumber2 ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, mobileNumber2: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Tel Number"
            name="telNumber"
            placeholder="Enter telephone number"
            type="text"
            value={student.person?.telNumber ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, telNumber: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="WhatsApp"
            name="whatsapp"
            placeholder="Enter WhatsApp number"
            type="text"
            value={student.person?.whatsapp ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, whatsapp: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Facebook"
            name="facebook"
            placeholder="Enter Facebook profile"
            type="text"
            value={student.person?.facebook ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, facebook: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="LinkedIn"
            name="linkedin"
            placeholder="Enter LinkedIn profile"
            type="text"
            value={student.person?.linkedin ?? ""}
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, linkedin: e },
              })
            }
            size="sm"
            variant="underlined"
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
            onValueChange={(e) =>
              setStudent({
                ...student,
                person: { ...student.person, dateOfBirth: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <div className="w-full flex">
            {/* 
              Use 'Date Range Picker' to pick out start and end years
              Only show years without month and date 
            */}
          </div>
          <Autocomplete
            label="Classroom"
            selectedKey={String(student?.classroom?.id)}
            placeholder="Select classroom"
            variant="underlined"
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
            variant="underlined"
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
            variant="underlined"
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
          className="w-full px-5"
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
