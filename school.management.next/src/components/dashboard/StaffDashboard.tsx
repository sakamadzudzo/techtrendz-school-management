import React from "react";
import MetricCard from "./MetricCard";
import ChartCard from "./ChartCard";
import QuickActions from "./QuickActions";
import ListCard from "./ListCard";
import BarChart from "./charts/BarChart";
import { useStudentsAll } from "@/hooks/useStudents";
import { useClassroomsAll } from "@/hooks/useClassrooms";
import { useDormsAll } from "@/hooks/useDorms";

export default function StaffDashboard() {
  const { students } = useStudentsAll({ page: 1, size: 1000 });
  const { data: classrooms } = useClassroomsAll({ page: 1, size: 1000 });
  const { dorms } = useDormsAll({ page: 1, size: 1000 });

  const quickActions = [
    { label: "Add Student", href: "/student/edit", color: "primary" as const },
    { label: "Add Parent", href: "/parent/edit", color: "primary" as const },
    {
      label: "Manage Classrooms",
      href: "/classroom/all",
      color: "secondary" as const,
    },
    { label: "Manage Dorms", href: "/dorm/all", color: "secondary" as const },
    {
      label: "Extra-Curriculars",
      href: "/extra-curricular/all",
      color: "success" as const,
    },
    { label: "Generate Reports", href: "/reports", color: "success" as const },
  ];

  const recentChanges = [
    {
      id: "1",
      label: "New student added",
      subtitle: "John Smith - Grade 10",
      value: "1h ago",
    },
    {
      id: "2",
      label: "Classroom updated",
      subtitle: "Grade 9A capacity changed",
      value: "3h ago",
    },
    {
      id: "3",
      label: "Parent record updated",
      subtitle: "Contact info changed",
      value: "5h ago",
    },
    {
      id: "4",
      label: "Dorm assignment",
      subtitle: "Student moved to Dorm B",
      value: "1d ago",
    },
  ];

  const dataTasks = [
    {
      id: "1",
      label: "Incomplete student profiles",
      subtitle: "Missing contact info",
      value: "12",
    },
    {
      id: "2",
      label: "Unassigned students",
      subtitle: "No classroom assigned",
      value: "5",
    },
    {
      id: "3",
      label: "Missing parent info",
      subtitle: "No guardian linked",
      value: "8",
    },
  ];

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <MetricCard
          title="Total Students"
          value={students?.content?.length || 0}
          subtitle="Enrolled"
        />
        <MetricCard
          title="Classrooms"
          value={classrooms?.length || 0}
          subtitle="Active"
        />
        <MetricCard
          title="Dorms"
          value={dorms?.content?.length || 0}
          subtitle="Available"
        />
        <MetricCard
          title="New This Month"
          value={15}
          subtitle="Enrollments"
          trend={{ value: 10, isPositive: true }}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ChartCard title="Students by Classroom">
          <BarChart
            labels={["Grade 10A", "Grade 10B", "Grade 9A", "Grade 9B"]}
            datasets={[{ label: "Students", data: [28, 32, 25, 30] }]}
          />
        </ChartCard>

        <ListCard
          title="Data Entry Tasks"
          items={dataTasks}
          action={{ label: "View All", href: "#" }}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ListCard
          title="Recent Changes"
          items={recentChanges}
          action={{ label: "View All", href: "#" }}
        />
        <QuickActions actions={quickActions} />
      </div>
    </div>
  );
}
