FROM amazoncorretto:21-alpine3.18-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean install

FROM amazoncorretto:21-alpine3.18
WORKDIR /app
COPY --from=0 /app/target/telemetry-proto-0.0.1-SNAPSHOT.jar ./telemetry-proto.jar
ENTRYPOINT java -jar telemetry-proto.jar
