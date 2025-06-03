import React from "react";
import { Avatar, Dropdown, DropdownMenu, DropdownItem, DropdownTrigger } from "@heroui/react";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../store/slices/authSlice";
import { getInitials } from "@/utils/helpers";

export default function AvatarDropdown() {
  const dispatch = useDispatch();
  const user = useSelector((state: any) => state.auth.user);

  return (
    <Dropdown className={` `}>
      <DropdownTrigger>
        <Avatar
          src={user?.avatarUrl}
          name={getInitials(user?.name || "User")}
          size="md"
          color="primary"
          className="cursor-pointer"
        >
          {!user?.avatarUrl && getInitials(user?.person?.name || "User")}
        </Avatar>
      </DropdownTrigger>
      <DropdownMenu className={`text-foreground `}>
        <DropdownItem as="a" href="/profile" key={"profile"}>
          Profile
        </DropdownItem>
        <DropdownItem as="a" href="/settings" key={"settings"}>
          Settings
        </DropdownItem>
        <DropdownItem color="danger" onClick={() => dispatch(logout())} key={"logout"}>
          Logout
        </DropdownItem>
      </DropdownMenu>
    </Dropdown>
  );
}