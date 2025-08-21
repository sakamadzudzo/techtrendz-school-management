import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useSchoolById } from "@/hooks/useSchools";
import { RootState } from "@/store/store";
import { Errors, School } from "@/utils/types";
import { Form, Button, Input, Textarea } from "@heroui/react";
import { emailRegex, emailError, phoneNumberRegex } from "@/utils/helpers";
import { ChalkboardIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function SchoolEdit({
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
  const [school, setSchool] = React.useState<School>({} as School);
  const [errors, setErrors] = React.useState<Errors<School, string>>(
    {} as Errors<School, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { school: oldSchool, isLoading, isError } = useSchoolById(id!);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <ChalkboardIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing school:
      </div>
      <div className="flex justify-center">
        Edit information about the school
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <ChalkboardIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating school:
      </div>
      <div className="flex justify-center">
        Enter information about the school
      </div>
    </div>
  );

  useEffect(() => {
    if (oldSchool) setSchool(oldSchool);
  }, [id, oldSchool]);

  const validate = () => {
    setErrors({} as Errors<School, string>);
    let tempErrors: Errors<School, string> = {} as Errors<School, string>;

    if (!school.name || school.name.trim() === "") {
      tempErrors = { ...tempErrors, name: "School name is required" };
    }
    if (!school.email || school.email.trim() === "") {
      tempErrors = { ...tempErrors, email: "Email is required" };
    } else if (!emailRegex.test(school.email)) {
      tempErrors = { ...tempErrors, email: emailError };
    }
    if (!school.telNumber || school.telNumber.trim() === "") {
      tempErrors = { ...tempErrors, telNumber: "Tel number is required" };
    } else if (!phoneNumberRegex.test(school.telNumber)) {
      tempErrors = { ...tempErrors, telNumber: "Invalid telephone number" };
    }
    if (!school.mobileNumber || school.mobileNumber.trim() === "") {
      tempErrors = {
        ...tempErrors,
        mobileNumber: "First cell number is required",
      };
    } else if (!phoneNumberRegex.test(school.mobileNumber)) {
      tempErrors = { ...tempErrors, mobileNumber: "Invalid cell number" };
    }
    if (!school.mobileNumber2 || school.mobileNumber2.trim() === "") {
      tempErrors = {
        ...tempErrors,
        mobileNumber2: "Second cell number is required",
      };
    } else if (!phoneNumberRegex.test(school.mobileNumber2)) {
      tempErrors = { ...tempErrors, mobileNumber2: "Invalid cell number" };
    }
    if (!school.address || school.address.trim() === "") {
      tempErrors = { ...tempErrors, address: "Address is required" };
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const changeNumbers = (field: string, value: string) => {
    if (!phoneNumberRegex.test(value)) {
      setErrors({ ...errors, [field]: "Invalid phone number" });
    } else {
      setSchool({ ...school, [field]: value });
    }
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
      const res = await fetch("/api/school?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(school),
      });

      const data = await res.json();

      if (!res.ok) {
        const err: string = data.error ?? "Sving school failed";
        const code: number = res.status ?? 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/school/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Network/Server error");
      setStatusCode(err.status ?? 500);
      useToastError({ error: "Network/Server error", statusCode: err.status ?? 500 });
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
            placeholder="Enter the school name"
            type="text"
            value={school.name ?? ""}
            errorMessage={errors?.name}
            isInvalid={!!errors?.name}
            onValueChange={(e) => setSchool({ ...school, name: e })}
            size="sm"
            variant="underlined"
            autoFocus={true}
          />
          <Input
            isRequired
            label="E-mail"
            name="email"
            placeholder="Enter your e-mail address"
            type="email"
            value={school.email ?? ""}
            errorMessage={errors?.email}
            isInvalid={!!errors?.email}
            onValueChange={(e) => setSchool({ ...school, email: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Input
            isRequired
            label="Tel Number"
            name="telNumber"
            placeholder="Enter tel number"
            type="text"
            value={school.telNumber ?? ""}
            errorMessage={errors?.telNumber}
            isInvalid={!!errors?.telNumber}
            onValueChange={(e) => changeNumbers("telNumber", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Cell 1"
            name="cell1"
            placeholder="Enter first cell number"
            type="text"
            value={school.mobileNumber ?? ""}
            errorMessage={errors?.mobileNumber}
            isInvalid={!!errors?.mobileNumber}
            onValueChange={(e) => changeNumbers("mobileNumber", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Cell 2"
            name="cell2"
            placeholder="Enter second cell number"
            type="text"
            value={school.mobileNumber2 ?? ""}
            errorMessage={errors?.mobileNumber2}
            isInvalid={!!errors?.mobileNumber2}
            onValueChange={(e) => changeNumbers("mobileNumber2", e)}
            size="sm"
            variant="underlined"
          />
          <Textarea
            isRequired
            label="Address"
            name="address"
            placeholder="Enter the school's address"
            type="text"
            value={school.address ?? ""}
            errorMessage={errors?.address}
            isInvalid={!!errors?.address}
            onValueChange={(e) => setSchool({ ...school, address: e })}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Website"
            name="website"
            placeholder="Enter the school's website"
            type="url"
            value={school.website ?? ""}
            onValueChange={(e) => setSchool({ ...school, website: e })}
            size="sm"
            variant="underlined"
          />
        </div>
        <Button type="submit" color="primary" className="w-full px-5">
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
