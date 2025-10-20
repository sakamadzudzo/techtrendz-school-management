import type { NextConfig } from "next";
import showBanner from "@/utils/banner";

const nextConfig: NextConfig = {
  /* config options here */
  reactStrictMode: true,
  allowedDevOrigins: ["local-origin.dev", "*.local-origin.dev"],
};

showBanner();

export default nextConfig;
