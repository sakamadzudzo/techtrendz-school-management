import { MouseEventHandler } from "react";

export const IconThreeDotsVerticalThin = ({
  className,
  onClick,
}: {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}) => {
  return (
    <div
      className={`icon text-foreground bg-background stroke-foreground fill-foreground ${className}`}
      onClick={onClick}
    >
      <svg
        className={`fill-inherit stroke-inherit`}
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
        fill="none"
        viewBox="0 0 256 256"
      >
        <path d="M120,60a8,8,0,1,1,8,8A8,8,0,0,1,120,60Zm8,60a8,8,0,1,0,8,8A8,8,0,0,0,128,120Zm0,68a8,8,0,1,0,8,8A8,8,0,0,0,128,188Z"></path>
      </svg>
    </div>
  );
};
