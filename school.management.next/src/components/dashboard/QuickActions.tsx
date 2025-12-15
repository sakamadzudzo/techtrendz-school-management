import React from "react";
import { Card, Button, Link } from "@heroui/react";

interface Action {
  label: string;
  href: string;
  icon?: React.ReactNode;
  color?: "primary" | "secondary" | "success" | "warning" | "danger";
}

interface QuickActionsProps {
  title?: string;
  actions: Action[];
}

export default function QuickActions({ title = "Quick Actions", actions }: QuickActionsProps) {
  return (
    <Card className="p-6 shadow-md">
      <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">{title}</h3>
      <div className="grid grid-cols-2 gap-3">
        {actions.map((action, idx) => (
          <Button
            key={idx}
            as={Link}
            href={action.href}
            color={action.color || "primary"}
            variant="flat"
            className="justify-start"
            startContent={action.icon}
          >
            {action.label}
          </Button>
        ))}
      </div>
    </Card>
  );
}
