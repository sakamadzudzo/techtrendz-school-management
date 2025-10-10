import React from "react";
import { Button, Link, Card } from "@heroui/react";

const dashboardCards = [
  {
    label: "Child’s Current Average Grade",
    action: { label: "View Full Report", href: "#" },
  },
  {
    label: "Upcoming Assignments",
    action: { label: "View Details", href: "#" },
  },
  {
    label: "Recent Teacher Feedback",
    action: { label: "Read More", href: "#" },
  },
];

export default function ParentLanding() {
  return (
    <div className="max-w-3xl mx-auto p-4 space-y-8">
      <header className="text-center space-y-2">
        <h1 className="text-3xl font-bold">Stay Connected to Your Child’s Learning Journey</h1>
      </header>

      {/* Dashboard Snapshot */}
      <section className="grid md:grid-cols-3 gap-4">
        {dashboardCards.map((card) => (
          <Card key={card.label} className="flex flex-col items-center py-6">
            <div className="text-lg font-semibold mb-4">{card.label}</div>
            <Button as={Link} color="primary" href={card.action.href} variant="flat">
              {card.action.label}
            </Button>
          </Card>
        ))}
      </section>

      {/* Insights */}
      <section className="bg-gray-50 rounded-lg p-6 text-center space-y-2">
        <div className="font-medium">Track progress by subject, class, or activity</div>
        <Button as={Link} color="secondary" href="#" variant="ghost">
          View Progress Reports
        </Button>
      </section>

      {/* Engagement */}
      <section className="bg-gray-50 rounded-lg p-6 text-center space-y-2">
        <div>Download detailed reports or view performance graphs.</div>
        <div className="space-x-2">
          <Button as={Link} color="primary" href="#" variant="flat">
            Download Report
          </Button>
          <Button as={Link} color="primary" href="#" variant="flat">
            View Graphs
          </Button>
        </div>
      </section>

      {/* Support */}
      <section className="bg-gray-100 rounded-lg p-6 text-center space-y-2">
        <div>Have questions about your child’s progress?</div>
        <div className="space-x-4">
          <Link href="#" color="secondary" underline>
            Contact Teacher
          </Link>
          <Link href="#" color="secondary" underline>
            Help & Support
          </Link>
        </div>
      </section>
    </div>
  );
}
