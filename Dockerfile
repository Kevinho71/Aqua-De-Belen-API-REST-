# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables (will be overridden by Coolify)
ENV DATABASE_URL=${DATABASE_URL:-jdbc:postgresql://localhost:5432/postgres}
ENV DATABASE_USERNAME=${DATABASE_USERNAME:-postgres}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD:-postgres}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
