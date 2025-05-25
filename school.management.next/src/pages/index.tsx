import { Card, CardHeader, CardBody, CardFooter, Button } from "@heroui/react";
import Head from "next/head";

export default function Home() {
  return (
    <>
      <Head>
        <title>Welcome | TechTrendz School Management System</title>
        <meta name="description" content="TechTrendz School Management System" />
      </Head>
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-blue-100 dark:from-gray-900 dark:to-gray-800">
        <Card className="max-w-lg w-full shadow-xl">
          <CardHeader className="text-center">
            <h1 className="text-3xl font-bold text-blue-700 dark:text-blue-300 mb-2">
              Welcome to TechTrendz School Management System!
            </h1>
          </CardHeader>
          <CardBody>
            <div className="space-y-4 text-gray-700 dark:text-gray-200">
              <div>
                <h2 className="font-semibold text-lg mb-1">For Teachers & Staff</h2>
                <ul className="list-disc list-inside ml-2">
                  <li>Register students</li>
                  <li>Assign subjects, classes, extracurricular activities, and dorms</li>
                  <li>Record marks and track progress</li>
                  <li>View comprehensive reports</li>
                </ul>
              </div>
              <div>
                <h2 className="font-semibold text-lg mb-1">For Parents</h2>
                <ul className="list-disc list-inside ml-2">
                  <li>View your child’s assigned subjects and activities</li>
                  <li>See class and dorm assignments</li>
                  <li>Download report cards</li>
                </ul>
              </div>
            </div>
          </CardBody>
          <CardFooter className="flex justify-center gap-4">
            <Button color="primary" variant="solid">
              Login
            </Button>
            <Button color="secondary" variant="ghost">
              Learn More
            </Button>
          </CardFooter>
        </Card>
      </div>
    </>
  );
}