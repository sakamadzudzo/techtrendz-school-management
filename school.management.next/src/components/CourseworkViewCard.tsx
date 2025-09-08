import { Coursework } from "@/utils/types";
import {
  Card,
  CardHeader,
  Divider,
  CardBody,
  Spacer,
  CardFooter,
  Button,
} from "@heroui/react";
import { ExamIcon } from "@phosphor-icons/react";

export default function CourseworkViewCard({ coursework }: { coursework: Coursework }) {
  if (coursework) {
    return (
      // Change card to a table. Action buttons will be grade and edit
      <Card className="px-4 m-4">
        <CardHeader>
          <div className="-space-y-2">
            <div className="font-semibold">{coursework?.title}:</div>
          </div>
        </CardHeader>
        <Divider />
        <CardBody>
          <div className="info-row">
            <div className="info-row-title">Type:</div>
            <div className="info-row-content">{coursework?.type}</div>
          </div>
        </CardBody>
        <Divider />
        <CardFooter className="flex justify-end gap-2">
          <Button
            color="primary"
            endContent={<ExamIcon weight="thin" size={34} />}
          >
            Garde
          </Button>
        </CardFooter>
      </Card>
    );
  } else {
    return (
      <Card>
        <CardHeader>No data</CardHeader>
        <Divider />
        <CardBody>There is no data to display</CardBody>
      </Card>
    );
  }
}
