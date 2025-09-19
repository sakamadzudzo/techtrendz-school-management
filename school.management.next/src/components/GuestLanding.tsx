import React from "react";
import { Button, Link, Card } from "@heroui/react";

export default function GuestLanding() {
  return (
    <div className="max-w-2xl mx-auto p-4 space-y-8">
      {/* Hero/Introduction */}
      <header className="text-center space-y-4">
        <h1 className="text-3xl font-bold mb-1">Building Bridges Between Schools, Parents, and Students</h1>
        <p className="text-gray-700">
          Our platform gives parents real-time visibility into student progress, while helping teachers manage classes and engage effectively with families.
        </p>
        <Button as={Link} color="primary" href="#" variant="solid">
          Learn More
        </Button>
      </header>

      <section className="grid md:grid-cols-2 gap-6">
        {/* For Parents */}
        <Card className="p-6 flex flex-col items-center text-center">
          <div className="font-semibold mb-2">For Parents</div>
          <div className="mb-4 text-gray-700">See your child’s progress anytime, anywhere.</div>
          <Button as={Link} color="secondary" href="#" variant="flat">
            Parent Features
          </Button>
        </Card>
        {/* For Teachers */}
        <Card className="p-6 flex flex-col items-center text-center">
          <div className="font-semibold mb-2">For Teachers</div>
          <div className="mb-4 text-gray-700">Simplify grading and reporting, and strengthen communication with parents.</div>
          <Button as={Link} color="secondary" href="#" variant="flat">
            Teacher Features
          </Button>
        </Card>
      </section>

      {/* Call to Action */}
      <section className="bg-gray-50 rounded-lg p-6 text-center space-y-2">
        <div className="font-medium mb-2">Join the future of connected learning.</div>
        <div className="space-x-2">
          <Button as={Link} color="primary" href="#" variant="solid">
            Sign Up
          </Button>
          <Button as={Link} color="default" href="#" variant="flat">
            Log In
          </Button>
        </div>
      </section>

      {/* Optional Demo/Preview */}
      <section className="bg-gray-100 rounded-lg p-6 text-center">
        <div className="mb-2">Explore a sample report.</div>
        <Button as={Link} color="secondary" href="#" variant="flat">
          View Demo Report
        </Button>
      </section>
    </div>
  );
}
