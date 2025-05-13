FROM amazoncorretto:17-alpine3.16 AS core-builder

RUN apk add --no-cache maven

WORKDIR /app

COPY assignment-core /app/assignment-core

RUN cd assignment-core && mvn clean install -DskipTests

FROM amazoncorretto:17-alpine3.16 AS app-builder

RUN apk add --no-cache maven

WORKDIR /app

COPY --from=core-builder /root/.m2/repository /root/.m2/repository

COPY assignment-gateway /app/assignment-gateway-service

RUN cd assignment-gateway-service && mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.16

WORKDIR /app

COPY --from=app-builder /app/assignment-gateway-service/target/assignment-gateway-0.0.1-SNAPSHOT.jar /app/assignment-gateway-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/assignment-gateway-service.jar"]