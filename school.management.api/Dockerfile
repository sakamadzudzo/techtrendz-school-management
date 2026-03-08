# ===================================================================
# Dockerfile for School Management API (Spring Boot Backend)
# ===================================================================
# This Dockerfile builds and runs the Spring Boot backend application.
# It uses a multi-stage build for optimized image size.
#
# Build: docker build -t school-management-api .
# Run:   docker run -p 4002:4002 school-management-api
# ===================================================================

# Stage 1: Build stage
# -----------------------------------------------------------------------------
# Use Maven with JDK 17 for building the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory for build stage
WORKDIR /app

# Copy pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Copy source files
COPY src ./src

# Build the application (skip tests for faster builds)
# The Maven wrapper is not needed since we use Maven directly
RUN mvn package -DskipTests -q

# Stage 2: Runtime stage
# -----------------------------------------------------------------------------
# Use a slim JDK 17 image for running the application
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

# Copy the built artifact from build stage
COPY --from=build /app/target/*.jar app.jar

# -----------------------------------------------------------------------------
# Environment Variables
# -----------------------------------------------------------------------------
# Database Configuration (H2 file-based)
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/data/schoolmanage
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=schoolmanage
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver

# Server Configuration
ENV SERVER_PORT=8080

# JWT Configuration
ENV JWT_SECRET=School_management_application_by_TechTrendz_(Saka_Shingirai_Madzudzo)
ENV JWT_EXPIRATION=60

# JPA Configuration
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

# H2 Console (disable in production)
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SPRING_H2_CONSOLE_PATH=/h2-console

# Application Name
ENV SPRING_APPLICATION_NAME=school.management.api

# Swagger Configuration
ENV SPRINGDOC_SWAGGER_UI_PATH=/swagger

# Actuator Configuration
ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info
ENV MANAGEMENT_ENDPOINT_HEALTH_ENABLED=true
ENV MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS=always
ENV MANAGEMENT_ENDPOINTS_WEB_BASE_PATH=/actuator

# -----------------------------------------------------------------------------
# Expose the application port
EXPOSE 4002

# -----------------------------------------------------------------------------
# Volume for persistent database storage
VOLUME /data

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:4002/actuator/health || exit 1

# -----------------------------------------------------------------------------
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
