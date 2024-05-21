FROM openjdk:17-jdk
WORKDIR /app
COPY target/cardoso-quizai-api-0.0.2.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]