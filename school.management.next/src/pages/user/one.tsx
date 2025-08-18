import MyChip from "@/components/MyChip";
import { useUserById } from "@/hooks/useUsers";
import { formatDate, getInitials, sanitizeForWhatsapp } from "@/utils/helpers";
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Image,
  Link,
  Spacer,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  IdentificationCardIcon,
  PencilSimpleLineIcon,
  PhoneIcon,
  BriefcaseIcon,
  ShareNetworkIcon,
  ChalkboardTeacherIcon,
  FolderSimpleIcon,
  CheckIcon,
  XIcon,
} from "@phosphor-icons/react";
import React, { useEffect } from "react";
import SubjectViewCard from "@/components/SubjectViewCard";
import useToastError from "@/hooks/toasts/ToastError";

export default function OneUserPage() {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const { user, isLoading, isError } = useUserById(id!);

  useEffect(() => {
    isError && useToastError({ error: isError?.message, statusCode: isError?.statusCode });
  }, [isError]);

  const hasContactInfo: boolean = React.useMemo(() => {
    return !!(
      user?.email ??
      user?.mobileNumber ??
      user?.mobileNumber2 ??
      user?.telNumber
    );
  }, [user]);

  const hasSocials: boolean = React.useMemo(() => {
    return !!(user?.whatsapp ?? user?.linkedin ?? user?.facebook);
  }, [user]);

  const disabledKeys: string[] = React.useMemo(() => {
    let disabled: string[] = [];
    if (!hasContactInfo) {
      disabled.push("2");
    }
    if (!hasSocials) {
      disabled.push("4");
    }
    return disabled;
  }, [hasContactInfo, hasSocials]);

  const XMark: React.ReactNode = <XIcon weight="thin" size={25} color="red" />;
  const CheckMark: React.ReactNode = (
    <CheckIcon weight="thin" size={25} color="green" />
  );

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
          href={`/user/edit?id=${user?.id}`}
        >
          Edit
        </Button>
      </div>
      <Accordion
        isCompact={true}
        defaultExpandedKeys={["1"]}
        disabledKeys={disabledKeys}
      >
        <AccordionItem
          key="1"
          aria-label="Basic Information"
          title="Basic Information"
          subtitle="User's identity and personal details."
          startContent={<IdentificationCardIcon weight="thin" size={34} />}
        >
          <div className="w-full px-4 flex flex-col md:flex-row">
            <div className="w-full md:w-1/4 aspect-square flex justify-center items-start">
              <div className="w-full aspect-square">
                {user?.avatarUrl ? (
                  <Image
                    src={user?.avatarUrl}
                    alt="Profile picture"
                    shadow="md"
                    className="w-full h-full"
                  />
                ) : (
                  <Avatar
                    src={user?.avatarUrl}
                    color="primary"
                    className="cursor-pointer w-full h-full"
                  >
                    {!user?.avatarUrl && getInitials(user?.name ?? "User")}
                  </Avatar>
                )}
              </div>
            </div>
            <div className="w-3/4 flex flex-col px-4">
              <div className="info-row">
                <div className="info-row-title">Full Name:</div>
                <div className="info-row-content">{user?.fullname}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Salutation:</div>
                <div className="info-row-content">{user?.salutation}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">First Name:</div>
                <div className="info-row-content">{user?.firstName}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Other Names:</div>
                <div className="info-row-content">{user?.otherNames}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Last Name:</div>
                <div className="info-row-content">{user?.lastName}</div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date of Birth:</div>
                <div className="info-row-content">
                  {formatDate(user?.dateOfBirth, false)}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Status:</div>
                <MyChip
                  className="capitalize info-row-content"
                  data={user?.status}
                  size="sm"
                  variant="flat"
                />
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">Date Created/Modified:</div>
                <div className="info-row-content">
                  {formatDate(user?.datestamp)}
                </div>
              </div>
              <Spacer x={4} y={4} />
              <div className="info-row">
                <div className="info-row-title">System ID:</div>
                <div className="info-row-content">{user?.id}</div>
              </div>
            </div>
          </div>
        </AccordionItem>
        <AccordionItem
          key="2"
          aria-label="Contact Details"
          title="Contact Details"
          subtitle="Channels for communication."
          startContent={<PhoneIcon weight="thin" size={34} />}
        >
          {user?.email && (
            <>
              <div className="info-row">
                <div className="info-row-title">E-mail:</div>
                <div className="info-row-content whitespace-pre-wrap">
                  <Link isExternal href={`mailto:${user?.email}`}>
                    {user?.email}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {user?.mobileNumber && (
            <>
              <div className="info-row">
                <div className="info-row-title">
                  {user?.mobileNumber2 ? "Cell 1" : "Cell"}
                </div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${user?.mobileNumber}`}>
                    {user?.mobileNumber}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {user?.mobileNumber2 && (
            <>
              <div className="info-row">
                <div className="info-row-title">Cell 2:</div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${user?.mobileNumber2}`}>
                    {user?.mobileNumber2}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {user?.telNumber && (
            <>
              <div className="info-row">
                <div className="info-row-title">Tel:</div>
                <div className="info-row-content">
                  <Link isExternal href={`tel:${user?.telNumber}`}>
                    {user?.telNumber}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
        </AccordionItem>
        <AccordionItem
          key="3"
          aria-label="Online Presence"
          title="Online Presence"
          subtitle="Professional or social links."
          startContent={<ShareNetworkIcon weight="thin" size={34} />}
        >
          {user?.whatsapp && (
            <>
              <div className="info-row">
                <div className="info-row-title">WhatsApp:</div>
                <div className="info-row-content">
                  <Link isExternal href={sanitizeForWhatsapp(user?.whatsapp)}>
                    {user?.whatsapp}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {user?.linkedin && (
            <>
              <div className="info-row">
                <div className="info-row-title">LinkedIn:</div>
                <div className="info-row-content">
                  <Link isExternal href={user?.linkedin}>
                    {user?.linkedin}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
          {user?.facebook && (
            <>
              <div className="info-row">
                <div className="info-row-title">Facebook:</div>
                <div className="info-row-content">
                  <Link isExternal href={user?.facebook}>
                    {user?.facebook}
                  </Link>
                </div>
              </div>
              <Spacer x={4} y={4} />
            </>
          )}
        </AccordionItem>
        <AccordionItem
          key="4"
          aria-label="Account Information"
          title="Account Information"
          subtitle="Information of the user account."
          startContent={<ChalkboardTeacherIcon weight="thin" size={34} />}
        >
          <div className="info-row">
            <div className="info-row-title">Username:</div>
            <div className="info-row-content">{user?.username}</div>
          </div>
          <Spacer x={4} y={4} />
          <div className="info-row">
            <div className="info-row-title">Roles:</div>
            <div className="info-row-content">
              <ul className="list-decimal list-inside">
                {user?.roles?.map((role) => (
                  <li>{role}</li>
                ))}
              </ul>
            </div>
          </div>
          <Spacer x={4} y={4} />
          <div className="info-row">
            <div className="info-row-title">Verified:</div>
            <div className="info-row-content">
              {user?.verified ? CheckMark : XMark}
            </div>
          </div>
          <Spacer x={4} y={4} />
          <div className="info-row">
            <div className="info-row-title">Enabled:</div>
            <div className="info-row-content">
              {user?.enabled ? CheckMark : XMark}
            </div>
          </div>
          <Spacer x={4} y={4} />
          <div className="info-row">
            <div className="info-row-title">User to change password:</div>
            <div className="info-row-content">
              {user?.changePassword ? CheckMark : XMark}
            </div>
          </div>
          <Spacer x={4} y={4} />
        </AccordionItem>
      </Accordion>
    </div>
  );
}
