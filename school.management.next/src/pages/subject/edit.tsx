import EditFormWrapper from "@/components/EditFormWrapper";
import InlineError from "@/components/InlineError";
import useToastError from "@/hooks/toasts/ToastError";
import { useSubjectById } from "@/hooks/useSubjects";
import { RootState } from "@/store/store";
import { subjectTypes } from "@/utils/classes";
import { Errors, Subject, SubjectType } from "@/utils/types";
import {
  Form,
  Button,
  Input,
  Textarea,
  Select,
  SelectItem,
} from "@heroui/react";
import { PaletteIcon } from "@phosphor-icons/react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";

export default function SubjectEdit({
  incrementLoading,
  decrementLoading,
}: {
  incrementLoading: () => void;
  decrementLoading: () => void;
}) {
  const searchParams = useSearchParams();
  const id: string | null = searchParams.get("id");
  const [error, setError] = React.useState<string | null>(null);
  const [statusCode, setStatusCode] = React.useState(0);
  const [subject, setSubject] = React.useState<Subject>({} as Subject);
  const [errors, setErrors] = React.useState<Errors<Subject, string>>(
    {} as Errors<Subject, string>
  );
  const token = useSelector((state: RootState) => state.auth.token);
  const router = useRouter();
  const { subject: oldSubject, isLoading, isError } = useSubjectById(id!);

  const editMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Editing subject:
      </div>
      <div className="flex justify-center">
        Edit information about the subject
      </div>
    </div>
  );

  const newMsg: React.ReactNode = (
    <div className="w-full">
      <div className="w-full flex justify-center">
        <PaletteIcon weight="thin" size={150} />
      </div>
      <div className="font-bold text-lg w-full flex justify-center">
        Creating subject:
      </div>
      <div className="flex justify-center">
        Enter information about the subject
      </div>
    </div>
  );

  useEffect(() => {
    if (oldSubject) setSubject(oldSubject);
  }, [id, oldSubject]);

  useEffect(() => {
    isError &&
      useToastError({
        error: isError?.message,
        statusCode: isError?.statusCode,
      });
  }, [isError]);

  const validate = () => {
    setErrors({} as Errors<Subject, string>);
    let tempErrors: Errors<Subject, string> = {} as Errors<Subject, string>;
    if (!subject.name || subject.name.trim() === "")
      tempErrors = { ...tempErrors, name: "Subject name is required" };
    if (!subject.description || subject.description.trim() === "")
      tempErrors = { ...tempErrors, description: "Description is required" };
    if (!subject.code || subject.code.trim() === "")
      tempErrors = { ...tempErrors, code: "Code is required" };
    if (!subject.syllabus || subject.syllabus.trim() === "")
      tempErrors = { ...tempErrors, syllabus: "Syllabus is required" };
    if (!subject.curriculum || subject.curriculum.trim() === "")
      tempErrors = { ...tempErrors, curriculum: "Curriculum is required" };
    if (!subject.level || subject.level.trim() === "")
      tempErrors = { ...tempErrors, level: "Level is required" };
    if (!subject.type || subject.type.trim() === "")
      tempErrors = { ...tempErrors, type: "Type is required" };
    if (!subject.category || subject.category.trim() === "")
      tempErrors = { ...tempErrors, category: "Category is required" };
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setStatusCode(0);
    incrementLoading();
    if (!validate()) {
      decrementLoading();
      return;
    }
    try {
      const res = await fetch("/api/subject?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(subject),
      });
      const data = await res.json();
      if (!res.ok) {
        setError(data.error ?? "Saving subject failed");
        setStatusCode(res.status ?? 500);
        return;
      } else if (data?.id) {
        router.push(`/subject/one?id=${data?.id}`);
      }
      decrementLoading();
    } catch (err: any) {
      setError("Netcoursework/Server error");
      setStatusCode(err.status ?? 500);
      decrementLoading();
    }
  };

  return (
    <EditFormWrapper editMsg={editMsg} newMsg={newMsg} isNew={!id}>
      <Form
        onSubmit={onSubmit}
        className="h-full w-full md:w-1/2 pb-2 flex flex-col"
      >
        {error && <InlineError statusCode={statusCode} title={error} />}
        <div className="space-y-4 h-full w-full py-2 overflow-y-auto grow">
          <Input
            isRequired
            label="Name"
            name="name"
            placeholder="Enter subject name"
            type="text"
            value={subject.name ?? ""}
            errorMessage={errors?.name}
            isInvalid={!!errors?.name}
            onValueChange={(e) => setSubject({ ...subject, name: e })}
            size="sm"
            variant="underlined"
            autoFocus={true}
          />
          <Textarea
            isRequired
            label="Description"
            name="description"
            placeholder="Enter subject description"
            value={subject.description ?? ""}
            errorMessage={errors?.description}
            isInvalid={!!errors?.description}
            onValueChange={(e) => setSubject({ ...subject, description: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Input
            isRequired
            label="Code"
            name="code"
            placeholder="Enter subject code"
            type="text"
            value={subject.code ?? ""}
            errorMessage={errors?.code}
            isInvalid={!!errors?.code}
            onValueChange={(e) => setSubject({ ...subject, code: e })}
            size="sm"
            variant="underlined"
          />
          <Input
            isRequired
            label="Syllabus"
            name="syllabus"
            placeholder="Enter subject syllabus"
            value={subject.syllabus ?? ""}
            errorMessage={errors?.syllabus}
            isInvalid={!!errors?.syllabus}
            onValueChange={(e) => setSubject({ ...subject, syllabus: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Input
            isRequired
            label="Curriculum"
            name="curriculum"
            placeholder="Enter curriculum"
            value={subject.curriculum ?? ""}
            errorMessage={errors?.curriculum}
            isInvalid={!!errors?.curriculum}
            onValueChange={(e) => setSubject({ ...subject, curriculum: e })}
            size="sm"
            variant="underlined"
            className="placeholder:text-ellipsis"
          />
          <Input
            isRequired
            label="Level"
            name="level"
            placeholder="Enter subject level"
            type="number"
            value={subject.level ?? ""}
            errorMessage={errors?.level}
            isInvalid={!!errors?.level}
            onValueChange={(e) => setSubject({ ...subject, level: e })}
            size="sm"
            variant="underlined"
          />
          <Select
            isRequired
            label="Type"
            name="type"
            selectedKeys={[subject?.type?.valueOf()]}
            variant="underlined"
            selectionMode="single"
            errorMessage={errors?.type}
            isInvalid={!!errors?.type}
            onSelectionChange={(key) =>
              setSubject({
                ...subject,
                type: key.currentKey! as SubjectType,
              })
            }
          >
            {subjectTypes?.map((type) => (
              <SelectItem key={type}>
                {SubjectType[type as keyof typeof SubjectType]}
              </SelectItem>
            ))}
          </Select>
          <Input
            isRequired
            label="Category"
            name="category"
            placeholder="Enter subject category"
            type="text"
            value={subject.category ?? ""}
            errorMessage={errors?.category}
            isInvalid={!!errors?.category}
            onValueChange={(e) => setSubject({ ...subject, category: e })}
            size="sm"
            variant="underlined"
          />
        </div>
        <Button type="submit" color="primary" className="w-full px-5" onPress={validate}>
          Save
        </Button>
      </Form>
    </EditFormWrapper>
  );
}
