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

const salutations = ["MR", "MRS", "MS", "DR", "PROF"];
const roles = ["ADMIN", "TEACHER", "PARENT", "STUDENT"];

export default function RegisterModal({
  isOpen,
  onOpenChange,
  setLoading,
}: {
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  setLoading: (loading: boolean) => void;
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
  const [submitted, setSubmitted] = useState<any>(null);

  const handleChange = (key: string, value: string) => {
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);

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

    setTimeout(() => {
      setLoading(false);
      setSubmitted(payload);
      onOpenChange(false);
    }, 2000);

    // Here you would POST `payload` to your register endpoint
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
              onValueChange={(v) => handleChange("password", v)}
              size="sm"
              variant="underlined"
            />
            <Select
              className="text-foreground"
              label="Salutation"
              selectedKeys={[form.salutation]}
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
              onValueChange={(v) => handleChange("firstName", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Other Names"
              name="otherNames"
              placeholder="Enter other names"
              value={form.otherNames}
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
              onValueChange={(v) => handleChange("email", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Mobile Number"
              name="mobileNumber"
              placeholder="Enter mobile number"
              value={form.mobileNumber}
              onValueChange={(v) => handleChange("mobileNumber", v)}
              size="sm"
              variant="underlined"
            />
            <Input
              label="Date of Birth"
              name="dateOfBirth"
              type="date"
              value={form.dateOfBirth}
              onValueChange={(v) => handleChange("dateOfBirth", v)}
              size="sm"
              variant="underlined"
            />
            <Select
              label="Role"
              selectedKeys={[form.role]}
              variant="underlined"
              onSelectionChange={(keys) =>
                handleChange("role", Array.from(keys)[0] as string)
              }
            >
              {roles.map((r) => (
                <SelectItem key={r}>{r}</SelectItem>
              ))}
            </Select>
            <Button type="submit" color="primary" className="w-full">
              Register
            </Button>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button
            color="danger"
            className="w-full"
            onPress={() => onOpenChange(false)}
          >
            Cancel
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
