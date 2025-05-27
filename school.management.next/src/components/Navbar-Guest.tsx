import React from "react";
import MyNavbar, { MyDropdown } from "./MyNavbar";
import LoginModal from "./LoginModal";
import RegisterModal from "./RegisterModal";
import { useSelector } from "react-redux";

export default function Navbar_Guest({
  setLoading,
}: {
  setLoading: (loading: boolean) => void;
}) {
  const [loginOpen, setLoginOpen] = React.useState(false);
  const [registerOpen, setRegisterOpen] = React.useState(false);
  const { token, user } = useSelector((state: any) => state.auth);

  //   const menuItems: MyDropdown[] = [
  //     { label: "Home", href: "/", className: "" },
  //     { label: "About", href: "/about", className: "" },
  //     { label: "Contact", href: "/contact", className: "" },
  //     {
  //       label: "What we offer",
  //       dropdown: [
  //         { label: "Core", dropdown: [
  //             {label: "Math", href: "/offer/core/math", className: "" },
  //             {label: "Science", href: "/offer/core/science", className: "" },
  //             {label: "Language", href: "/offer/core/language", className: "" },
  //         ] },
  //         { label: "Elective", href: "/offer/elective", className: "" },
  //         { label: "AP", href: "/offer/ap", className: "" },
  //         { label: "Physical", href: "/offer/physical", className: "" },
  //         { label: "Extra curricular", href: "/offer/extracurricular", className: "" },
  //       ],
  //     },
  //     { label: "Login", href: "/login", className: "font-semibold" },
  //     { label: "Register", href: "/register", className: "font-semibold" }
  //   ];

  const menuItems: MyDropdown[] = [
    { label: "Home", href: "/", className: "" },
    { label: "Guest", href: "/", className: "" },
    { label: "About", href: "/about", className: "" },
    { label: "Contact", href: "/contact", className: "" },
    {
      label: "What we offer",
      dropdown: [
        { label: "Core", href: "/offer/core", className: "" },
        { label: "Elective", href: "/offer/elective", className: "" },
        { label: "AP", href: "/offer/ap", className: "" },
        { label: "Physical", href: "/offer/physical", className: "" },
        {
          label: "Extra curricular",
          href: "/offer/extracurricular",
          className: "",
        },
      ],
    },
    {
      label: "Login",
      href: "#",
      asButton: true,
      color: "primary",
      variant: "solid",
      onClick: () => setLoginOpen(true),
    },
    {
      label: "Register",
      href: "#",
      asButton: true,
      color: "primary",
      variant: "solid",
      onClick: () => setRegisterOpen(true),
    },
  ];

  return (
    <>
      <LoginModal
        isOpen={loginOpen}
        onOpenChange={setLoginOpen}
        setLoading={setLoading}
      />
      <RegisterModal
        isOpen={registerOpen}
        onOpenChange={setRegisterOpen}
        setLoading={setLoading}
      />
      <MyNavbar menuItems={menuItems} />
    </>
  );
}
