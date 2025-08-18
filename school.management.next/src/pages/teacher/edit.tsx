import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useTeacherById } from "@/hooks/useTeachers";
import { RootState } from "@/store/store";
import { Teacher } from "@/utils/types";
import { Form, Button, Input, Select, SelectItem, Switch } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import {
  ChalkboardTeacherIcon,
  FloppyDiskIcon,
  UserIcon,
  UserPlusIcon,
} from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import { teacherViewToTeacher } from "@/utils/helpers";

export default function TeacherEdit({
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
  const [teacher, setTeacher] = React.useState<Teacher>({} as Teacher);
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { teacher: oldTeacher, isLoading, isError } = useTeacherById(id!);

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
        <ChalkboardTeacherIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing teacher:
      </div>
      <div className="flex justify-center">
        Edit information about the teacher
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <ChalkboardTeacherIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating teacher:
      </div>
      <div className="flex justify-center">
        Enter information about the teacher
      </div>
    </div>
  );

  useEffect(() => {
    if (oldTeacher) setTeacher(teacherViewToTeacher(oldTeacher));
  }, [id, oldTeacher]);

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();

    try {
      const res = await fetch("/api/teacher?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(teacher),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error ?? "Sving teacher failed";
        const code = res.status ?? 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/teacher/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Network error");
      setStatusCode(err.status ?? 500);
      useToastError({ error: "Network error", statusCode: err.status ?? 500 });
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
            label="Salutation"
            name="salutation"
            placeholder="Enter salutation"
            type="text"
            value={teacher.person?.salutation ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, salutation: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="First Name"
            name="firstName"
            placeholder="Enter first name"
            type="text"
            value={teacher.person?.firstName ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, firstName: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Other Names"
            name="otherNames"
            placeholder="Enter other names"
            type="text"
            value={teacher.person?.otherNames ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, otherNames: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Last Name"
            name="lastName"
            placeholder="Enter last name"
            type="text"
            value={teacher.person?.lastName ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, lastName: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Email"
            name="email"
            placeholder="Enter email"
            type="email"
            value={teacher.person?.email ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, email: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number"
            name="mobileNumber"
            placeholder="Enter mobile number"
            type="text"
            value={teacher.person?.mobileNumber ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, mobileNumber: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number 2"
            name="mobileNumber2"
            placeholder="Enter secondary mobile number"
            type="text"
            value={teacher.person?.mobileNumber2 ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, mobileNumber2: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Tel Number"
            name="telNumber"
            placeholder="Enter telephone number"
            type="text"
            value={teacher.person?.telNumber ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, telNumber: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="WhatsApp"
            name="whatsapp"
            placeholder="Enter WhatsApp number"
            type="text"
            value={teacher.person?.whatsapp ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, whatsapp: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Facebook"
            name="facebook"
            placeholder="Enter Facebook profile"
            type="text"
            value={teacher.person?.facebook ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, facebook: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="LinkedIn"
            name="linkedin"
            placeholder="Enter LinkedIn profile"
            type="text"
            value={teacher.person?.linkedin ?? ""}
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, linkedin: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Date of Birth"
            name="dateOfBirth"
            placeholder="Enter date of birth"
            type="date"
            value={
              teacher.person?.dateOfBirth
                ? typeof teacher.person.dateOfBirth === "string"
                  ? teacher.person.dateOfBirth
                  : new Date(teacher.person.dateOfBirth)
                      .toISOString()
                      .substring(0, 10)
                : ""
            }
            onValueChange={(e) =>
              setTeacher({
                ...teacher,
                person: { ...teacher.person, dateOfBirth: e },
              })
            }
            size="sm"
            variant="underlined"
          />
          {/* <Select
            label="Roles"
            selectedKeys={teacher.roles ?? []}
            variant="underlined"
            selectionMode="multiple"
            onSelectionChange={(keys) =>
              setTeacher({ ...teacher, roles: Array.from(keys) as string[] })
            }
          >
            {roles?.map((role) => (
              <SelectItem key={role}>{role}</SelectItem>
            ))}
          </Select>
          <div className="flex gap-4">
            <Switch
              aria-label="Verified"
              color="primary"
              size="sm"
              isSelected={teacher.verified ?? false}
              onValueChange={(e) =>
                setTeacher({ ...teacher, verified: e.valueOf() })
              }
            >
              Verified
            </Switch>
            <Switch
              aria-label="Enabled"
              color="primary"
              size="sm"
              isSelected={teacher.enabled ?? false}
              onValueChange={(e) =>
                setTeacher({ ...teacher, enabled: e.valueOf() })
              }
            >
              Enabled
            </Switch>
            <Switch
              aria-label="Change Password"
              color="primary"
              size="sm"
              isSelected={teacher.changePassword ?? false}
              onValueChange={(e) =>
                setTeacher({ ...teacher, changePassword: e.valueOf() })
              }
            >
              Change Password
            </Switch> */}
        </div>
        <Button
          type="submit"
          color="primary"
          className="w-full px-5"
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
