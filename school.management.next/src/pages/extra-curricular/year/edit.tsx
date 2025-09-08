import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useExtraCurricularsList } from "@/hooks/useExtraCurriculars";
import { useExtraCurricularYearById } from "@/hooks/useExtraCurricularYears";
import { useStudentsDropdown } from "@/hooks/useStudents";
import { useTeachersDropdown } from "@/hooks/useTeachers";
import { RootState } from "@/store/store";
import { extraCurricularYearViewToExtraCurricularYear } from "@/utils/helpers";
import {
  Errors,
  ExtraCurricularYear,
  ExtraCurricular,
  Teacher,
  Student,
} from "@/utils/types";
import { Form, Button, Input, Select, SelectItem } from "@heroui/react";
import { PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function ExtraCurricularYearEdit({
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
  const [extraCurricularYear, setExtraCurricularYear] =
    React.useState<ExtraCurricularYear>({
      facilitators: [] as Teacher[],
      students: [] as Student[],
    } as ExtraCurricularYear);
  const [errors, setErrors] = React.useState<
    Errors<ExtraCurricularYear, string>
  >({} as Errors<ExtraCurricularYear, string>);
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const {
    extraCurricularYear: oldYear,
    isLoading,
    isError,
  } = useExtraCurricularYearById(id!);
  const {
    extraCurriculars,
    isLoading: extraCurricularsLoading,
    isError: extraCurricularsError,
  } = useExtraCurricularsList({});
  const {
    teachers: facilitators,
    isLoading: teachersLoading,
    isError: teachersError,
  } = useTeachersDropdown();
  const {
    students,
    isLoading: studentsLoading,
    isError: studentsError,
  } = useStudentsDropdown();

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing extra-curricular year:
      </div>
      <div className="flex justify-center">
        Edit information about the activity year
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating extra-curricular year:
      </div>
      <div className="flex justify-center">
        Enter information about the activity year
      </div>
    </div>
  );

  useEffect(() => {
    if (oldYear)
      setExtraCurricularYear(
        extraCurricularYearViewToExtraCurricularYear(oldYear)
      );
  }, [id, oldYear]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    teachersError &&
      useToastError({
        error: teachersError?.message,
        statusCode: teachersError?.statusCode,
      });
  }, [teachersError]);

  useEffect(() => {
    studentsError &&
      useToastError({
        error: studentsError?.message,
        statusCode: studentsError?.statusCode,
      });
  }, [studentsError]);

  useEffect(() => {
    extraCurricularsError &&
      useToastError({
        error: extraCurricularsError?.message,
        statusCode: extraCurricularsError?.statusCode,
      });
  }, [extraCurricularsError]);

  const validate = () => {
    setErrors({} as Errors<ExtraCurricularYear, string>);
    let tempErrors: Errors<ExtraCurricularYear, string> = {} as Errors<
      ExtraCurricularYear,
      string
    >;
    if (
      !extraCurricularYear.extraCurricular ||
      !extraCurricularYear.extraCurricular.id
    ) {
      tempErrors = {
        ...tempErrors,
        extraCurricular: {
          ...tempErrors.extraCurricular,
          general: "Extra curricular activity is required",
        },
      };
    }
    if (!extraCurricularYear.calendarYear) {
      tempErrors = {
        ...tempErrors,
        calendarYear: "Calendar year is required",
      };
    }
    if (
      !extraCurricularYear?.facilitators ||
      extraCurricularYear?.facilitators.length === 0
    ) {
      tempErrors = {
        ...tempErrors,
        facilitators: {
          ...tempErrors?.facilitators,
          general: "At least one facilitator is required",
        },
      };
    }
    if (
      !extraCurricularYear.students ||
      extraCurricularYear.students.length === 0
    ) {
      tempErrors = {
        ...tempErrors,
        students: {
          ...tempErrors.students,
          general: "At least one student is required",
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
      const res = await fetch("/api/extra-curricular-year?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(extraCurricularYear),
      });
      const data = await res.json();
      if (!res.ok) {
        const err = data.error ?? "Saving extra curricular year failed";
        const code = res.status ?? 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/extra-curricular/year/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Netcoursework/Server error");
      setStatusCode(err.status ?? 500);
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
            label="Calendar Year"
            name="calendarYear"
            variant="underlined"
            selectedKeys={[String(extraCurricularYear.calendarYear)]}
            errorMessage={errors?.calendarYear}
            isInvalid={!!errors?.calendarYear}
            onSelectionChange={(e) =>
              setExtraCurricularYear({
                ...extraCurricularYear,
                calendarYear: Number(e.currentKey!),
              })
            }
          >
            {[...Array(31).keys()].map((i) => {
              const year = (new Date().getFullYear() - 15 + i).toString();
              return <SelectItem key={year}>{year}</SelectItem>;
            })}
          </Select>
          <Select
            label="Extra-curricular Activity"
            selectedKeys={
              extraCurricularYear?.extraCurricular?.id
                ? [String(extraCurricularYear.extraCurricular.id)]
                : []
            }
            errorMessage={errors?.extraCurricular?.general}
            isInvalid={!!errors?.extraCurricular?.general}
            variant="underlined"
            onSelectionChange={(keys) =>
              setExtraCurricularYear({
                ...extraCurricularYear,
                extraCurricular: extraCurriculars.find(
                  (a) => a.id === Number([...keys][0])
                ) as ExtraCurricular,
              })
            }
          >
            {extraCurriculars?.map((a) => (
              <SelectItem key={a.id}>{a.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Facilitators"
            selectedKeys={
              extraCurricularYear?.facilitators?.map((t) => String(t.id)) ?? []
            }
            errorMessage={errors?.facilitators?.general}
            isInvalid={!!errors?.facilitators?.general}
            variant="underlined"
            selectionMode="multiple"
            isVirtualized={true}
            onSelectionChange={(keys) =>
              setExtraCurricularYear({
                ...extraCurricularYear,
                facilitators: Array.from(keys).map((id) => {
                  return { id: id } as Teacher;
                }),
              })
            }
          >
            {facilitators?.map((t) => (
              <SelectItem key={t.id}>{t.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Students"
            selectedKeys={
              extraCurricularYear?.students?.map((s) => String(s.id)) ?? []
            }
            errorMessage={errors?.students?.general}
            isInvalid={!!errors?.students?.general}
            variant="underlined"
            selectionMode="multiple"
            isVirtualized={true}
            onSelectionChange={(keys) =>
              setExtraCurricularYear({
                ...extraCurricularYear,
                students: Array.from(keys).map((id) => {
                  return { id: id } as Student;
                }),
              })
            }
          >
            {students?.map((s) => (
              <SelectItem key={s.id}>{s.name}</SelectItem>
            ))}
          </Select>
        </div>
        <Button type="submit" color="primary" className="w-full px-5" onPress={validate}>
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
