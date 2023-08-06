FROM openjdk:17
WORKDIR /app

COPY ./target/gateways-service-0.0.1-SNAPSHOT.jar ./target/gateways-service-0.0.1-SNAPSHOT.jar

EXPOSE 8081

# Command to run your Spring Boot application
CMD ["java", "-jar", "./target/gateways-service-0.0.1-SNAPSHOT.jar"]