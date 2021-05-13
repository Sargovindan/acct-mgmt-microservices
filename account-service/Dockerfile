FROM adoptopenjdk/openjdk13:jre-13.0.2_8-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=development /app.jar"]