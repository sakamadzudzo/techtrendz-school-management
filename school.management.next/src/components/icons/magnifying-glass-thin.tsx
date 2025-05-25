import { MouseEventHandler } from "react";

export const IconMagnifyingGlassThin = ({
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
        <path d="M226.83,221.17l-52.7-52.7a84.1,84.1,0,1,0-5.66,5.66l52.7,52.7a4,4,0,0,0,5.66-5.66ZM36,112a76,76,0,1,1,76,76A76.08,76.08,0,0,1,36,112Z"></path>
      </svg>
    </div>
  );
};
