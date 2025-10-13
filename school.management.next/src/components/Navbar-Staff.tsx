import React, { useEffect } from "react";
import MyNavbar, { MyDropdown } from "./MyNavbar";
import LoginModal from "./LoginModal";
import RegisterModal from "./RegisterModal";
import { useRouter } from "next/router";
import { useSelector } from "react-redux";

export default function Navbar_Staff() {
  const { token, user } = useSelector((state: any) => state.auth);
  const [rawMenuItems, setRawMenuItems] = React.useState<MyDropdown[]>([]);
  const [menuItems, setMenuItems] = React.useState<MyDropdown[]>([]);
  const router = useRouter();

  const isItSelected = (url: string) => {
    if (router.pathname === url) {
      return true;
    }
    return false;
  };

  useEffect(() => {
    const genralItems: MyDropdown[] = [
      {
        label: "Subjects",
        dropdown: [
          { label: "All", href: "/subject/all", className: "" },
          { label: " Subject Years", href: "/subject/year/all", className: "" },
        ],
      },
      {
        label: "Classrooms",
        dropdown: [
          { label: "All", href: "/classroom/all", className: "" },
          {
            label: "Classroom Years",
            href: "/classroom/year/all",
            className: "",
          },
        ],
      },
      {
        label: "Dorms",
        dropdown: [
          { label: "All", href: "/dorm/all", className: "" },
          { label: "Dorm Years", href: "/dorm/year/all", className: "" },
        ],
      },
      {
        label: "Extra Curriculars",
        dropdown: [
          {
            label: "All",
            href: "/extra-curricular/all",
            className: "",
          },
          {
            label: "ECA Years",
            href: "/extra-curricular/year/all",
            className: "",
          },
        ],
      },
      { label: "Students", href: "/student/all", className: "" },
    ];

    const protectedItems: MyDropdown[] = [
      { label: "Teachers", href: "/teacher/all", className: "" },
      { label: "Users", href: "/user/all", className: "" },
    ];

    const reportItems: MyDropdown[] = [
      {
        label: "Reports",
        href: "/reports",
        className: "",
      },
    ];

    const rawItems: MyDropdown[] = [
      ...genralItems,
      ...(user?.roles?.includes("ADMIN") ? protectedItems : []),
      ...reportItems,
    ];

    setRawMenuItems(rawItems);
  }, [user]);

  useEffect(() => {
    setMenuItems(
      rawMenuItems.map((item) => ({
        ...item,
        isActive: item.href ? isItSelected(item.href) : undefined,
        dropdown: item.dropdown
          ? item.dropdown.map((sub) => ({
              ...sub,
              isActive: sub.href ? isItSelected(sub.href) : undefined,
            }))
          : undefined,
      }))
    );
  }, [rawMenuItems]);

  return (
    <>
      <MyNavbar menuItems={menuItems} hasAvatar={true} />
    </>
  );
}
