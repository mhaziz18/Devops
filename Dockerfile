FROM openjdk:11
EXPOSE 8089
COPY target/*.jar /event.jar
ENTRYPOINT ["java", "-jar", "/event.jar"]
