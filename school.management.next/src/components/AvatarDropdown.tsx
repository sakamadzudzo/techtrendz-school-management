import React from "react";
import { Avatar, Dropdown, DropdownMenu, DropdownItem, DropdownTrigger } from "@heroui/react";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../store/slices/authSlice"; // Adjust path as needed

export default function AvatarDropdown({ darkMode }: { darkMode: boolean }) {
  const dispatch = useDispatch();
  const user = useSelector((state: any) => state.auth.user);

  const getInitials = (name: string) => {
    if (!name) return "U";
    return name
      .split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
      .slice(0, 2);
  };

  return (
    <Dropdown className={` ${darkMode ? "dark" : ""}`}>
      <DropdownTrigger>
        <Avatar
          src={user?.avatarUrl}
          name={user?.name}
          size="md"
          color="primary"
          className="cursor-pointer"
        >
          {!user?.avatarUrl && getInitials(user?.name || "User")}
        </Avatar>
      </DropdownTrigger>
      <DropdownMenu className={`text-foreground ${darkMode ? "dark" : ""}`}>
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