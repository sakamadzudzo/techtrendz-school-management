import { sanitizeForWhatsapp } from "@/utils/helpers";
import { TeacherView } from "@/utils/types";
import {
  Card,
  CardBody,
  Spacer,
  Link,
} from "@heroui/react";
import MyChip from "./MyChip";

export default function TeacherViewCard({ teacher }: { teacher: TeacherView }) {
  return (
    <Card className="px-4 m-4">
      <CardBody>
        <div className="info-row">
          <div className="info-row-title">Teacher:</div>
          <div className="info-row-content">
            <Link href={`/teacher/one?id=${teacher.id}`}>
              {teacher?.fullname}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        {teacher?.email && (
          <>
            <div className="info-row">
              <div className="info-row-title">E-mail:</div>
              <div className="info-row-content whitespace-pre-wrap">
                <Link isExternal href={`mailto:${teacher?.email}`}>
                  {teacher?.email}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.mobileNumber && (
          <>
            <div className="info-row">
              <div className="info-row-title">
                {teacher?.mobileNumber2 ? "Cell 1" : "Cell"}
              </div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${teacher?.mobileNumber}`}>
                  {teacher?.mobileNumber}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.mobileNumber2 && (
          <>
            <div className="info-row">
              <div className="info-row-title">Cell 2:</div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${teacher?.mobileNumber2}`}>
                  {teacher?.mobileNumber2}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.telNumber && (
          <>
            <div className="info-row">
              <div className="info-row-title">Tel:</div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${teacher?.telNumber}`}>
                  {teacher?.telNumber}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.whatsapp && (
          <>
            <div className="info-row">
              <div className="info-row-title">WhatsApp:</div>
              <div className="info-row-content">
                <Link isExternal href={sanitizeForWhatsapp(teacher?.whatsapp)}>
                  {teacher?.whatsapp}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.linkedin && (
          <>
            <div className="info-row">
              <div className="info-row-title">LinkedIn:</div>
              <div className="info-row-content">
                <Link isExternal href={teacher?.linkedin}>
                  {teacher?.linkedin}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {teacher?.facebook && (
          <>
            <div className="info-row">
              <div className="info-row-title">Facebook:</div>
              <div className="info-row-content">
                <Link isExternal href={teacher?.facebook}>
                  {teacher?.facebook}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        <div className="info-row">
          <div className="info-row-title">Status:</div>
          <MyChip
            className="capitalize info-row-content"
            data={teacher?.status}
            size="sm"
            variant="flat"
          />
        </div>
      </CardBody>
    </Card>
  );
}
