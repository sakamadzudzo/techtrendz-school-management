import type { NextConfig } from "next";
import showBanner from "@/utils/banner";

const nextConfig: NextConfig = {
  /* config options here */
  reactStrictMode: true,
  allowedDevOrigins: ["local-origin.dev", "*.local-origin.dev"],
  // Enable standalone output for Docker production builds
  // This creates a self-contained output that can be run in containers
  output: 'standalone',
};

showBanner();

export default nextConfig;
