import React from "react";
import { Button, Link, Card } from "@heroui/react";

const dashboardCards = [
  {
    label: "Pending Grading Tasks",
    action: { label: "Grade Now", href: "#" },
  },
  {
    label: "Recently Submitted Work",
    action: { label: "Review Submissions", href: "#" },
  },
  {
    label: "Class Overview",
    action: { label: "View Class Reports", href: "#" },
  },
];

export default function StaffLanding() {
  return (
    <div className="max-w-3xl mx-auto p-4 space-y-8">
      <header className="text-center space-y-2">
        <h1 className="text-3xl font-bold">Empowering You to Guide Every Student</h1>
      </header>

      {/* Quick Snapshot */}
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

      {/* Management Section */}
      <section className="bg-gray-50 rounded-lg p-6 text-center space-y-2">
        <div className="font-medium">
          Easily assign work, track submissions, and update student records.
        </div>
        <div className="space-x-2">
          <Button as={Link} color="secondary" href="#" variant="flat">Assign Work</Button>
          <Button as={Link} color="secondary" href="#" variant="flat">Update Grades</Button>
        </div>
      </section>

      {/* Insights Section */}
      <section className="bg-gray-50 rounded-lg p-6 text-center space-y-2">
        <div>Monitor student performance across subjects and activities.</div>
        <Button as={Link} color="primary" href="#" variant="ghost">
          Generate Reports
        </Button>
      </section>

      {/* Collaboration Section */}
      <section className="bg-gray-100 rounded-lg p-6 text-center space-y-2">
        <div>Engage with parents and colleagues for better outcomes.</div>
        <div className="space-x-4">
          <Link href="#" color="secondary" underline>
            Send Updates to Parents
          </Link>
          <Link href="#" color="secondary" underline>
            Team Collaboration
          </Link>
        </div>
      </section>
    </div>
  );
}
