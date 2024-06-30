FROM openjdk:11
EXPOSE 9091
COPY target/*.jar /event.jar
ENTRYPOINT ["java", "-jar", "/event.jar"]
