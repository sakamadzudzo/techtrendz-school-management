import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useDormsList } from "@/hooks/useDorms";
import { useSchoolsList } from "@/hooks/useSchools";
import { useDormYearById } from "@/hooks/useDormYears";
import { RootState } from "@/store/store";
import { Errors, DormYear, Dorm, School } from "@/utils/types";
import { Form, Button, Select, SelectItem } from "@heroui/react";
import { HouseLineIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { dormYearViewToDormYear } from "@/utils/helpers";

export default function DormYearEdit({
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
  const [dormYear, setDormYear] = React.useState<DormYear>({} as DormYear);
  const [errors, setErrors] = React.useState<Errors<DormYear, string>>(
    {} as Errors<DormYear, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { dormYear: oldDormYear, isLoading, isError } = useDormYearById(id!);
  const {
    dorms,
    isLoading: dormsLoading,
    isError: dormsError,
  } = useDormsList({});

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <HouseLineIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing dorm year:
      </div>
      <div className="flex justify-center">
        Edit information about the dorm year
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <HouseLineIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating dorm year:
      </div>
      <div className="flex justify-center">
        Enter information about the dorm year
      </div>
    </div>
  );

  useEffect(() => {
    if (oldDormYear) setDormYear(dormYearViewToDormYear(oldDormYear));
  }, [id, oldDormYear]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    dormsError &&
      useToastError({
        error: dormsError?.message,
        statusCode: dormsError?.statusCode,
      });
  }, [dormsError]);

  const validate = () => {
    setErrors({} as Errors<DormYear, string>);
    let tempErrors: Errors<DormYear, string> = {} as Errors<DormYear, string>;
    if (!dormYear.dorm || !dormYear.dorm.id) {
      tempErrors = {
        ...tempErrors,
        dorm: { ...tempErrors.dorm, general: "Dorm is required" },
      };
    }
    if (!dormYear.calendarYear) {
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
      const res = await fetch("/api/dorm-year?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(dormYear),
      });
      const data = await res.json();
      if (!res.ok) {
        const err = data.error ?? "Saving dorm year failed";
        const code = res.status ?? 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/dorm/year/one?id=${data?.id}`);
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
            label="Dorm"
            selectedKeys={dormYear?.dorm?.id ? [String(dormYear.dorm.id)] : []}
            errorMessage={errors?.dorm?.general}
            isInvalid={!!errors?.dorm?.general}
            variant="underlined"
            onSelectionChange={(keys) =>
              setDormYear({
                ...dormYear,
                dorm: dorms.find((d) => d.id === Number([...keys][0])) as Dorm,
              })
            }
          >
            {dorms?.map((d) => (
              <SelectItem key={d.id}>{d.name}</SelectItem>
            ))}
          </Select>
          <Select
            label="Calendar Year"
            variant="underlined"
            selectedKeys={
              dormYear?.calendarYear ? [String(dormYear.calendarYear)] : []
            }
            errorMessage={errors?.calendarYear}
            isInvalid={!!errors?.calendarYear}
            onSelectionChange={(e) =>
              setDormYear({ ...dormYear, calendarYear: Number(e.currentKey) })
            }
          >
            {[...Array(31).keys()].map((i) => {
              const year = (new Date().getFullYear() - 15 + i).toString();
              return <SelectItem key={year}>{year}</SelectItem>;
            })}
          </Select>
        </div>
        <Button type="submit" color="primary" className="w-full px-5" onPress={validate}>
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
