import React from "react";
import ParentDashboard from "./dashboard/ParentDashboard";

export default function ParentLanding() {
  return (
    <div className="max-w-7xl mx-auto p-4">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Parent Dashboard</h1>
      <ParentDashboard />
    </div>
  );
}
