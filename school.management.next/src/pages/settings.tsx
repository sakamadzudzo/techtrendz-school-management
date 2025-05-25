import React, { useState } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Input,
  Button,
  Divider,
} from "@heroui/react";

export default function Settings() {
  const [current, setCurrent] = useState("");
  const [newPass, setNewPass] = useState("");
  const [confirm, setConfirm] = useState("");
  const [message, setMessage] = useState<string | null>(null);

  const handleChangePassword = (e: React.FormEvent) => {
    e.preventDefault();
    setMessage(null);

    if (newPass !== confirm) {
      setMessage("New passwords do not match.");
      return;
    }
    // TODO: Call API to change password
    setMessage("Password changed successfully!");
    setCurrent("");
    setNewPass("");
    setConfirm("");
  };

  return (
    <div className="max-w-xl mx-auto mt-10 space-y-8">
      <Card>
        <CardHeader>
          <p className="text-xl font-bold">Settings</p>
        </CardHeader>
        <Divider />
        <CardBody>
          {/* Change Password Section */}
          <section>
            <p className="text-lg font-semibold mb-4">Change Password</p>
            <form onSubmit={handleChangePassword} className="space-y-4">
              <Input
                label="Current Password"
                type="password"
                value={current}
                onValueChange={setCurrent}
                required
              />
              <Input
                label="New Password"
                type="password"
                value={newPass}
                onValueChange={setNewPass}
                required
              />
              <Input
                label="Confirm New Password"
                type="password"
                value={confirm}
                onValueChange={setConfirm}
                required
              />
              <Button type="submit" color="primary">
                Change Password
              </Button>
              {message && (
                <p className="text-danger mt-2">{message}</p>
              )}
            </form>
          </section>
        </CardBody>
      </Card>

      {/* Add more settings sections below as needed */}
      <Card>
        <CardHeader>
          <p className="text-lg font-semibold">Other Settings</p>
        </CardHeader>
        <Divider />
        <CardBody>
          <p className="text-neutral-500">More settings coming soon...</p>
        </CardBody>
      </Card>
    </div>
  );
}