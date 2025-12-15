import React from "react";
import MetricCard from "./MetricCard";
import ChartCard from "./ChartCard";
import QuickActions from "./QuickActions";
import ListCard from "./ListCard";
import BarChart from "./charts/BarChart";
import LineChart from "./charts/LineChart";
import { useStudentsAll } from "@/hooks/useStudents";
import { useTeachersAll } from "@/hooks/useTeachers";
import { useClassroomsAll } from "@/hooks/useClassrooms";
import { useSubjectsAll } from "@/hooks/useSubjects";
import { useExtraCurricularsAll } from "@/hooks/useExtraCurriculars";

export default function AdminDashboard() {
  const { students } = useStudentsAll({ page: 1, size: 1000 });
  const { teachers } = useTeachersAll({ page: 1, size: 1000 });
  const { data: classrooms } = useClassroomsAll({ page: 1, size: 1000 });
  const { subjects } = useSubjectsAll({ page: 1, size: 1000 });
  const { extraCurriculars } = useExtraCurricularsAll({ page: 1, size: 1000 });

  const quickActions = [
    { label: "Add Student", href: "/student/edit", color: "primary" as const },
    { label: "Add Teacher", href: "/teacher/edit", color: "primary" as const },
    {
      label: "Create Classroom",
      href: "/classroom/edit",
      color: "secondary" as const,
    },
    {
      label: "Generate Reports",
      href: "/reports",
      color: "secondary" as const,
    },
    { label: "Manage Users", href: "/user/all", color: "success" as const },
    { label: "View Schools", href: "/school/all", color: "success" as const },
  ];

  const recentActivity = [
    {
      id: "1",
      label: "New student enrolled",
      subtitle: "2 hours ago",
      value: "",
    },
    {
      id: "2",
      label: "Coursework submitted",
      subtitle: "3 hours ago",
      value: "",
    },
    { id: "3", label: "Teacher added", subtitle: "5 hours ago", value: "" },
    { id: "4", label: "Report generated", subtitle: "1 day ago", value: "" },
  ];

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <MetricCard
          title="Total Students"
          value={students?.content?.length || 0}
          subtitle="Enrolled students"
        />
        <MetricCard
          title="Total Teachers"
          value={teachers?.content?.length || 0}
          subtitle="Active teachers"
        />
        <MetricCard
          title="Classrooms"
          value={classrooms?.length || 0}
          subtitle="Active classrooms"
        />
        <MetricCard
          title="Subjects"
          value={subjects?.content?.length || 0}
          subtitle="Offered subjects"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ChartCard title="Subject Performance">
          <BarChart
            labels={["Math", "Science", "English", "History", "Art"]}
            datasets={[{ label: "Average Marks", data: [75, 82, 68, 71, 88] }]}
          />
        </ChartCard>

        <ChartCard title="Performance Trends">
          <LineChart
            labels={["Jan", "Feb", "Mar", "Apr", "May", "Jun"]}
            datasets={[
              { label: "Average Grade", data: [70, 72, 75, 73, 78, 80] },
            ]}
          />
        </ChartCard>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ListCard
          title="Recent Activity"
          items={recentActivity}
          action={{ label: "View All", href: "#" }}
        />
        <QuickActions actions={quickActions} />
      </div>
    </div>
  );
}
