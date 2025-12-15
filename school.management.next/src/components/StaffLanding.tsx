import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "@/store/store";
import { Role } from "@/utils/types";
import AdminDashboard from "./dashboard/AdminDashboard";
import TeacherDashboard from "./dashboard/TeacherDashboard";
import StaffDashboard from "./dashboard/StaffDashboard";

export default function StaffLanding() {
  const user = useSelector((state: RootState) => state.auth.user);
  const roles = user?.roles || [];

  if (roles.includes(Role.ADMIN)) {
    return (
      <div className="max-w-7xl mx-auto p-4">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Admin Dashboard</h1>
        <AdminDashboard />
      </div>
    );
  }

  if (roles.includes(Role.TEACHER)) {
    return (
      <div className="max-w-7xl mx-auto p-4">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Teacher Dashboard</h1>
        <TeacherDashboard />
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto p-4">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Staff Dashboard</h1>
      <StaffDashboard />
    </div>
  );
}
