import React from "react";
import MyNavbar, { MyDropdown } from "./MyNavbar";
import LoginModal from "./LoginModal";
import RegisterModal from "./RegisterModal";

export default function Navbar_Parent({
  darkMode,
  setLoading,
}: {
  darkMode: boolean;
  setLoading: (loading: boolean) => void;
}) {

  const menuItems: MyDropdown[] = [
  { label: "Home", href: "/", className: "" },
  { label: "My Children", href: "/my-children", className: "" },
  { label: "Attendance", href: "/attendance", className: "" },
  { label: "Grades", href: "/grades", className: "" },
  { label: "Timetable", href: "/timetable", className: "" },
  { label: "Extra Curricular", href: "/extra-curricular", className: "" }
];

  return (
    <>
      <MyNavbar darkMode={darkMode} menuItems={menuItems} hasAvatar={true} />
    </>
  );
}
