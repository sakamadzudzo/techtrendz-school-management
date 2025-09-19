import GuestLanding from "@/components/GuestLanding";
import ParentLanding from "@/components/ParentLanding";
import StaffLanding from "@/components/StaffLanding";
import { Role, User } from "@/utils/types";
import { Select, SelectItem } from "@heroui/react";
import Head from "next/head";
import React from "react";
import { useSelector } from "react-redux";

export default function Home() {
  const user: User = useSelector((state: any) => state.auth.user);
  const [role, setRole] = React.useState<Role>(Role.GUEST);

  React.useEffect(() => {
    if (user && user?.roles && user?.roles?.length) {
      setRole(user?.roles[0] as Role);
    } else {
      setRole(Role.GUEST);
    }
  }, [user]);

  const landing = React.useMemo(() => {
    switch (role) {
      case Role.GUEST:
        return <GuestLanding />;
      case Role.PARENT:
        return <ParentLanding />;
      case Role.ADMIN:
      case Role.STAFF:
      case Role.TEACHER:
        return <StaffLanding />;
      default:
        return <GuestLanding />;
    }
  }, [user, role]);

  return (
    <>
      <Head>
        <title>Welcome | TechTrendz School Management System</title>
        <meta
          name="description"
          content="TechTrendz School Management System"
        />
      </Head>
      <div>
        <Select name="role" id="role" selectedKeys={[role]}>
          {user?.roles?.map((role: any) => (
            <SelectItem key={role}>{role}</SelectItem>
          ))}
        </Select>
        {landing}
      </div>
    </>
  );
}
