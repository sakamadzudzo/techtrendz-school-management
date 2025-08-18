import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useDormById } from "@/hooks/useDorms";
import { useSchoolsList } from "@/hooks/useSchools";
import { RootState } from "@/store/store";
import { Dorm } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  Textarea,
  Select,
  SelectItem,
  SharedSelection,
} from "@heroui/react";
import { HouseLineIcon } from "@phosphor-icons/react";
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
  const [name, setName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [telNumber, setTelNumber] = React.useState("");
  const [mobileNumber, setMobileNumber] = React.useState("");
  const [mobileNumber2, setMobileNumber2] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [website, setWebsite] = React.useState("");
  const [dorm, setDorm] = React.useState<Dorm>({} as Dorm);
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { dorm: oldDorm, isLoading, isError } = useDormById(id!);
  const {
    schools,
    isLoading: schoolsLoading,
    isError: schoolsErorr,
  } = useSchoolsList({});

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

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();

    try {
      const res = await fetch("/api/dorm?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(dorm),
      });

      const data = await res.json();

      if (!res.ok) {
        // const data = await res.json();
        setError(data.error ?? "Sving dorm failed");
        setStatusCode(res.status ?? 500);
        return;
      } else if (data?.id) {
        router.push(`/dorm/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Network error");
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
            placeholder="Enter the dorm name"
            type="text"
            value={dorm.name ?? ""}
            onValueChange={(e) => setDorm({ ...dorm, name: e })}
            size="sm"
            variant="underlined"
            autoFocus={true}
          />
          <Textarea
            isRequired
            label="Description"
            name="description"
            placeholder="Enter your dorm's description"
            type="text"
            value={dorm.description ?? ""}
            onValueChange={(e) => setDorm({ ...dorm, description: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Textarea
            isRequired
            label="Address"
            name="address"
            placeholder="Enter your dorm's address"
            type="text"
            value={dorm.address ?? ""}
            onValueChange={(e) => setDorm({ ...dorm, address: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Select
            label="School"
            selectedKeys={dorm?.school?.id ? [String(dorm.school.id)] : []}
            variant="underlined"
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
        <Button type="submit" color="primary" className="w-full px-5">
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
