import React, { useEffect, useState } from "react";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  Input,
  Form,
} from "@heroui/react";
import { useDispatch } from "react-redux";
import { setCredentials } from "../store/slices/authSlice";
import { useRouter } from "next/navigation";
import InlineError from "./InlineError";

export default function LoginModal({
  isOpen,
  onOpenChange,
  setLoading
}: {
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  setLoading: (loading: boolean) => void;
}) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [statusCode, setStatusCode] = useState(0);
  const dispatch = useDispatch();
  const router = useRouter();

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    setLoading(true);

    try {
      const res = await fetch("/api/auth?action=login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: username,
          password,
          rememberMe: true,
        }),
      });

      if (!res.ok) {
        const data = await res.json();
        setError(data.error || "Login failed");
        setStatusCode(res.status || 500);
        setLoading(false);
        return;
      }

      const data = await res.json();
      const token = data.token;
      const user = data.user;
      dispatch(setCredentials({ token, user }));

      setLoading(false);
      onOpenChange(false);
      // router.push("/");
    } catch (err: any) {
      setError("Network error");
      setStatusCode(err.status || 500);
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isOpen) {
      setUsername("");
      setPassword("");
      setError(null);
      setStatusCode(0);
    }
  }, [isOpen]);

  return (
    <Modal
      isOpen={isOpen}
      onOpenChange={onOpenChange}
      hideCloseButton={false}
      backdrop="blur"
      radius="md"
      shadow="md"
    >
      <ModalContent>
        <ModalHeader className="text-xl font-bold">Login</ModalHeader>
        <ModalBody>
          <Form onSubmit={onSubmit} className="space-y-4">
            {error && (
              <InlineError
                statusCode={statusCode}
                title={error}
              />
            )}
            <Input
              isRequired
              label="Username"
              name="username"
              placeholder="Enter your username"
              type="text"
              value={username}
              onValueChange={setUsername}
              size="sm"
              autoFocus={true}
            />
            <Input
              isRequired
              label="Password"
              name="password"
              placeholder="Enter your password"
              type="password"
              value={password}
              onValueChange={setPassword}
              size="sm"
            />
            <Button type="submit" color="primary" className="w-full">
              Login
            </Button>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button variant="light" onClick={() => onOpenChange(false)}>
            Cancel
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
