import React, { useState } from "react";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  Input,
  Form,
  Select,
  SelectItem,
} from "@heroui/react";
import { SignInIcon, XIcon } from "@phosphor-icons/react";
import {
  emailRegex,
  emailError,
  phoneNumberRegex,
  phoneNumberError,
} from "@/utils/helpers";

const salutations = ["MR", "MRS", "MS", "DR", "PROF"];
const roles = ["ADMIN", "TEACHER", "PARENT", "STUDENT"];

export default function RegisterModal({
  isOpen,
  onOpenChange,
  incrementLoading,
  decrementLoading,
}: {
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const [form, setForm] = useState({
    username: "",
    password: "",
    salutation: "MR",
    firstName: "",
    otherNames: "",
    lastName: "",
    email: "",
    mobileNumber: "",
    dateOfBirth: "",
    role: "ADMIN",
  });
  const [errors, setErrors] = useState<any>({ person: {} });
  const [submitted, setSubmitted] = useState<any>(null);

  const handleChange = (key: string, value: string) => {
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const validate = () => {
    setErrors({ person: {} });
    let tempErrors: any = { person: {} };

    // Username
    if (!form.username) tempErrors.username = "Username is required";
    // Password
    if (!form.password) tempErrors.password = "Password is required";
    // Person fields
    if (!form.salutation)
      tempErrors.person = {
        ...tempErrors.person,
        salutation: "Salutation is required",
      };
    if (!form.firstName)
      tempErrors.person = {
        ...tempErrors.person,
        firstName: "First name is required",
      };
    if (!form.lastName)
      tempErrors.person = {
        ...tempErrors.person,
        lastName: "Last name is required",
      };
    if (!form.email) {
      tempErrors.person = { ...tempErrors.person, email: "Email is required" };
    } else if (!emailRegex.test(form.email)) {
      tempErrors.person = { ...tempErrors.person, email: emailError };
    }
    if (form.mobileNumber && !phoneNumberRegex.test(form.mobileNumber)) {
      tempErrors.person = {
        ...tempErrors.person,
        mobileNumber: phoneNumberError,
      };
    }
    if (!form.dateOfBirth)
      tempErrors.person = {
        ...tempErrors.person,
        dateOfBirth: "Date of birth is required",
      };
    if (!form.role) tempErrors.role = "Role is required";

    setErrors(tempErrors);
    return (
      Object.keys(tempErrors).length === 0 &&
      Object.keys(tempErrors?.person || {}).length === 0
    );
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!validate()) return;
    incrementLoading();

    // Build the JSON object as per your API
    const payload = {
      datestamp: new Date().toISOString(),
      status: "ACTIVE",
      id: 0,
      username: form.username,
      password: form.password,
      verified: false,
      enabled: true,
      changePassword: true,
      person: {
        datestamp: new Date().toISOString(),
        status: "ACTIVE",
        id: 0,
        salutation: form.salutation,
        firstName: form.firstName,
        otherNames: form.otherNames,
        lastName: form.lastName,
        email: form.email,
        mobileNumber: form.mobileNumber,
        mobileNumber2: "",
        telNumber: "",
        whatsapp: "",
        facebook: "",
        linkedin: "",
        dateOfBirth: form.dateOfBirth,
      },
      roles: [form.role],
    };

    try {
      const res = await fetch("/api/auth?action=register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      if (!res.ok) {
        setErrors({ api: data.error || "Registration failed" });
      } else {
        setSubmitted(payload);
        onOpenChange(false);
      }
    } catch (e) {
      setErrors({ api: "Network/Server error" });
    } finally {
      decrementLoading();
    }
  };

  return (
    <Modal
      isOpen={isOpen}
      onOpenChange={onOpenChange}
      hideCloseButton={false}
      backdrop="blur"
      radius="md"
      shadow="md"
      classNames={{
        wrapper: "py-2 overflow-hidden",
      }}
    >
      <ModalContent className="overflow-y-auto max-h-full">
        <ModalHeader className="text-xl font-bold text-foreground">
          Register
        </ModalHeader>
        <ModalBody>
          <Form onSubmit={onSubmit} className="space-y-3">
            <Input
              isRequired
              label="Username"
              name="username"
              placeholder="Enter username"
              value={form.username}
              errorMessage={errors?.username}
              isInvalid={!!errors?.username}
              onValueChange={(v) => handleChange("username", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              isRequired
              label="Password"
              name="password"
              type="password"
              placeholder="Enter password"
              value={form.password}
              errorMessage={errors?.password}
              isInvalid={!!errors?.password}
              onValueChange={(v) => handleChange("password", v)}
              size="sm"
              variant="underlined"
            />
            <Select
              className="text-foreground"
              label="Salutation"
              selectedKeys={[form.salutation]}
              errorMessage={errors?.person?.salutation}
              isInvalid={!!errors?.person?.salutation}
              variant="underlined"
              onSelectionChange={(keys) =>
                handleChange("salutation", Array.from(keys)[0] as string)
              }
            >
              {salutations.map((s) => (
                <SelectItem key={s}>{s}</SelectItem>
              ))}
            </Select>
            <Input
              isRequired
              label="First Name"
              name="firstName"
              placeholder="Enter first name"
              value={form.firstName}
              errorMessage={errors?.person?.firstName}
              isInvalid={!!errors?.person?.firstName}
              onValueChange={(v) => handleChange("firstName", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Other Names"
              name="otherNames"
              placeholder="Enter other names"
              value={form.otherNames}
              errorMessage={errors?.person?.otherNames}
              isInvalid={!!errors?.person?.otherNames}
              onValueChange={(v) => handleChange("otherNames", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              isRequired
              label="Last Name"
              name="lastName"
              placeholder="Enter last name"
              value={form.lastName}
              errorMessage={errors?.person?.lastName}
              isInvalid={!!errors?.person?.lastName}
              onValueChange={(v) => handleChange("lastName", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              isRequired
              label="Email"
              name="email"
              type="email"
              placeholder="Enter email"
              value={form.email}
              errorMessage={errors?.person?.email}
              isInvalid={!!errors?.person?.email}
              onValueChange={(v) => handleChange("email", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Mobile Number"
              name="mobileNumber"
              placeholder="Enter mobile number"
              value={form.mobileNumber}
              errorMessage={errors?.person?.mobileNumber}
              isInvalid={!!errors?.person?.mobileNumber}
              onValueChange={(v) => handleChange("mobileNumber", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Date of Birth"
              name="dateOfBirth"
              type="date"
              value={form.dateOfBirth}
              errorMessage={errors?.person?.dateOfBirth}
              isInvalid={!!errors?.person?.dateOfBirth}
              onValueChange={(v) => handleChange("dateOfBirth", v)}
              size="sm"
              variant="underlined"
            />
            <Select
              label="Role"
              selectedKeys={[form.role]}
              errorMessage={errors?.role}
              isInvalid={!!errors?.role}
              variant="underlined"
              onSelectionChange={(keys) =>
                handleChange("role", Array.from(keys)[0] as string)
              }
            >
              {roles.map((r) => (
                <SelectItem key={r}>{r}</SelectItem>
              ))}
            </Select>
            <Button
              type="submit"
              color="primary"
              className="w-full"
              endContent={<SignInIcon weight="thin" size={25} />}
            >
              Register
            </Button>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button
            color="danger"
            className="w-full"
            onPress={() => onOpenChange(false)}
            endContent={<XIcon weight="thin" size={25} />}
          >
            Cancel
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
