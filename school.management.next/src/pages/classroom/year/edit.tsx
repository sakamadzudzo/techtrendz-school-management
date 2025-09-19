import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import {
  useClassroomYearsDropdown,
  useClassroomYearById,
} from "@/hooks/useClassroomYears";
import { useTeachersDropdown } from "@/hooks/useTeachers";
import { useSubjectYearsDropdown } from "@/hooks/useSubjectYears";
import { RootState } from "@/store/store";
import {
  Errors,
  ClassroomYear,
  Classroom,
  Teacher,
  SubjectYear,
} from "@/utils/types";
import { classroomViewToClassroomYear } from "@/utils/helpers";
import { Form, Button, Select, SelectItem } from "@heroui/react";
import { FloppyDiskIcon, PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function ClassroomYearEdit({
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
  const [classroomYear, setClassroomYear] = React.useState<ClassroomYear>({
    subjects: [] as SubjectYear[],
  } as ClassroomYear);
  const [errors, setErrors] = React.useState<Errors<ClassroomYear, string>>(
    {} as Errors<ClassroomYear, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const {
    classroomYear: oldYear,
    isLoading,
    isError,
  } = useClassroomYearById(id!);
  const { classroomYears } = useClassroomYearsDropdown();
  const { teachers } = useTeachersDropdown();
  const { subjectYears } = useSubjectYearsDropdown();

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing classroom year:
      </div>
      <div className="flex justify-center">
        Edit information about the classroom year
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating classroom year:
      </div>
      <div className="flex justify-center">
        Enter information about the classroom year
      </div>
    </div>
  );

  useEffect(() => {
    if (oldYear) setClassroomYear(classroomViewToClassroomYear(oldYear));
  }, [id, oldYear]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const validate = () => {
    setErrors({} as Errors<ClassroomYear, string>);
    let tempErrors: Errors<ClassroomYear, string> = {} as Errors<
      ClassroomYear,
      string
    >;
    if (!classroomYear.classroom || !classroomYear.classroom.id) {
      tempErrors = {
        ...tempErrors,
        classroom: {
          ...tempErrors.classroom,
          general: "Classroom is required",
        },
      };
    }
    if (!classroomYear.calendarYear || classroomYear.calendarYear < 2000) {
      tempErrors = {
        ...tempErrors,
        calendarYear: "Valid calendar year is required",
      };
    }
    if (!classroomYear.homeroomTeacher) {
      tempErrors = {
        ...tempErrors,
        homeroomTeacher: {
          ...tempErrors.homeroomTeacher,
          general: "Homeroom teacher is required",
        },
      };
    }
    if (!classroomYear.subjects || classroomYear.subjects.length === 0) {
      tempErrors = {
        ...tempErrors,
        subjects: {
          ...tempErrors.subjects,
          general: "At least one subject is required",
        },
      };
    }
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();

    if (!validate()) {
      decrementLoading();
      return;
    }

    try {
      const res = await fetch("/api/classroom-year?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(classroomYear),
      });
      const data = await res.json();
      if (!res.ok) {
        const err = data.error || "Saving classroom year failed";
        const code = res.status || 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        decrementLoading();
        return;
      } else if (data?.id) {
        router.push(`/classroom/year/one?id=${data?.id}`);
      }
    } catch (err: any) {
      setError(err.response.data.message || "Network/Server error");
      setStatusCode(err.status || 500);
    } finally {
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
          <Select
            label="Classroom"
            selectedKeys={
              classroomYear?.classroom?.id
                ? [String(classroomYear.classroom.id)]
                : []
            }
            errorMessage={errors?.classroom?.general}
            isInvalid={!!errors?.classroom?.general}
            variant="underlined"
            onSelectionChange={(keys) =>
              setClassroomYear({
                ...classroomYear,
                classroom: classroomYears?.find(
                  (c) => c.id === Number([...keys][0])
                ) as Classroom,
              })
            }
          >
            {classroomYears?.map((c) => (
              <SelectItem key={c.id}>{c.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Calendar Year"
            variant="underlined"
            selectedKeys={
              classroomYear?.calendarYear
                ? [String(classroomYear.calendarYear)]
                : []
            }
            errorMessage={errors?.calendarYear}
            isInvalid={!!errors?.calendarYear}
            onSelectionChange={(e) =>
              setClassroomYear({
                ...classroomYear,
                calendarYear: Number(e.currentKey),
              })
            }
          >
            {[...Array(31).keys()].map((i) => {
              const year = (new Date().getFullYear() - 15 + i).toString();
              return <SelectItem key={year}>{year}</SelectItem>;
            })}
          </Select>
          <Select
            label="Homeroom Teacher"
            selectedKeys={
              classroomYear?.homeroomTeacher?.id
                ? [String(classroomYear.homeroomTeacher.id)]
                : []
            }
            errorMessage={errors?.homeroomTeacher?.general}
            isInvalid={!!errors?.homeroomTeacher?.general}
            variant="underlined"
            onSelectionChange={(key) =>
              setClassroomYear({
                ...classroomYear,
                homeroomTeacher: {
                  ...classroomYear.homeroomTeacher,
                  id: Number(key.currentKey),
                },
              })
            }
          >
            {teachers?.map((t) => (
              <SelectItem key={t.id}>{t.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Subjects"
            selectedKeys={
              classroomYear?.subjects?.map((s) => String(s.id)) || []
            }
            errorMessage={errors?.subjects?.general}
            isInvalid={!!errors?.subjects?.general}
            variant="underlined"
            selectionMode="multiple"
            isVirtualized={true}
            onSelectionChange={(keys) =>
              setClassroomYear({
                ...classroomYear,
                subjects: Array.from(keys).map((id) => {
                  return { id: id } as SubjectYear;
                }),
              })
            }
          >
            {subjectYears?.map((s) => (
              <SelectItem
                key={s.id}
              >{`${s.name} - ${s.calendarYear}`}</SelectItem>
            ))}
          </Select>
        </div>
        <Button
          type="submit"
          color="primary"
          className="button"
          onPress={validate}
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
