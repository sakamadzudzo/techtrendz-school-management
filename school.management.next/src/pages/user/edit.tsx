import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import { useUserById } from "@/hooks/useUsers";
import { RootState } from "@/store/store";
import { Errors, User } from "@/utils/types";
import { Form, Button, Input, Select, SelectItem, Switch } from "@heroui/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { userViewToUser } from "@/utils/helpers";
import { emailRegex, emailError, phoneNumberRegex } from "@/utils/helpers";
import { roles } from "@/utils/classes";
import { FloppyDiskIcon, UserIcon, UserPlusIcon } from "@phosphor-icons/react";
import useToastError from "@/hooks/toasts/ToastError";

export default function UserEdit({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const principal = useSelector((state: any) => state.auth.user);
  const [isAdmin, setIsAdmin] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);
  const [statusCode, setStatusCode] = React.useState(0);
  const [user, setUser] = React.useState<User>({} as User);
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { user: oldUser, isLoading, isError } = useUserById(id!);
  const [updatePassword, setUpdatePassword] = React.useState(false);
  const [updateUsername, setUpdateUsername] = React.useState(false);
  const [errors, setErrors] = React.useState<Errors<User, string>>(
    {} as Errors<User, string>
  );

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
        <UserIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing user:
      </div>
      <div className="flex justify-center">Edit information about the user</div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <UserPlusIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating user:
      </div>
      <div className="flex justify-center">
        Enter information about the user
      </div>
    </div>
  );

  useEffect(() => {
    if (oldUser) setUser(userViewToUser(oldUser));
  }, [id, oldUser]);

  useEffect(() => {
    if (principal) {
      setIsAdmin(principal.roles.includes("ADMIN"));
    }
  }, [principal]);

  const validate = () => {
    setErrors({} as Errors<User, string>);
    let tempErrors: Errors<User, string> = {} as Errors<User, string>;

    if (!user?.person?.salutation) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, salutation: "Salutation is required" },
      };
    }
    if (!user?.person?.firstName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, firstName: "First name is required" },
      };
    }
    if (!user?.person?.lastName) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, lastName: "Last name is required" },
      };
    }
    if (!user?.person?.email) {
      tempErrors = {
        ...tempErrors,
        person: { ...tempErrors?.person, email: "Email is required" },
      };
    } else {
      if (!emailRegex.test(user?.person?.email)) {
        tempErrors = {
          ...tempErrors,
          person: { ...tempErrors?.person, email: emailError },
        };
      }
    }
    if (!user?.person?.dateOfBirth) {
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

  const changeNumbers = (field: string, value: string) => {
    if (!phoneNumberRegex.test(value)) {
      setErrors({
        ...errors,
        person: { ...errors.person, [field]: "Invalid phone number" },
      });
    } else {
      setUser({ ...user, person: { ...user.person, [field]: value } });
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
      const res = await fetch("/api/user?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(user),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Sving teacher failed";
        const code = res.status || 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else if (data?.id) {
        router.push(`/user/one?id=${data?.id}`);
      }
    } catch (err: any) {
      setError(err.response.data.message || "Network/Server error");
      setStatusCode(err.status || 500);
      useToastError({
        error: "Network/Server error",
        statusCode: err.status || 500,
      });
    } finally {
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
          {(!id || isAdmin) && (
            <>
              {!updateUsername ? (
                <div className="w-full">
                  <Switch
                    aria-label="update-username"
                    color="primary"
                    size="sm"
                    isSelected={updateUsername || false}
                    onValueChange={(e) => setUpdateUsername(e.valueOf())}
                  >
                    Update username?
                  </Switch>
                </div>
              ) : (
                <Input
                  isRequired
                  label="Username"
                  name="username"
                  placeholder="Enter username"
                  type="text"
                  value={user.username || ""}
                  onValueChange={(e) => setUser({ ...user, username: e })}
                  size="sm"
                  variant="underlined"
                  autoFocus={true}
                />
              )}
            </>
          )}
          {(!id || isAdmin) && (
            <>
              {!updatePassword ? (
                <div className="w-full">
                  <Switch
                    aria-label="update-password"
                    color="primary"
                    size="sm"
                    isSelected={updatePassword || false}
                    onValueChange={(e) => setUpdatePassword(e.valueOf())}
                  >
                    Update password?
                  </Switch>
                </div>
              ) : (
                <Input
                  label="Password"
                  name="password"
                  placeholder="Enter password (leave blank to keep unchanged)"
                  type="password"
                  value={user.password || ""}
                  onValueChange={(e) => setUser({ ...user, password: e })}
                  size="sm"
                  variant="underlined"
                />
              )}
            </>
          )}
          <Input
            label="Salutation"
            name="salutation"
            placeholder="Enter salutation"
            type="text"
            value={user.person?.salutation || ""}
            errorMessage={errors?.person?.salutation}
            isInvalid={!!errors?.person?.salutation}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, salutation: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="First Name"
            name="firstName"
            placeholder="Enter first name"
            type="text"
            value={user.person?.firstName || ""}
            errorMessage={errors?.person?.firstName}
            isInvalid={!!errors?.person?.firstName}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, firstName: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Other Names"
            name="otherNames"
            placeholder="Enter other names"
            type="text"
            value={user.person?.otherNames || ""}
            errorMessage={errors?.person?.otherNames}
            isInvalid={!!errors?.person?.otherNames}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, otherNames: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Last Name"
            name="lastName"
            placeholder="Enter last name"
            type="text"
            value={user.person?.lastName || ""}
            errorMessage={errors?.person?.lastName}
            isInvalid={!!errors?.person?.lastName}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, lastName: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Email"
            name="email"
            placeholder="Enter email"
            type="email"
            value={user.person?.email || ""}
            errorMessage={errors?.person?.email}
            isInvalid={!!errors?.person?.email}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, email: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number"
            name="mobileNumber"
            placeholder="Enter mobile number"
            type="text"
            value={user.person?.mobileNumber || ""}
            errorMessage={errors?.person?.mobileNumber}
            isInvalid={!!errors?.person?.mobileNumber}
            onValueChange={(e) => changeNumbers("mobileNumber", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            label="Mobile Number 2"
            name="mobileNumber2"
            placeholder="Enter secondary mobile number"
            type="text"
            value={user.person?.mobileNumber2 || ""}
            errorMessage={errors?.person?.mobileNumber2}
            isInvalid={!!errors?.person?.mobileNumber2}
            onValueChange={(e) => changeNumbers("mobileNumber2", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            label="Tel Number"
            name="telNumber"
            placeholder="Enter telephone number"
            type="text"
            value={user.person?.telNumber || ""}
            errorMessage={errors?.person?.telNumber}
            isInvalid={!!errors?.person?.telNumber}
            onValueChange={(e) => changeNumbers("telNumber", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            label="WhatsApp"
            name="whatsapp"
            placeholder="Enter WhatsApp number"
            type="text"
            value={user.person?.whatsapp || ""}
            errorMessage={errors?.person?.whatsapp}
            isInvalid={!!errors?.person?.whatsapp}
            onValueChange={(e) => changeNumbers("whatsapp", e)}
            size="sm"
            variant="underlined"
          />
          <Input
            label="Facebook"
            name="facebook"
            placeholder="Enter Facebook profile"
            type="text"
            value={user.person?.facebook || ""}
            errorMessage={errors?.person?.facebook}
            isInvalid={!!errors?.person?.facebook}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, facebook: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Input
            label="LinkedIn"
            name="linkedin"
            placeholder="Enter LinkedIn profile"
            type="text"
            value={user.person?.linkedin || ""}
            errorMessage={errors?.person?.linkedin}
            isInvalid={!!errors?.person?.linkedin}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, linkedin: e } })
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
              user.person?.dateOfBirth
                ? typeof user.person.dateOfBirth === "string"
                  ? user.person.dateOfBirth
                  : new Date(user.person.dateOfBirth)
                      .toISOString()
                      .substring(0, 10)
                : ""
            }
            errorMessage={errors?.person?.dateOfBirth}
            isInvalid={!!errors?.person?.dateOfBirth}
            onValueChange={(e) =>
              setUser({ ...user, person: { ...user.person, dateOfBirth: e } })
            }
            size="sm"
            variant="underlined"
          />
          <Select
            label="Roles"
            selectedKeys={user.roles || []}
            variant="underlined"
            selectionMode="multiple"
            errorMessage={errors?.roles}
            isInvalid={!!errors?.roles}
            onSelectionChange={(keys) =>
              setUser({ ...user, roles: Array.from(keys) as string[] })
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
              isSelected={user.verified || false}
              onValueChange={(e) => setUser({ ...user, verified: e.valueOf() })}
            >
              Verified
            </Switch>
            <Switch
              aria-label="Enabled"
              color="primary"
              size="sm"
              isSelected={user.enabled || false}
              onValueChange={(e) => setUser({ ...user, enabled: e.valueOf() })}
            >
              Enabled
            </Switch>
            <Switch
              aria-label="Change Password"
              color="primary"
              size="sm"
              isSelected={user.changePassword || false}
              onValueChange={(e) =>
                setUser({ ...user, changePassword: e.valueOf() })
              }
            >
              Change Password
            </Switch>
          </div>
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
