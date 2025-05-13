FROM amazoncorretto:17-alpine3.16

COPY target/*.jar /app/assignment-discovery-service.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/app/assignment-discovery-service.jar"]

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/assignment-app/discovery-service.jar"]


FROM amazoncorretto:17-alpine3.16 AS builder

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.16

WORKDIR /app

COPY --from=builder /app/target/assignment-discovery-0.0.1-SNAPSHOT.jar /app/assignment-discorvery-service.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/assignment-discorvery-service.jar"]