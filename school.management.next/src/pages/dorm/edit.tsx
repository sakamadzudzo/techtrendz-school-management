import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useDormById, useSaveDorm } from "@/hooks/useDorms";
import { useSchoolsList } from "@/hooks/useSchools";
import { RootState } from "@/store/store";
import { Errors, Dorm } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  Textarea,
  Select,
  SelectItem,
} from "@heroui/react";
import { phoneNumberRegex } from "@/utils/helpers";
import { FloppyDiskIcon, HouseLineIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function DormEdit({
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
  const [dorm, setDorm] = React.useState<Dorm>({} as Dorm);
  const [errors, setErrors] = React.useState<Errors<Dorm, string>>(
    {} as Errors<Dorm, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { dorm: oldDorm, isLoading, isError } = useDormById(id!);
  const {
    schools,
    isLoading: schoolsLoading,
    isError: schoolsErorr,
  } = useSchoolsList({});
  const { saveDorm } = useSaveDorm();

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <HouseLineIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing dorm:
      </div>
      <div className="flex justify-center">Edit information about the dorm</div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <HouseLineIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating dorm:
      </div>
      <div className="flex justify-center">
        Enter information about the dorm
      </div>
    </div>
  );

  useEffect(() => {
    if (oldDorm) setDorm(oldDorm);
  }, [id, oldDorm]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    schoolsErorr &&
      useToastError({
        error: schoolsErorr?.message,
        statusCode: schoolsErorr?.statusCode,
      });
  }, [schoolsErorr]);

  const validate = () => {
    setErrors({} as Errors<Dorm, string>);
    let tempErrors: Errors<Dorm, string> = {} as Errors<Dorm, string>;

    if (!dorm.name || dorm.name.trim() === "") {
      tempErrors = { ...tempErrors, name: "Dorm name is required" };
    }
    if (!dorm.description || dorm.description.trim() === "") {
      tempErrors = { ...tempErrors, description: "Description is required" };
    }
    if (!dorm.address || dorm.address.trim() === "") {
      tempErrors = { ...tempErrors, address: "Address is required" };
    }
    if (!dorm.school || !dorm.school.id) {
      tempErrors = {
        ...tempErrors,
        school: { ...tempErrors.school, general: "School is required" },
      };
    }
    setErrors(tempErrors);
    return (
      Object.keys(tempErrors).length === 0 &&
      Object.keys(tempErrors.school || {}).length === 0
    );
  };

  const changeNumbers = (field: string, value: string) => {
    if (!phoneNumberRegex.test(value)) {
      setErrors({ ...errors, [field]: "Invalid phone number" });
    } else {
      setDorm({ ...dorm, [field]: value });
    }
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);

    if (!validate()) {
      return;
    }

    await saveDorm(
      dorm,
      incrementLoading,
      decrementLoading,
      () => {
        if (dorm?.id) {
          router.push(`/dorm/one?id=${dorm.id}`);
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
            placeholder="Enter the dorm name"
            type="text"
            value={dorm.name || ""}
            errorMessage={errors?.name}
            isInvalid={!!errors?.name}
            onValueChange={(e) => setDorm({ ...dorm, name: e })}
            size="sm"
            variant="bordered"
            autoFocus={true}
          />
          <Textarea
            isRequired
            label="Description"
            name="description"
            placeholder="Enter your dorm's description"
            type="text"
            value={dorm.description || ""}
            errorMessage={errors?.description}
            isInvalid={!!errors?.description}
            onValueChange={(e) => setDorm({ ...dorm, description: e })}
            size="sm"
            variant="bordered"
            className="placeholder:text-ellipsis"
          />
          <Textarea
            isRequired
            label="Address"
            name="address"
            placeholder="Enter your dorm's address"
            type="text"
            value={dorm.address || ""}
            errorMessage={errors?.address}
            isInvalid={!!errors?.address}
            onValueChange={(e) => setDorm({ ...dorm, address: e })}
            size="sm"
            variant="bordered"
            className="placeholder:text-ellipsis"
          />
          <Select
            label="School"
            selectedKeys={dorm?.school?.id ? [String(dorm.school.id)] : []}
            errorMessage={errors?.school?.general}
            isInvalid={!!errors?.school?.general}
            variant="bordered"
            onSelectionChange={(keys) =>
              setDorm({
                ...dorm,
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
          onPress={validate}
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
