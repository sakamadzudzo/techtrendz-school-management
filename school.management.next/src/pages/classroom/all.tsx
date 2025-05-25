import MyTable from "@/components/MyTable";
import React from "react";

export default function AllClassroomsPage() {
  const columns = [
    { name: "Name", uid: "name", sortable: true },
    { name: "Email", uid: "email", sortable: true },
    { name: "Role", uid: "role" },
    { name: "Status", uid: "status" },
    { name: "Actions", uid: "actions" },
  ];

  const users = [
    {
      id: 1,
      name: "Alice Johnson",
      email: "alice.johnson@example.com",
      role: "Admin",
      status: "Active",
    },
    {
      id: 2,
      name: "Bob Smith",
      email: "bob.smith@example.com",
      role: "Teacher",
      status: "Inactive",
    },
    {
      id: 3,
      name: "Carol Lee",
      email: "carol.lee@example.com",
      role: "Student",
      status: "Active",
    },
    {
      id: 4,
      name: "Alice Johnson",
      email: "alice.johnson@example.com",
      role: "Admin",
      status: "Active",
    },
    {
      id: 5,
      name: "Bob Smith",
      email: "bob.smith@example.com",
      role: "Teacher",
      status: "Inactive",
    },
    {
      id: 6,
      name: "Carol Lee",
      email: "carol.lee@example.com",
      role: "Student",
      status: "Active",
    },
    {
      id: 7,
      name: "Alice Johnson",
      email: "alice.johnson@example.com",
      role: "Admin",
      status: "Active",
    },
    {
      id: 8,
      name: "Bob Smith",
      email: "bob.smith@example.com",
      role: "Teacher",
      status: "Inactive",
    },
    {
      id: 9,
      name: "Carol Lee",
      email: "carol.lee@example.com",
      role: "Student",
      status: "Active",
    },
    {
      id: 10,
      name: "Alice Johnson",
      email: "alice.johnson@example.com",
      role: "Admin",
      status: "Active",
    },
    {
      id: 11,
      name: "Bob Smith",
      email: "bob.smith@example.com",
      role: "Teacher",
      status: "Inactive",
    },
    {
      id: 12,
      name: "Carol Lee",
      email: "carol.lee@example.com",
      role: "Student",
      status: "Active",
    },
  ];

  const [searchResults, setSearchResults] = React.useState(users);
  const [page, setPage] = React.useState(1);

  const handleSearch = (value: string) => {
    if (!value) {
      setSearchResults(users);
      return;
    }
    setSearchResults(
      users.filter(
        (user) =>
          user.name.toLowerCase().includes(value.toLowerCase()) ||
          user.email.toLowerCase().includes(value.toLowerCase()) ||
          user.role.toLowerCase().includes(value.toLowerCase()) ||
          user.status.toLowerCase().includes(value.toLowerCase())
      )
    );
  };

  const statusOptions = [
    "ACTIVE",
    "INACTIVE",
    "SUSPENDED",
    "DELETED",
    "PENDING",
    "APPROVED",
    "REJECTED",
    "BLOCKED",
    "UNBLOCKED",
    "ENABLED",
    "DISABLED",
  ].sort();

  return <MyTable setPage={setPage} statusOptions={statusOptions} />;
}
