FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]