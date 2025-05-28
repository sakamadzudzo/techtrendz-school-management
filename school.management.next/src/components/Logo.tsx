import React from "react";

export const Logo = () => {
  return (
    <div className="flex items-center gap-2 select-none">
      <svg
        fill="none"
        height="36"
        viewBox="0 0 36 36"
        width="36"
        aria-label="TechTrendz Logo Icon"
      >
        <rect x="4" y="6" width="28" height="4" rx="2" fill="currentColor" />
        <rect x="14" y="10" width="4" height="20" rx="2" fill="currentColor" />
        <rect
          x="18"
          y="14"
          width="14"
          height="4"
          rx="2"
          fill="currentColor"
          opacity="0.7"
        />
        <rect
          x="23"
          y="18"
          width="4"
          height="12"
          rx="2"
          fill="currentColor"
          opacity="0.7"
        />
        <path
          d="M8 32 Q18 28 28 32"
          stroke="currentColor"
          strokeWidth="1.5"
          strokeLinecap="round"
          opacity="0.5"
        />
        <circle cx="8" cy="32" r="1.5" fill="currentColor" opacity="0.7" />
        <circle cx="28" cy="32" r="1.5" fill="currentColor" opacity="0.7" />
      </svg>
    </div>
  );
};
