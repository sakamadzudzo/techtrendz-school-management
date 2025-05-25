import { MouseEventHandler } from "react";

export const IconPlusThin = ({
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
        <path d="M220,128a4,4,0,0,1-4,4H132v84a4,4,0,0,1-8,0V132H40a4,4,0,0,1,0-8h84V40a4,4,0,0,1,8,0v84h84A4,4,0,0,1,220,128Z"></path>
      </svg>
    </div>
  );
};
