import { capitalize, sanitizeForWhatsapp } from "@/utils/helpers";
import { SubjectView } from "@/utils/types";
import {
  Card,
  CardHeader,
  Divider,
  CardBody,
  Spacer,
  CardFooter,
  Link,
} from "@heroui/react";

export default function SubjectViewCard({
  subject,
}: {
  subject: SubjectView;
}) {
  return (
    <Card className="px-4 m-4">
      <CardHeader>
        <div className="-space-y-2">
          <div className="font-semibold">{subject?.name}:</div>
          <div className="font-light text-sm italic w-full">
            {subject?.description}
          </div>
        </div>
      </CardHeader>
      <Divider />
      <CardBody>
        <div className="info-row">
          <div className="info-row-title">Code:</div>
          <div className="info-row-content">{subject?.code}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Curriculum:</div>
          <div className="info-row-content">{subject?.curriculum}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Syllabus:</div>
          <div className="info-row-content">{subject?.syllabus}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Level:</div>
          <div className="info-row-content">{subject?.level}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Category:</div>
          <div className="info-row-content">{subject?.category}</div>
        </div>
        <Spacer x={4} y={4} />
        <div className="info-row">
          <div className="info-row-title">Type:</div>
          <div className="info-row-content">{capitalize(subject?.type)}</div>
        </div>
      </CardBody>
      {subject.teacher && (
        <>
          <Divider />
          <CardBody>
            <div className="info-row">
              <div className="info-row-title">Teacher:</div>
              <div className="info-row-content"><Link href={`/teacher/one?id=${subject.teacher.id}`}>{subject.teacher?.fullname}</Link></div>
            </div>
            <Spacer x={4} y={4} />
            {subject.teacher?.email && (
              <>
                <div className="info-row">
                  <div className="info-row-title">E-mail:</div>
                  <div className="info-row-content whitespace-pre-wrap">
                    <Link isExternal href={`mailto:${subject.teacher?.email}`}>
                      {subject.teacher?.email}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.mobileNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">
                    {subject.teacher?.mobileNumber2 ? "Cell 1" : "Cell"}
                  </div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${subject.teacher?.mobileNumber}`}>
                      {subject.teacher?.mobileNumber}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.mobileNumber2 && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Cell 2:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${subject.teacher?.mobileNumber2}`}>
                      {subject.teacher?.mobileNumber2}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.telNumber && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Tel:</div>
                  <div className="info-row-content">
                    <Link isExternal href={`tel:${subject.teacher?.telNumber}`}>
                      {subject.teacher?.telNumber}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.whatsapp && (
              <>
                <div className="info-row">
                  <div className="info-row-title">WhatsApp:</div>
                  <div className="info-row-content">
                    <Link
                      isExternal
                      href={sanitizeForWhatsapp(subject.teacher?.whatsapp)}
                    >
                      {subject.teacher?.whatsapp}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.linkedin && (
              <>
                <div className="info-row">
                  <div className="info-row-title">LinkedIn:</div>
                  <div className="info-row-content">
                    <Link isExternal href={subject.teacher?.linkedin}>
                      {subject.teacher?.linkedin}
                    </Link>
                  </div>
                </div>
                <Spacer x={4} y={4} />
              </>
            )}
            {subject.teacher?.facebook && (
              <>
                <div className="info-row">
                  <div className="info-row-title">Facebook:</div>
                  <div className="info-row-content">
                    <Link isExternal href={subject.teacher?.facebook}>
                      {subject.teacher?.facebook}
                    </Link>
                  </div>
                </div>
              </>
            )}
          </CardBody>
        </>
      )}
      <Divider />
      <CardFooter>
        <div className="info-row">
          <div className="info-row-title">Calendar Year:</div>
          <div className="info-row-content">{subject?.calendarYear}</div>
        </div>
      </CardFooter>
    </Card>
  );
}
