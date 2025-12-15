import React from "react";
import { Card, Button, Link } from "@heroui/react";

interface ListItem {
  id: string;
  label: string;
  subtitle?: string;
  value?: string;
}

interface ListCardProps {
  title: string;
  items: ListItem[];
  action?: { label: string; href: string };
  maxItems?: number;
}

export default function ListCard({ title, items, action, maxItems = 5 }: ListCardProps) {
  const displayItems = items.slice(0, maxItems);

  return (
    <Card className="p-6 shadow-md">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-gray-900 dark:text-white">{title}</h3>
        {action && (
          <Button as={Link} href={action.href} size="sm" variant="light" color="primary">
            {action.label}
          </Button>
        )}
      </div>
      <div className="space-y-3">
        {displayItems.map((item) => (
          <div key={item.id} className="flex items-center justify-between py-2 border-b border-gray-100 dark:border-gray-800 last:border-0">
            <div className="flex-1">
              <p className="text-sm font-medium text-gray-900 dark:text-white">{item.label}</p>
              {item.subtitle && <p className="text-xs text-gray-500 dark:text-gray-400">{item.subtitle}</p>}
            </div>
            {item.value && <span className="text-sm font-semibold text-primary-600">{item.value}</span>}
          </div>
        ))}
      </div>
    </Card>
  );
}
