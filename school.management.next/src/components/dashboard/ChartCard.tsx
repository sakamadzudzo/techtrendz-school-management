import React from "react";
import { Card } from "@heroui/react";

interface ChartCardProps {
  title: string;
  children: React.ReactNode;
  action?: React.ReactNode;
}

export default function ChartCard({ title, children, action }: ChartCardProps) {
  return (
    <Card className="p-6 shadow-md">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-gray-900 dark:text-white">{title}</h3>
        {action}
      </div>
      <div className="w-full">{children}</div>
    </Card>
  );
}
