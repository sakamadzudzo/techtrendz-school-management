import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useClassroomById, useSaveClassroom } from "@/hooks/useClassrooms";
import { RootState } from "@/store/store";
import { Errors, Classroom } from "@/utils/types";
import { Form, Button, Input, Textarea } from "@heroui/react";
import { FloppyDiskIcon, PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function ClassroomEdit({
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
  const [classroom, setClassroom] = React.useState<Classroom>({} as Classroom);
  const [errors, setErrors] = React.useState<Errors<Classroom, string>>(
    {} as Errors<Classroom, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { classroom: oldClassroom, isLoading, isError } = useClassroomById(id!);
  const { saveClassroom } = useSaveClassroom();

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing classroom:
      </div>
      <div className="flex justify-center">
        Edit information about the classroom
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating classroom:
      </div>
      <div className="flex justify-center">
        Enter information about the classroom
      </div>
    </div>
  );

  useEffect(() => {
    if (oldClassroom) setClassroom(oldClassroom);
  }, [id, oldClassroom]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const validate = () => {
    setErrors({} as Errors<Classroom, string>);
    let tempErrors: Errors<Classroom, string> = {} as Errors<Classroom, string>;
    if (!classroom.name || classroom.name.trim() === "") {
      tempErrors = { ...tempErrors, name: "Classroom name is required" };
    }
    if (!classroom.description || classroom.description.trim() === "") {
      tempErrors = { ...tempErrors, description: "Description is required" };
    }
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);

    if (!validate()) {
      return;
    }

    await saveClassroom(
      classroom,
      incrementLoading,
      decrementLoading,
      () => {
        if (classroom?.id) {
          router.push(`/classroom/one?id=${classroom.id}`);
        }
      }, // onSuccess callback with router.push
      (error, statusCode) => {
        setError(error);
        setStatusCode(statusCode);
        useToastError({ error, statusCode });
      } // onError callback
    );
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
            placeholder="Enter the classroom name"
            type="text"
            value={classroom.name || ""}
            errorMessage={errors?.name}
            isInvalid={!!errors?.name}
            onValueChange={(e) => setClassroom({ ...classroom, name: e })}
            size="sm"
            variant="bordered"
            autoFocus={true}
          />
          <Textarea
            isRequired
            label="Description"
            name="description"
            placeholder="Enter classroom description"
            type="text"
            value={classroom.description || ""}
            errorMessage={errors?.description}
            isInvalid={!!errors?.description}
            onValueChange={(e) =>
              setClassroom({ ...classroom, description: e })
            }
            size="sm"
            variant="bordered"
            className="placeholder:text-ellipsis"
          />
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
