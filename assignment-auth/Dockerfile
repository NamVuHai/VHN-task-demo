FROM amazoncorretto:17-alpine3.16 AS core-builder

RUN apk add --no-cache maven

WORKDIR /app

COPY assignment-core /app/assignment-core

RUN cd assignment-core && mvn clean install -DskipTests

FROM amazoncorretto:17-alpine3.16 AS app-builder

RUN apk add --no-cache maven

WORKDIR /app

COPY --from=core-builder /root/.m2/repository /root/.m2/repository

COPY assignment-auth /app/assignment-auth-service

RUN cd assignment-auth-service && mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.16

WORKDIR /app

COPY --from=app-builder /app/assignment-auth-service/target/assignment-auth-0.0.1-SNAPSHOT.jar /app/assignment-auth-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/assignment-auth-service.jar"]