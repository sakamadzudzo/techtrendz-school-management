import React from "react";
import { Card } from "@heroui/react";

interface MetricCardProps {
  title: string;
  value: string | number;
  icon?: React.ReactNode;
  trend?: { value: number; isPositive: boolean };
  subtitle?: string;
}

export default function MetricCard({ title, value, icon, trend, subtitle }: MetricCardProps) {
  return (
    <Card className="p-6 shadow-md hover:shadow-lg transition-shadow">
      <div className="flex items-start justify-between">
        <div className="flex-1">
          <p className="text-sm text-gray-600 dark:text-gray-400 mb-1">{title}</p>
          <h3 className="text-3xl font-bold text-gray-900 dark:text-white">{value}</h3>
          {trend && (
            <div className={`flex items-center mt-2 text-sm ${trend.isPositive ? "text-green-600" : "text-red-600"}`}>
              <span>{trend.isPositive ? "↑" : "↓"}</span>
              <span className="ml-1">{Math.abs(trend.value)}%</span>
            </div>
          )}
          {subtitle && <p className="text-xs text-gray-500 dark:text-gray-400 mt-2">{subtitle}</p>}
        </div>
        {icon && <div className="text-3xl text-primary-500">{icon}</div>}
      </div>
    </Card>
  );
}
