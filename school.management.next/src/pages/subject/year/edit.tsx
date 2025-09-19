import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useSubjectsList } from "@/hooks/useSubjects";
import { useTeachersDropdown } from "@/hooks/useTeachers";
import { RootState } from "@/store/store";
import { Errors, SubjectYear, Subject, Teacher } from "@/utils/types";
import { subjectViewToSubjectYear } from "@/utils/helpers";
import { Form, Button, Select, SelectItem } from "@heroui/react";
import { FloppyDiskIcon, PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { useSubjectYearById } from "@/hooks/useSubjectYears";

export default function SubjectYearEdit({
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
  const [subjectYear, setSubjectYear] = React.useState<SubjectYear>(
    {} as SubjectYear
  );
  const [errors, setErrors] = React.useState<Errors<SubjectYear, string>>(
    {} as Errors<SubjectYear, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const {
    subjectYear: oldSubjectYear,
    isLoading,
    isError,
  } = useSubjectYearById(id!);
  const { subjects } = useSubjectsList({});
  const { teachers } = useTeachersDropdown();

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing subject year:
      </div>
      <div className="flex justify-center">
        Edit information about the subject year
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating subject year:
      </div>
      <div className="flex justify-center">
        Enter information about the subject year
      </div>
    </div>
  );

  useEffect(() => {
    if (oldSubjectYear)
      setSubjectYear(subjectViewToSubjectYear(oldSubjectYear));
  }, [id, oldSubjectYear]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const validate = () => {
    setErrors({} as Errors<SubjectYear, string>);
    let tempErrors: Errors<SubjectYear, string> = {} as Errors<
      SubjectYear,
      string
    >;
    if (!subjectYear.subject || !subjectYear.subject.id) {
      tempErrors = {
        ...tempErrors,
        subject: { ...tempErrors.subject, general: "Subject is required" },
      };
    }
    if (!subjectYear.teacher || !subjectYear.teacher.id) {
      tempErrors = {
        ...tempErrors,
        teacher: { ...tempErrors.teacher, general: "Teacher is required" },
      };
    }
    if (!subjectYear.calendarYear) {
      tempErrors = {
        ...tempErrors,
        calendarYear: "Calendar year is required",
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
      const res = await fetch("/api/subject-year?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(subjectYear),
      });
      const data = await res.json();
      if (!res.ok) {
        setError(data.error || "Saving subject year failed");
        setStatusCode(res.status || 500);
        return;
      } else if (data?.id) {
        router.push(`/subject/year/one?id=${data?.id}`);
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
            label="Subject"
            selectedKeys={
              subjectYear?.subject?.id ? [String(subjectYear.subject.id)] : []
            }
            errorMessage={errors?.subject?.general}
            isInvalid={!!errors?.subject?.general}
            variant="underlined"
            onSelectionChange={(keys) =>
              setSubjectYear({
                ...subjectYear,
                subject: subjects.find(
                  (s) => s.id === Number([...keys][0])
                ) as Subject,
              })
            }
          >
            {subjects?.map((s) => (
              <SelectItem key={s.id}>{s.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Teacher"
            selectedKeys={
              subjectYear?.teacher?.id ? [String(subjectYear.teacher.id)] : []
            }
            errorMessage={errors?.teacher?.general}
            isInvalid={!!errors?.teacher?.general}
            variant="underlined"
            onSelectionChange={(key) =>
              setSubjectYear({
                ...subjectYear,
                teacher: { ...subjectYear.teacher, id: Number(key.currentKey) },
              })
            }
          >
            {teachers?.map((t) => (
              <SelectItem key={t.id}>{t.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Calendar Year"
            variant="underlined"
            selectedKeys={
              subjectYear?.calendarYear
                ? [String(subjectYear.calendarYear)]
                : []
            }
            errorMessage={errors?.calendarYear}
            isInvalid={!!errors?.calendarYear}
            onSelectionChange={(e) =>
              setSubjectYear({
                ...subjectYear,
                calendarYear: Number(e.currentKey),
              })
            }
          >
            {[...Array(31).keys()].map((i) => {
              const year = (new Date().getFullYear() - 15 + i).toString();
              return <SelectItem key={year}>{year}</SelectItem>;
            })}
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
