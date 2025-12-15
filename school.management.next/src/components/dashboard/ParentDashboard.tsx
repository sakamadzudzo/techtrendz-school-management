import React from "react";
import { Card } from "@heroui/react";
import MetricCard from "./MetricCard";
import ChartCard from "./ChartCard";
import QuickActions from "./QuickActions";
import ListCard from "./ListCard";
import LineChart from "./charts/LineChart";
import { useSelector } from "react-redux";
import { RootState } from "@/store/store";

export default function ParentDashboard() {
  const user = useSelector((state: RootState) => state.auth.user);

  const quickActions = [
    { label: "View Report Card", href: "/reports", color: "primary" as const },
    { label: "Download Progress", href: "/reports", color: "primary" as const },
    { label: "View Profile", href: "/parent/one", color: "secondary" as const },
    { label: "Contact Teacher", href: "#", color: "secondary" as const },
  ];

  const recentMarks = [
    { id: "1", label: "Mathematics - Final Exam", subtitle: "Grade: A", value: "85/100" },
    { id: "2", label: "Science - Lab Report", subtitle: "Grade: B+", value: "78/100" },
    { id: "3", label: "English - Essay", subtitle: "Grade: A-", value: "82/100" },
  ];

  const upcomingAssignments = [
    { id: "1", label: "History Project", subtitle: "Due: Dec 20, 2024", value: "Pending" },
    { id: "2", label: "Math Test", subtitle: "Due: Dec 22, 2024", value: "Pending" },
  ];

  return (
    <div className="space-y-6">
      <Card className="p-6 shadow-md">
        <div className="flex items-center space-x-4">
          <div className="w-20 h-20 rounded-full bg-gradient-to-br from-blue-400 to-blue-600 flex items-center justify-center text-white text-2xl font-bold">
            JD
          </div>
          <div>
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">John Doe</h2>
            <p className="text-gray-600 dark:text-gray-400">Grade 10A</p>
            <p className="text-lg font-semibold text-primary-600 mt-1">Overall Average: 82%</p>
          </div>
        </div>
      </Card>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <MetricCard title="Current Average" value="82%" trend={{ value: 5, isPositive: true }} />
        <MetricCard title="Best Subject" value="Math" subtitle="Average: 88%" />
        <MetricCard title="Attendance" value="95%" trend={{ value: 2, isPositive: true }} />
        <MetricCard title="Activities" value={3} subtitle="Enrolled" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ChartCard title="Performance Trend">
          <LineChart
            labels={["Aug", "Sep", "Oct", "Nov", "Dec"]}
            datasets={[{ label: "Average Grade", data: [75, 78, 80, 81, 82] }]}
          />
        </ChartCard>

        <ListCard
          title="Recent Marks"
          items={recentMarks}
          action={{ label: "View All", href: "/reports" }}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ListCard
          title="Upcoming Assignments"
          items={upcomingAssignments}
          action={{ label: "View All", href: "#" }}
        />
        <QuickActions actions={quickActions} />
      </div>
    </div>
  );
}
