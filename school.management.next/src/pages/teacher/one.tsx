import MyChip from "@/components/MyChip";
import { useTeacherById } from "@/hooks/useTeachers";
import {
  capitalize,
  formatDate,
  getInitials,
  sanitizeForWhatsapp,
} from "@/utils/helpers";
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Divider,
  Image,
  Link,
  Spacer,
} from "@heroui/react";
import { useSearchParams } from "next/navigation";
import {
  IdentificationCardIcon,
  UserFocusIcon,
  StudentIcon,
  BookOpenTextIcon,
  HouseLineIcon,
  ChalkboardSimpleIcon,
  PencilSimpleLineIcon,
} from "@phosphor-icons/react";

export default function OneTeacherPage() {
    const searchParams = useSearchParams();
      const id: string | null = searchParams.get("id");
      const { teacher, isLoading, isError } = useTeacherById(id!);

  return (
    <div className="grow overflow-auto">
      <div aria-label="Page buttons" className="w-full flex justify-end gap-4">
        <Button
          color="primary"
          endContent={<PencilSimpleLineIcon weight="thin" size={34} />}
        >
          Edit
        </Button>
      </div>
      <Accordion isCompact={true} defaultExpandedKeys={["1"]}>
        <AccordionItem
          key="1"
          aria-label="Student Overview"
          title="Student Overview"
          subtitle="Quick snapshot of the student's current status and core identifiers"
          startContent={<IdentificationCardIcon weight="thin" size={34} />}
        ></AccordionItem>
      </Accordion>
    </div>
  );
}
