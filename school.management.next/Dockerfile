# ===================================================================
# Dockerfile for School Management Next.js Frontend
# ===================================================================
# This Dockerfile builds and runs the Next.js frontend application.
# The frontend communicates with the API behind the scenes.
#
# Build: docker build -t school-management-next .
# Run:   docker run -p 3000:3000 school-management-next
# ===================================================================

# Stage 1: Build stage
# -----------------------------------------------------------------------------
# Use Node.js 20 LTS for building the Next.js application
FROM node:20-alpine AS build

# Set working directory
WORKDIR /app

# Copy package files first to leverage Docker cache
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source files
COPY ./ ./

# -----------------------------------------------------------------------------
# Environment Variables for Production Build
# -----------------------------------------------------------------------------
# The API URL - update this to point to your API container/service
ENV API_BASE=http://localhost:4002

# Build the Next.js application
RUN npm run build

# Stage 2: Production stage
# -----------------------------------------------------------------------------
# Use a smaller Node.js image for running the production build
FROM node:20-alpine AS production

# Set working directory
WORKDIR /app

# Copy the built application from build stage
COPY --from=build /app/.next/standalone ./
COPY --from=build /app/.next/static ./.next/static
COPY --from=build /app/public ./public

# -----------------------------------------------------------------------------
# Environment Variables
# -----------------------------------------------------------------------------
# The API Base URL - must match the API service name in docker-compose
# In Docker Compose, this will be http://api:4002
ENV API_BASE=http://api:4002
ENV NODE_ENV=production

# -----------------------------------------------------------------------------
# Expose the frontend port (Next.js default)
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:3000 || exit 1

# -----------------------------------------------------------------------------
# Start the Next.js production server
# The standalone output mode is used for optimized production deployments
CMD ["node", "server.js"]
