import { Errors, Coursework, CourseworkType } from "@/utils/types";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  Form,
  Input,
  Select,
  SelectItem,
  Button,
} from "@heroui/react";
import { useCourseworkById } from "@/hooks/useCourseworks";
import { useEffect, useState } from "react";
import useToastError from "@/hooks/toasts/ToastError";
import { courseworkTypes } from "@/utils/classes";
import { useRouter } from "next/router";
import { RootState } from "@/store/store";
import { useSelector } from "react-redux";

type AddCourseworkProps = {
  coursework?: Coursework;
  subjectName: string;
  subjectYearId: number;
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  incrementLoading: () => void;
  decrementLoading: () => void;
  reloadTrigger: () => void;
};

export const AddCoursework = ({
  coursework: currentCoursework,
  subjectName,
  subjectYearId,
  isOpen,
  onOpenChange,
  incrementLoading,
  decrementLoading,
  reloadTrigger,
}: AddCourseworkProps) => {
  const router = useRouter();
  const token = useSelector((state: RootState) => state.auth.token);
  const [coursework, setCoursework] = useState<Coursework>({} as Coursework);
  const [error, setError] = useState<string | null>(null);
  const [statusCode, setStatusCode] = useState(0);
  const [errors, setErrors] = useState<Errors<Coursework, string>>(
    {} as Errors<Coursework, string>
  );

  useEffect(() => {
    if (isOpen) {
      setError(null);
      setStatusCode(0);
    }
  }, [isOpen]);

  useEffect(() => {
    setCoursework(currentCoursework || ({} as Coursework));
  }, [currentCoursework]);

  const validate = () => {
    setErrors({} as Errors<Coursework, string>);
    let tempErrors: Errors<Coursework, string> = {} as Errors<
      Coursework,
      string
    >;
    if (!coursework.subject || !coursework.subject.id) {
      tempErrors = {
        ...tempErrors,
        subject: { ...tempErrors.subject, general: "Subject year is required" },
      };
    }
    if (!coursework.type) {
      tempErrors = {
        ...tempErrors,
        type: "Coursework type is required",
      };
    }
    if (!coursework.total || coursework.total <= 0) {
      tempErrors = {
        ...tempErrors,
        total: "Coursework total is required",
      };
    }
    if (!coursework.passMark || coursework.passMark <= 0) {
      tempErrors = {
        ...tempErrors,
        passMark: "Coursework pass mark is required",
      };
    }

    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();

    setCoursework({
      ...coursework,
      subject: { ...coursework.subject, id: subjectYearId },
    });
    if (!validate()) {
      decrementLoading();
      return;
    }

    try {
      const res = await fetch("/api/coursework?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(coursework),
      });

      const data = await res.json();

      if (!res.ok) {
        const err = data.error || "Saving coursework failed";
        const code = res.status || 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        decrementLoading();
        return;
      } else if (data?.id) {
        reloadTrigger();
      }
      decrementLoading();
    } catch (err: any) {
      setError(err.response.data.message || "Network/Server error");
      setStatusCode(err.status || 500);
      decrementLoading();
    }
  };

  return (
    <Modal
      isOpen={isOpen}
      onOpenChange={onOpenChange}
      hideCloseButton={false}
      backdrop="blur"
      radius="md"
      shadow="md"
      classNames={{
        wrapper: "py-2 overflow-hidden",
      }}
      className="text-foreground"
    >
      <ModalContent className="overflow-y-auto max-h-full">
        <ModalHeader className="text-xl font-bold">
          {`${
            coursework?.id ? "Edit" : "Add"
          } coursework to { ${subjectName} }`}
          <Button color="primary" variant="ghost" onPress={reloadTrigger}>
            Test
          </Button>
        </ModalHeader>
        <ModalBody>
          <Form onSubmit={onSubmit} className="space-y-3">
            <Input
              isRequired
              label="Title"
              name="title"
              placeholder="Enter title"
              value={coursework.title || ""}
              errorMessage={errors?.title}
              isInvalid={!!errors?.title}
              onValueChange={(e) => setCoursework({ ...coursework, title: e })}
              size="sm"
              variant="underlined"
              autoFocus={true}
            />
            <Select
              label="Type"
              selectedKeys={[coursework.type]}
              variant="underlined"
              selectionMode="single"
              errorMessage={errors?.type}
              isInvalid={!!errors?.type}
              onSelectionChange={(key) =>
                setCoursework({
                  ...coursework,
                  type: key.currentKey! as CourseworkType,
                })
              }
              isRequired
            >
              {courseworkTypes?.map((type) => (
                <SelectItem key={type}>{type}</SelectItem>
              ))}
            </Select>
            <Input
              isRequired
              label="Total Mark"
              name="total"
              placeholder="Enter total mark"
              value={String(coursework.total) || ""}
              errorMessage={errors?.total}
              isInvalid={!!errors?.total}
              onValueChange={(e) =>
                setCoursework({ ...coursework, total: Number(e) })
              }
              size="sm"
              variant="underlined"
              type="number"
            />
            <Input
              isRequired
              label="Pass Mark"
              name="passMark"
              placeholder="Enter pass mark"
              value={String(coursework.passMark) || ""}
              errorMessage={errors?.passMark}
              isInvalid={!!errors?.passMark}
              onValueChange={(e) =>
                setCoursework({ ...coursework, passMark: Number(e) })
              }
              size="sm"
              variant="underlined"
              type="number"
            />
            <Button
              type="submit"
              color="primary"
              className="button"
              onPress={validate}
            >
              Save
            </Button>
            <Button
              type="button"
              color="danger"
              className="button"
              onPress={() => onOpenChange(false)}
            >
              Cancel
            </Button>
          </Form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
