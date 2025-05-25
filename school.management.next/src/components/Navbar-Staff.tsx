import React from "react";
import MyNavbar, { MyDropdown } from "./MyNavbar";
import LoginModal from "./LoginModal";
import RegisterModal from "./RegisterModal";

export default function Navbar_Staff({
  darkMode,
  setLoading,
}: {
  darkMode: boolean;
  setLoading: (loading: boolean) => void;
}) {

  const menuItems: MyDropdown[] = [
  { label: "Subject", href: "/subject", className: "" },
  { label: "Subject Year", href: "/subject-year", className: "" },
  { label: "Classroom", href: "/classroom", className: "" },
  { label: "Classroom Year", href: "/classroom-year", className: "" },
  { label: "Dorm", href: "/dorm", className: "" },
  { label: "Dorm Year", href: "/dorm-year", className: "" },
  { label: "Student", href: "/student", className: "" },
  { label: "Extra Curriculars", href: "/extra-curriculars", className: "" },
  { label: "Teachers", href: "/teachers", className: "" },
  { label: "Users", href: "/users", className: "" }, // Only show if admin (handle in MyNavbar or here)
  {
    label: "Reports",
    dropdown: [
      { label: "By Classroom Year", href: "/reports/classroom-year", className: "" },
      { label: "By Subject", href: "/reports/subject", className: "" },
      { label: "By Extra Curricular", href: "/reports/extra-curricular", className: "" },
      { label: "By Year", href: "/reports/year", className: "" },
      { label: "By Teacher", href: "/reports/teacher", className: "" },
      { label: "By Student", href: "/reports/student", className: "" },
    ],
  },
];

  return (
    <>
      <MyNavbar darkMode={darkMode} menuItems={menuItems} hasAvatar={true} />
    </>
  );
}
