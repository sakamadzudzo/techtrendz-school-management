import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useExtraCurricularById } from "@/hooks/useExtraCurriculars";
import { RootState } from "@/store/store";
import { Errors, ExtraCurricular } from "@/utils/types";
import { Form, Button, Input, Textarea } from "@heroui/react";
import { PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function ExtraCurricularEdit({
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
  const [extraCurricular, setExtraCurricular] = React.useState<ExtraCurricular>(
    {} as ExtraCurricular
  );
  const [errors, setErrors] = React.useState<Errors<ExtraCurricular, string>>(
    {} as Errors<ExtraCurricular, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const {
    extraCurricular: oldEA,
    isLoading,
    isError,
  } = useExtraCurricularById(id!);

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing extra-curricular activity:
      </div>
      <div className="flex justify-center">
        Edit information about the activity
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating extra-curricular activity:
      </div>
      <div className="flex justify-center">
        Enter information about the activity
      </div>
    </div>
  );

  useEffect(() => {
    if (oldEA) setExtraCurricular(oldEA);
  }, [id, oldEA]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const validate = () => {
    setErrors({} as Errors<ExtraCurricular, string>);
    let tempErrors: Errors<ExtraCurricular, string> = {} as Errors<
      ExtraCurricular,
      string
    >;

    if (!extraCurricular.name || extraCurricular.name.trim() === "") {
      tempErrors = { ...tempErrors, name: "Activity name is required" };
    }
    if (
      !extraCurricular.description ||
      extraCurricular.description.trim() === ""
    ) {
      tempErrors = { ...tempErrors, description: "Description is required" };
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
      const res = await fetch("/api/extra-curricular?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(extraCurricular),
      });

      const data = await res.json();

      if (!res.ok) {
        setError(data.error ?? "Saving extra-curricular failed");
        setStatusCode(res.status ?? 500);
        return;
      } else if (data?.id) {
        router.push(`/extra-curricular/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Network/Server error");
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
          <Input
            isRequired
            label="Name"
            name="name"
            placeholder="Enter activity name"
            type="text"
            value={extraCurricular.name ?? ""}
            errorMessage={errors?.name}
            isInvalid={!!errors?.name}
            onValueChange={(e) =>
              setExtraCurricular({ ...extraCurricular, name: e })
            }
            size="sm"
            variant="underlined"
            autoFocus={true}
          />
          <Textarea
            isRequired
            label="Description"
            name="description"
            placeholder="Enter description"
            type="text"
            value={extraCurricular.description ?? ""}
            errorMessage={errors?.description}
            isInvalid={!!errors?.description}
            onValueChange={(e) =>
              setExtraCurricular({ ...extraCurricular, description: e })
            }
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
        </div>
        <Button type="submit" color="primary" className="w-full px-5">
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
