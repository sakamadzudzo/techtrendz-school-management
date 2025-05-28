import {
  Button,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownTrigger,
  Link,
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  NavbarMenu,
  NavbarMenuItem,
  NavbarMenuToggle,
} from "@heroui/react";
import React, { useEffect } from "react";
import { Logo } from "./Logo";
import AvatarDropdown from "./AvatarDropdown";

export type MyDropdown = {
  label: string;
  href?: string;
  className?: string;
  asButton?: boolean;
  color?:
    | "primary"
    | "secondary"
    | "success"
    | "warning"
    | "danger"
    | "default"
    | undefined;
  variant?: "flat" | "solid" | "light" | "ghost" | undefined;
  onClick?: () => void;
  dropdown?: MyDropdown[];
};

export default function MyNavbar({
  menuItems,
  hasAvatar
}: {
  menuItems: MyDropdown[];
  hasAvatar?: boolean;
}) {
  const [isMenuOpen, setIsMenuOpen] = React.useState(false);
  const [openDropdown, setOpenDropdown] = React.useState<string | null>(null);

  useEffect(() => {
    if (!isMenuOpen) {
      setOpenDropdown(null);
    }
  }, [isMenuOpen]);

  return (
    <Navbar
      className={`mb-2`}
      shouldHideOnScroll
      isBordered
      onMenuOpenChange={setIsMenuOpen}
    >
      <NavbarContent>
        <NavbarMenuToggle
          aria-label={isMenuOpen ? "Close menu" : "Open menu"}
          className="sm:hidden"
        />
        <NavbarBrand>
          <Logo />
          <p className="font-bold text-inherit">TechTrendz</p>
        </NavbarBrand>
      </NavbarContent>

      {/* Desktop Menu */}
      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        {renderDesktopMenu(menuItems, 0)}
      </NavbarContent>
      {hasAvatar && (
        <NavbarContent className="hidden sm:flex" justify="end">
          <AvatarDropdown />
        </NavbarContent>
      )}

      {/* Mobile Menu Toggle */}

      {/* Mobile Menu */}
      <NavbarMenu>
        {hasAvatar && (
          <div className="flex justify-end px-4 py-2">
            <AvatarDropdown />
          </div>
        )}
        {renderMobileMenu(
          menuItems,
          openDropdown,
          setOpenDropdown,
          0,
        )}
      </NavbarMenu>
    </Navbar>
  );
}

function renderDesktopMenu(
  items: MyDropdown[],
  depth = 0,
) {
  return items.map((item) =>
    item.dropdown ? (
      <Dropdown key={item.label} className={``}>
        <DropdownTrigger>
          <NavbarItem className={`cursor-pointer pl-${depth * 1}`}>
            {item.label}
          </NavbarItem>
        </DropdownTrigger>
        <DropdownMenu>
          {item.dropdown.map((sub) => (
            <DropdownItem
              key={sub.label}
              as={Link}
              href={sub.href}
              className={sub.className}
            >
              {sub.label}
            </DropdownItem>
          ))}
        </DropdownMenu>
      </Dropdown>
    ) : item.asButton ? (
      <NavbarItem key={item.label} className={item.className}>
        <Button
          as={Link}
          color={item.color}
          href={item.href}
          onClick={item.onClick}
          variant={item.variant}
        >
          {item.label}
        </Button>
      </NavbarItem>
    ) : (
      <NavbarItem
        key={item.label}
        as={Link}
        href={item.href}
        className={item.className}
      >
        {item.label}
      </NavbarItem>
    )
  );
}

function renderMobileMenu(
  items: MyDropdown[],
  openDropdown: string | null,
  setOpenDropdown: (label: string | null) => void,
  depth = 0,
) {
  return items.map((item) =>
    item.dropdown ? (
      <div key={item.label}>
        <NavbarMenuItem
          className={`cursor-pointer font-semibold pl-${depth * 4}`}
          onClick={() =>
            setOpenDropdown(openDropdown === item.label ? null : item.label)
          }
        >
          {item.label}
        </NavbarMenuItem>
        {openDropdown === item.label &&
          renderMobileMenu(
            item.dropdown,
            openDropdown,
            setOpenDropdown,
            depth + 1
          )}
      </div>
    ) : item.asButton ? (
      <NavbarItem
        key={item.label}
        className={`pl-${depth * 4} ${item.className}`}
      >
        <Button
          as={Link}
          color={item.color}
          href={item.href}
          onClick={item.onClick}
          variant={item.variant}
        >
          {item.label}
        </Button>
      </NavbarItem>
    ) : (
      <NavbarMenuItem
        key={item.label}
        as={Link}
        href={item.href}
        className={`pl-${depth * 4} ${item.className}`}
        onClick={() => setOpenDropdown(null)}
      >
        {item.label}
      </NavbarMenuItem>
    )
  );
}
