import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useSchoolById } from "@/hooks/useSchools";
import { RootState } from "@/store/store";
import { School } from "@/utils/types";
import { Form, Button, Input, Textarea } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function SchoolEdit({
  setLoading,
}: {
  setLoading: (loading: boolean) => void;
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
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { school: oldSchool, isLoading, isError } = useSchoolById(id!);

  const editMsg: React.ReactNode = (
    <div className="w-full">
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

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    setLoading(true);

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
        // const data = await res.json();
        setError(data.error || "Sving school failed");
        setStatusCode(res.status || 500);
        return;
      } else if (data?.id) {
        router.push(`/school/one?id=${data?.id}`);
      }
      setLoading(false);
    } catch (err: any) {
      setError("Network error");
      setStatusCode(err.status || 500);
      setLoading(false);
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
            value={school.name || ""}
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
            value={school.email || ""}
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
            value={school.telNumber || ""}
            onValueChange={(e) => setSchool({ ...school, telNumber: e })}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Cell 1"
            name="cell1"
            placeholder="Enter first cell number"
            type="text"
            value={school.mobileNumber || ""}
            onValueChange={(e) => setSchool({ ...school, mobileNumber: e })}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Cell 2"
            name="cell2"
            placeholder="Enter second cell number"
            type="text"
            value={school.mobileNumber2 || ""}
            onValueChange={(e) => setSchool({ ...school, mobileNumber2: e })}
            size="sm"
            variant="underlined"
          />
          <Textarea
            isRequired
            label="Address"
            name="address"
            placeholder="Enter the school's address"
            type="text"
            value={school.address || ""}
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
            value={school.website || ""}
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
