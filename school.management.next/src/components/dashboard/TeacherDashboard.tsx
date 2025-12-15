import React from "react";
import MetricCard from "./MetricCard";
import ChartCard from "./ChartCard";
import QuickActions from "./QuickActions";
import ListCard from "./ListCard";
import BarChart from "./charts/BarChart";
import PieChart from "./charts/PieChart";
import { useSelector } from "react-redux";
import { RootState } from "@/store/store";

export default function TeacherDashboard() {
  const user = useSelector((state: RootState) => state.auth.user);

  const quickActions = [
    { label: "Create Coursework", href: "/subject/one", color: "primary" as const },
    { label: "Enter Marks", href: "/subject/all", color: "primary" as const },
    { label: "View Students", href: "/student/all", color: "secondary" as const },
    { label: "Generate Report", href: "/reports", color: "secondary" as const },
  ];

  const pendingTasks = [
    { id: "1", label: "Grade Math Test", subtitle: "Class 10A", value: "15 pending" },
    { id: "2", label: "Review Assignments", subtitle: "Class 9B", value: "8 pending" },
    { id: "3", label: "Update Marks", subtitle: "Science", value: "12 pending" },
  ];

  const recentSubmissions = [
    { id: "1", label: "John Doe - Math Assignment", subtitle: "2 hours ago", value: "" },
    { id: "2", label: "Jane Smith - Science Project", subtitle: "4 hours ago", value: "" },
    { id: "3", label: "Bob Johnson - English Essay", subtitle: "1 day ago", value: "" },
  ];

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <MetricCard title="My Classes" value={3} subtitle="Active classes" />
        <MetricCard title="Total Students" value={85} subtitle="Across all classes" />
        <MetricCard title="Subjects" value={2} subtitle="Teaching" />
        <MetricCard title="Pending Tasks" value={35} subtitle="Awaiting grading" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ListCard
          title="Pending Tasks"
          items={pendingTasks}
          action={{ label: "View All", href: "/subject/all" }}
        />
        <QuickActions actions={quickActions} />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ChartCard title="Class Performance">
          <BarChart
            labels={["Class 10A", "Class 10B", "Class 9A"]}
            datasets={[{ label: "Average Marks", data: [78, 82, 75] }]}
          />
        </ChartCard>

        <ChartCard title="Grade Distribution">
          <PieChart labels={["A", "B", "C", "D", "F"]} data={[25, 35, 20, 15, 5]} />
        </ChartCard>
      </div>

      <ListCard
        title="Recent Submissions"
        items={recentSubmissions}
        action={{ label: "View All", href: "#" }}
      />
    </div>
  );
}
