import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useParentById, useSaveParent, useManageParentStudents } from "@/hooks/useParents";
import { useStudentsDropdown } from "@/hooks/useStudents";
import { RootState } from "@/store/store";
import { Errors, Parent } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  Select,
  SelectItem,
  Autocomplete,
  AutocompleteItem,
  Chip,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { FloppyDiskIcon, UserIcon } from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";
import {
  emailError,
  emailRegex,
  phoneNumberRegex,
  parentViewToParent,
} from "@/utils/helpers";

export default function ParentEdit({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const [error, setError] = React.useState<string | null>(null);
  const [errors, setErrors] = React.useState<Errors<Parent, string>>(
    {} as Errors<Parent, string>
  );
  const [statusCode, setStatusCode] = React.useState(0);
  const [parent, setParent] = React.useState<Parent>({} as Parent);
  const [selectedStudentIds, setSelectedStudentIds] = React.useState<Set<string>>(new Set());
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { parent: oldParent, isLoading, isError } = useParentById(id!);
  const {
    students,
    isLoading: studentsLoading,
    isError: studentsError,
  } = useStudentsDropdown();
  const { saveParent } = useSaveParent();
  const { manageStudents } = useManageParentStudents();

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  useEffect(() => {
    studentsError &&
      useToastError({
        error: studentsError?.message,
        statusCode: studentsError?.statusCode,
      });
  }, [studentsError]);

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <UserIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing parent:
      </div>
      <div className="flex justify-center">
        Edit information about the parent
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <UserIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating parent:
      </div>
      <div className="flex justify-center">
        Enter information about the parent
      </div>
    </div>
  );

  useEffect(() => {
    if (oldParent) {
      setParent(parentViewToParent(oldParent));
      const studentIds = new Set(oldParent.students?.map(s => String(s.id)) || []);
      setSelectedStudentIds(studentIds);
    }
  }, [id, oldParent]);

  const validate = () => {
    setErrors({} as Errors<Parent, string>);
    let tempErrors: Errors<Parent, string> = {} as Errors<Parent, string>;

    if (!parent?.person?.salutation) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, salutation: "Salutation is required" },
      };
    }
    if (!parent?.person?.firstName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, firstName: "First name is required" },
      };
    }
    if (!parent?.person?.lastName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, lastName: "Last name is required" },
      };
    }
    if (!parent?.person?.email) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, email: "Email is required" },
      };
    } else {
      if (!emailRegex.test(parent?.person?.email)) {
        tempErrors = {
          ...tempErrors,
          person: { ...tempErrors?.person, email: emailError },
        };
      }
    }
    if (!parent?.person?.dateOfBirth) {
      tempErrors = {
        ...tempErrors,
        person: {
          ...tempErrors?.person,
          dateOfBirth: "Date of birth is required",
        },
      };
    }

    setErrors(tempErrors);
    return (
      Object.keys(tempErrors).length === 0 &&
      Object.keys(tempErrors?.person || {}).length === 0
    );
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);

    if (!validate()) {
      return;
    }

    const result = await saveParent(
      parent,
      incrementLoading,
      decrementLoading,
      undefined, // onSuccess callback - will be handled below
      (error, statusCode) => {
        setError(error);
        setStatusCode(statusCode);
        useToastError({ error, statusCode });
      } // onError callback
    );

    if (result.success && result.data) {
      const savedParent = result.data;
      
      // Manage students if any are selected
      if (selectedStudentIds.size > 0) {
        const studentIdsArray = Array.from(selectedStudentIds).map(id => Number(id));
        await manageStudents(
          String(savedParent.id),
          studentIdsArray,
          incrementLoading,
          decrementLoading,
          () => {
            router.push(`/parent/one?id=${savedParent.id}`);
          },
          (error, statusCode) => {
            useToastError({ error, statusCode });
            // Still navigate even if student management fails
            router.push(`/parent/one?id=${savedParent.id}`);
          }
        );
      } else {
        router.push(`/parent/one?id=${savedParent.id}`);
      }
    }
  };

  const changeNumbers = (field: string, value: string) => {
    if (!phoneNumberRegex.test(value)) {
      setErrors({ ...errors, person: { ...errors.person, [field]: value } });
    } else {
      setParent({
        ...parent,
        person: { ...parent.person, [field]: value },
      });
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
            value={parent.person?.salutation || ""}
            errorMessage={errors?.person?.salutation}
            isInvalid={!!errors?.person?.salutation}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, salutation: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="First Name"
            name="firstName"
            placeholder="Enter first name"
            type="text"
            value={parent.person?.firstName || ""}
            errorMessage={errors?.person?.firstName}
            isInvalid={!!errors?.person?.firstName}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, firstName: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Other Names"
            name="otherNames"
            placeholder="Enter other names"
            type="text"
            value={parent.person?.otherNames || ""}
            errorMessage={errors?.person?.otherNames}
            isInvalid={!!errors?.person?.otherNames}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, otherNames: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Last Name"
            name="lastName"
            placeholder="Enter last name"
            type="text"
            value={parent.person?.lastName || ""}
            errorMessage={errors?.person?.lastName}
            isInvalid={!!errors?.person?.lastName}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, lastName: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Email"
            name="email"
            placeholder="Enter email"
            type="email"
            value={parent.person?.email || ""}
            errorMessage={errors?.person?.email}
            isInvalid={!!errors?.person?.email}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, email: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Mobile Number"
            name="mobileNumber"
            placeholder="Enter mobile number"
            type="text"
            value={parent.person?.mobileNumber || ""}
            errorMessage={errors?.person?.mobileNumber}
            isInvalid={!!errors?.person?.mobileNumber}
            onValueChange={(e) => changeNumbers("mobileNumber", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Mobile Number 2"
            name="mobileNumber2"
            placeholder="Enter secondary mobile number"
            type="text"
            value={parent.person?.mobileNumber2 || ""}
            errorMessage={errors?.person?.mobileNumber2}
            isInvalid={!!errors?.person?.mobileNumber2}
            onValueChange={(e) => changeNumbers("mobileNumber2", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Tel Number"
            name="telNumber"
            placeholder="Enter telephone number"
            type="text"
            value={parent.person?.telNumber || ""}
            errorMessage={errors?.person?.telNumber}
            isInvalid={!!errors?.person?.telNumber}
            onValueChange={(e) => changeNumbers("telNumber", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="WhatsApp"
            name="whatsapp"
            placeholder="Enter WhatsApp number"
            type="text"
            value={parent.person?.whatsapp || ""}
            errorMessage={errors?.person?.whatsapp}
            isInvalid={!!errors?.person?.whatsapp}
            onValueChange={(e) => changeNumbers("whatsapp", e)}
            size="sm"
            variant="bordered"
          />
          <Input
            label="Facebook"
            name="facebook"
            placeholder="Enter Facebook profile"
            type="text"
            value={parent.person?.facebook || ""}
            errorMessage={errors?.person?.facebook}
            isInvalid={!!errors?.person?.facebook}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, facebook: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="LinkedIn"
            name="linkedin"
            placeholder="Enter LinkedIn profile"
            type="text"
            value={parent.person?.linkedin || ""}
            errorMessage={errors?.person?.linkedin}
            isInvalid={!!errors?.person?.linkedin}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, linkedin: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Input
            label="Date of Birth"
            name="dateOfBirth"
            placeholder="Enter date of birth"
            type="date"
            value={
              parent.person?.dateOfBirth
                ? typeof parent.person.dateOfBirth === "string"
                  ? parent.person.dateOfBirth
                  : new Date(parent.person.dateOfBirth)
                      .toISOString()
                      .substring(0, 10)
                : ""
            }
            errorMessage={errors?.person?.dateOfBirth}
            isInvalid={!!errors?.person?.dateOfBirth}
            onValueChange={(e) =>
              setParent({
                ...parent,
                person: { ...parent.person, dateOfBirth: e },
              })
            }
            size="sm"
            variant="bordered"
          />
          <Select
            label="Students"
            placeholder="Select students"
            selectionMode="multiple"
            selectedKeys={selectedStudentIds}
            onSelectionChange={(keys) => {
              const stringKeys = new Set([...keys].map(k => String(k)));
              setSelectedStudentIds(stringKeys);
            }}
            variant="bordered"
            renderValue={(items) => (
              <div className="flex flex-wrap gap-2">
                {items.map((item) => (
                  <Chip key={item.key} size="sm">
                    {item.textValue}
                  </Chip>
                ))}
              </div>
            )}
          >
            {students?.map((student) => (
              <SelectItem key={student.id} textValue={student.name}>
                {student.name}
              </SelectItem>
            ))}
          </Select>
        </div>
        <Button
          type="submit"
          color="primary"
          className="button"
          endContent={<FloppyDiskIcon weight="thin" size={25} />}
        >
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}