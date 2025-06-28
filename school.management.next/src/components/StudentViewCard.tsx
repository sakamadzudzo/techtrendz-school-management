import { sanitizeForWhatsapp } from "@/utils/helpers";
import { StudentView } from "@/utils/types";
import { Card, CardBody, Spacer, Link } from "@heroui/react";
import MyChip from "./MyChip";

export default function StudentViewCard({ student }: { student: StudentView }) {
  return (
    <Card className="px-4 m-4">
      <CardBody>
        <div className="info-row">
          <div className="info-row-title">Student:</div>
          <div className="info-row-content">
            <Link href={`/student/one?id=${student.id}`}>
              {student?.fullname}
            </Link>
          </div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Period:</div>
          <div className="info-row-content">
            {`${student?.startYear} - ${student?.endYear}`}
          </div>
        </div>
        <Spacer x={4} y={4} />
        {student?.email && (
          <>
            <div className="info-row">
              <div className="info-row-title">E-mail:</div>
              <div className="info-row-content whitespace-pre-wrap">
                <Link isExternal href={`mailto:${student?.email}`}>
                  {student?.email}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.mobileNumber && (
          <>
            <div className="info-row">
              <div className="info-row-title">
                {student?.mobileNumber2 ? "Cell 1" : "Cell"}
              </div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${student?.mobileNumber}`}>
                  {student?.mobileNumber}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.mobileNumber2 && (
          <>
            <div className="info-row">
              <div className="info-row-title">Cell 2:</div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${student?.mobileNumber2}`}>
                  {student?.mobileNumber2}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.telNumber && (
          <>
            <div className="info-row">
              <div className="info-row-title">Tel:</div>
              <div className="info-row-content">
                <Link isExternal href={`tel:${student?.telNumber}`}>
                  {student?.telNumber}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.whatsapp && (
          <>
            <div className="info-row">
              <div className="info-row-title">WhatsApp:</div>
              <div className="info-row-content">
                <Link isExternal href={sanitizeForWhatsapp(student?.whatsapp)}>
                  {student?.whatsapp}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.linkedin && (
          <>
            <div className="info-row">
              <div className="info-row-title">LinkedIn:</div>
              <div className="info-row-content">
                <Link isExternal href={student?.linkedin}>
                  {student?.linkedin}
                </Link>
              </div>
            </div>
            <Spacer x={4} y={4} />
          </>
        )}
        {student?.facebook && (
          <>
            <div className="info-row">
              <div className="info-row-title">Facebook:</div>
              <div className="info-row-content">
                <Link isExternal href={student?.facebook}>
                  {student?.facebook}
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
            data={student?.status}
            size="sm"
            variant="flat"
          />
        </div>
      </CardBody>
    </Card>
  );
}
